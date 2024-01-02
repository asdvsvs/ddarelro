package com.b3.ddarelro.domain.file;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.b3.ddarelro.domain.card.entity.Card;
import com.b3.ddarelro.domain.card.service.CardService;
import com.b3.ddarelro.domain.file.entity.File;
import com.b3.ddarelro.domain.file.repository.FileRepository;
import com.b3.ddarelro.domain.file.service.FileService;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@TestInstance(TestInstance.Lifecycle.PER_METHOD) // 기본값 테스트마다 인스턴스 공유안함
@Transactional
@ExtendWith(MockitoExtension.class) // junit5용
public class FileServiceTest {

    @Mock
    private AmazonS3 amazonS3;
    @Mock
    private CardService cardService;
    @Mock
    private FileRepository fileRepository;
    @InjectMocks
    private FileService fileService;


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private List<MultipartFile> fileList;

    private Long cardId;

    @BeforeEach
    void setup() {
        // Given
        cardId = 1L;
        given(cardService.findCard(cardId)).willReturn(Card.builder().build());

        fileList = new ArrayList<>();
        MockMultipartFile file1 = new MockMultipartFile(
            "file",     // Request parameter name
            "테스트.txt", // Original file name
            "text/plain", // Content type
            "테스트파일입니다.".getBytes() // File content
        );
        fileList.add(file1);

        MockMultipartFile file2 = new MockMultipartFile(
            "file",     // Request parameter name
            "테스트2.txt", // Original file name
            "text/plain", // Content type
            "테스트파일2입니다.".getBytes() // File content
        );
        fileList.add(file2);

        MockMultipartFile file3 = new MockMultipartFile(
            "file",     // Request parameter name
            "테스트3.txt", // Original file name
            "text/plain", // Content type
            "테스트파일3입니다.".getBytes() // File content
        );
        fileList.add(file3);

    }

    @Test
    @DisplayName("파일업로드")
    void 파일업로드() throws IOException {

        // Given

        given(
            amazonS3.putObject(eq(bucket), any(String.class), any(InputStream.class), any(
                ObjectMetadata.class))).willReturn((new PutObjectResult()));
        given(amazonS3.getUrl(eq(bucket), any(String.class)))
            .willReturn(new URL("https://test"));

        fileService.uploadFile(cardId, fileList);
        Mockito.verify(fileRepository, Mockito.times(3)).save(any(File.class));
        //save 3회일어나는지 체크

    }


    @Test
    @DisplayName("업로도된파일조회")
    void 파일조회() throws IOException {

        given(
            amazonS3.putObject(eq(bucket), any(String.class), any(InputStream.class), any(
                ObjectMetadata.class))).willReturn((new PutObjectResult()));
        given(amazonS3.getUrl(eq(bucket), any(String.class)))
            .willReturn(new URL("https://test"));

        fileService.uploadFile(cardId, fileList);
        fileService.getFileList(cardId);
        Mockito.verify(fileRepository, Mockito.times(1))
            .findAllByCardAndDeletedFalse(any(Card.class));

    }

    @Test
    @DisplayName("업로드된 파일삭제")
    void 파일다운로드() throws IOException {

        Long fileId = 1L;
        given(fileRepository.findById(fileId)).willReturn(
            Optional.ofNullable(File.builder().build()));

        given(
            amazonS3.putObject(eq(bucket), any(String.class), any(InputStream.class), any(
                ObjectMetadata.class))).willReturn((new PutObjectResult()));
        given(amazonS3.getUrl(eq(bucket), any(String.class)))
            .willReturn(new URL("https://test"));

        fileService.uploadFile(cardId, fileList);

        fileService.deleteFile(fileId);
        Mockito.verify(fileRepository, Mockito.times(1))
            .delete(any(File.class));


    }


}
