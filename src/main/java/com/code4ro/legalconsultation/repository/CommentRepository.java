package com.code4ro.legalconsultation.repository;

import com.code4ro.legalconsultation.model.persistence.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    @Query("SELECT comment FROM Comment comment " +
            " JOIN FETCH comment.article article" +
            " WHERE article.id = :articleId")
    List<Comment> findByArticleId(UUID articleId);

    @Query("SELECT comment FROM Comment comment " +
            " JOIN FETCH comment.chapter chapter" +
            " WHERE chapter.id = :chapterId")
    List<Comment> findByChapterId(UUID chapterId);

    @Query("SELECT comment FROM Comment comment " +
            " JOIN FETCH comment.document document" +
            " WHERE document.id = :documentId")
    List<Comment> findByDocumentId(UUID documentId);
}
