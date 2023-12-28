package com.b3.ddarelro.domain.file.service;

import com.b3.ddarelro.domain.file.repository.FileRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FileDeleteRestoreService {

    private final FileRepository fileRepository;


    public void deleteAllFiles(List<Long> fileIds) {
        fileRepository.SoftDelete(fileIds);
    }

    public void restoreAllFiles(List<Long> fileIds) {
        fileRepository.restoreAll(fileIds);
    }

}
