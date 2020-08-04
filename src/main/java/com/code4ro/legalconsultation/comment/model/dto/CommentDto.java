package com.code4ro.legalconsultation.comment.model.dto;

import com.code4ro.legalconsultation.comment.model.persistence.CommentStatus;
import com.code4ro.legalconsultation.core.model.dto.BaseEntityDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentDto extends BaseEntityDto {
    private String text;
    private Date lastEditDateTime;
    private CommentStatus status;
}
