package com.b3.ddarelro.domain.file.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.b3.ddarelro.domain.card.entity.Card;
import com.b3.ddarelro.domain.card.service.CardService;
import com.b3.ddarelro.domain.file.dto.response.FileDeleteRes;
import com.b3.ddarelro.domain.file.dto.response.FileDownloadRes;
import com.b3.ddarelro.domain.file.dto.response.FileListRes;
import com.b3.ddarelro.domain.file.dto.response.FileUploadRes;
import com.b3.ddarelro.domain.file.entity.File;
import com.b3.ddarelro.domain.file.exception.FileErrorCod;
import com.b3.ddarelro.domain.file.repository.FileRepository;
import com.b3.ddarelro.global.exception.GlobalException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final CardService cardService;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<FileUploadRes> uploadFile(Long cardId, List<MultipartFile> multipartFileList)
        throws IOException {

        Card card = cardService.findCard(cardId);
        List<FileUploadRes> fileList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFileList) {
            if (multipartFile != null) {
                String originalFilename = multipartFile.getOriginalFilename();
                // 업로그한 원본 파일명

                String storeFilename = UUID.randomUUID() + "." + extractExt(originalFilename);
                // 서버에 업로들될 파일이름 + .확장자

                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(multipartFile.getSize());
                metadata.setContentType(multipartFile.getContentType());

                amazonS3.putObject(bucket, "cards/" + storeFilename, multipartFile.getInputStream(),
                    metadata);

                String filePathUrl = amazonS3.getUrl(bucket, "cards/" + storeFilename).toString();

                File uploadFile = File.builder()
                    .originFilename(originalFilename)
                    .storeFilename(storeFilename)
                    .filePath(filePathUrl)
                    .card(card)
                    .build();

                fileRepository.save(uploadFile);
                fileList.add(FileUploadRes.builder()
                    .originFilename(uploadFile.getOriginFilename())
                    .id(uploadFile.getId())
                    .storeFilename(uploadFile.getStoreFilename())
                    .filePath(uploadFile.getFilePath())
                    .build()
                );
            }
        }

        return fileList;
    }

    @Transactional(readOnly = true)
    public List<FileListRes> getFileList(
        Long cardId
    ) {
        Card card = cardService.findCard(cardId);

        List<File> fileList = fileRepository.findAllByCardAndDeletedFalse(card);

        return fileList.stream()
            .map(
                file -> FileListRes.builder()
                    .id(file.getId())
                    .originFilename(file.getOriginFilename())
                    .storeFilename(file.getStoreFilename())
                    .filePath(file.getFilePath())
                    .build()
            ).toList();
    }


    public FileDownloadRes downloadFile(Long fileId) throws MalformedURLException {
        File file = findFile(fileId);

        UrlResource urlResource = new UrlResource(file.getFilePath());

        String contentDisposition = "attachment; filename=\"" + file.getStoreFilename() + "\"";

        return FileDownloadRes.builder()
            .contentDisposition(contentDisposition)
            .urlResource(urlResource)
            .build();

    }

    public FileDeleteRes deleteFile(Long fileId) {
        File file = findFile(fileId);

        file.updateState(true);

        return FileDeleteRes.builder()
            .message("파일삭제가 완료되었습니다.")
            .build();

    }


    private String extractExt(String originalFilename) { // 확장자 추출
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public File findFile(Long fileId) {

        File file = fileRepository.findById(fileId)
            .orElseThrow(() -> new GlobalException(FileErrorCod.NOT_FOUND_FILE));

        if (file.getDeleted()) {
            throw new GlobalException(FileErrorCod.NOT_FOUND_FILE);
        }
        return file;
    }

}
