package com.code4ro.legalconsultation.model.dto;

import com.code4ro.legalconsultation.model.persistence.CommentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class CommentDto {
    private String text;
    private Date lastEditDateTime;
    private CommentStatus status;
}
