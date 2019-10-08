package com.code4ro.legalconsultation.controller.comments;

import com.code4ro.legalconsultation.common.controller.AbstractControllerIntegrationTest;
import com.code4ro.legalconsultation.model.persistence.Article;
import com.code4ro.legalconsultation.model.persistence.Comment;
import com.code4ro.legalconsultation.model.persistence.User;
import com.code4ro.legalconsultation.repository.ArticleRepository;
import com.code4ro.legalconsultation.repository.CommentRepository;
import com.code4ro.legalconsultation.repository.UserRepository;
import com.code4ro.legalconsultation.util.RandomObjectFiller;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ArticleCommentControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @WithMockUser
    public void fetchAllComments() throws Exception {
        Article article = RandomObjectFiller.createAndFillWithBaseEntity(Article.class);
        article.setId(null);
        article = articleRepository.save(article);
        final List<Comment> comments = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Comment comment = RandomObjectFiller.createAndFillWithBaseEntity(Comment.class);
            User user = RandomObjectFiller.createAndFillWithBaseEntity(User.class);
            user = userRepository.save(user);

            comment.setOwner(user);
            comment.setArticle(article);
            comments.add(comment);
            comment.setChapter(null);
            comment.setDocument(null);
        }
        commentRepository.saveAll(comments);
        mvc.perform(get("/api/article/" + article.getId().toString())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }
}
