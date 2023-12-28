package com.b3.ddarelro.domain.file.dto.response;

import lombok.Builder;

@Builder
public record FileUploadRes(Long id, String originFilename, String storeFilename, String filePath) {

}
