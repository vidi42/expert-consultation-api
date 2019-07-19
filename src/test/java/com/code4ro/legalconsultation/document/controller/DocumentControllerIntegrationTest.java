package com.code4ro.legalconsultation.document.controller;

import com.code4ro.legalconsultation.common.controller.AbstractControllerIntegrationTest;
import com.code4ro.legalconsultation.model.dto.DocumentView;
import com.code4ro.legalconsultation.repository.DocumentBreakdownRepository;
import com.code4ro.legalconsultation.repository.DocumentConsolidatedRepository;
import com.code4ro.legalconsultation.repository.DocumentMetadataRepository;
import com.code4ro.legalconsultation.util.RandomObjectFiller;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DocumentControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Autowired
    private DocumentMetadataRepository documentMetadataRepository;
    @Autowired
    private DocumentBreakdownRepository documentBreakdownRepository;
    @Autowired
    private DocumentConsolidatedRepository documentConsolidatedRepository;

    @Test
    @WithMockUser
    public void saveDocument() throws Exception{

        final DocumentView randomView = RandomObjectFiller.createAndFill(DocumentView.class);
        final MockMultipartFile randomFile = new MockMultipartFile("file", "file.doc", "text/plain", "text".getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/api/document")
                .file(randomFile)
                .param("title", randomView.getTitle())
                .param("number", randomView.getDocumentNumber().toString())
                .param("initiator", randomView.getInitiator())
                .param("type", randomView.getType().toString())
                .param("creationDate", "09/09/2018")
                .param("receiveDate", "10/09/2018"))
                .andExpect(status().isCreated());

        assertThat(documentMetadataRepository.count()).isEqualTo(1);
        assertThat(documentBreakdownRepository.count()).isEqualTo(1);
        assertThat(documentConsolidatedRepository.count()).isEqualTo(1);
    }
}
