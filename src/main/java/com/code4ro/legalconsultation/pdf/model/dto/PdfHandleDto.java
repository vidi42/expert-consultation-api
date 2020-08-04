package com.code4ro.legalconsultation.pdf.model.dto;

import com.code4ro.legalconsultation.core.model.dto.BaseEntityDto;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class PdfHandleDto extends BaseEntityDto {
    private String state;
    private String uri;
    private Instant timestamp;
    private Integer hash;
}
