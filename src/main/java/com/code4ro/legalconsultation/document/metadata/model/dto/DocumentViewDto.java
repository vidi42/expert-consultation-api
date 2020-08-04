package com.code4ro.legalconsultation.document.metadata.model.dto;

import com.code4ro.legalconsultation.core.model.dto.validator.UniqueDocumentFilePath;
import com.code4ro.legalconsultation.core.model.dto.validator.UniqueDocumentNumberConstraint;
import com.code4ro.legalconsultation.document.metadata.model.persistence.DocumentType;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentViewDto {
    @NotNull(message = "document.save.number.null")
    @UniqueDocumentNumberConstraint
    private BigInteger documentNumber;

    @NotNull(message = "document.save.title.null")
    private String documentTitle;

    @NotNull(message = "document.save.initializer.null")
    private String documentInitializer;

    @NotNull(message = "document.save.type.null")
    private DocumentType documentType;

    @NotNull(message = "document.save.develop.null")
    private Date dateOfDevelopment;

    @NotNull(message = "document.save.receiving.null")
    private Date dateOfReceipt;

    @NotNull(message = "document.save.filePath.null")
    @UniqueDocumentFilePath
    private String filePath;

}
