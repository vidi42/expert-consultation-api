package com.code4ro.legalconsultation.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentConsolidatedDto extends BaseEntityDto {
    private DocumentMetadataDto documentMetadata;
    private DocumentNodeDto documentNode;
    private DocumentConfigurationDto documentConfiguration;
}
