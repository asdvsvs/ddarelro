package com.b3.ddarelro.domain.file.controller;

import com.b3.ddarelro.domain.file.dto.FileListReq;
import com.b3.ddarelro.domain.file.dto.request.FileUploadReq;
import com.b3.ddarelro.domain.file.dto.response.FileDeleteRes;
import com.b3.ddarelro.domain.file.dto.response.FileDownloadRes;
import com.b3.ddarelro.domain.file.dto.response.FileListRes;
import com.b3.ddarelro.domain.file.dto.response.FileUploadRes;
import com.b3.ddarelro.domain.file.service.FileService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<List<FileUploadRes>> uploadFile(
        @RequestPart("file") List<MultipartFile> multipartFileList,
        @RequestPart("dto") FileUploadReq req
    ) throws IOException {

        List<FileUploadRes> res = fileService.uploadFile(req.cardId(), multipartFileList);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping
    public ResponseEntity<List<FileListRes>> getFileList(
        @RequestBody FileListReq req
    ) {

        List<FileListRes> res = fileService.getFileList(req.cardId());
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<UrlResource> downloadFile(
        @PathVariable Long fileId
    ) throws MalformedURLException {
        FileDownloadRes res = fileService.downloadFile(fileId);
        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.CONTENT_DISPOSITION, res.contentDisposition())
            .body(res.urlResource());
    }


    @DeleteMapping("/{fileId}")
    public ResponseEntity<FileDeleteRes> deleteFile(
        @PathVariable Long fileId
    ) {

        FileDeleteRes res = fileService.deleteFile(fileId);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
