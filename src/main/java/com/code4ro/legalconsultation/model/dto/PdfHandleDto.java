package com.code4ro.legalconsultation.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.time.Instant;

@Getter
@Setter
public class PdfHandleDto extends BaseEntityDto {
    private String state;
    private URI uri;
    private Instant timestamp;
    private Integer hash;
}
