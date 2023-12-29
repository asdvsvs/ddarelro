package com.b3.ddarelro.domain.file.dto.response;

import lombok.Builder;
import org.springframework.core.io.UrlResource;


@Builder
public record FileDownloadRes(UrlResource urlResource, String contentDisposition) {

}
