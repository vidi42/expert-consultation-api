package com.code4ro.legalconsultation.document.controller;

import com.code4ro.legalconsultation.common.controller.AbstractControllerIntegrationTest;
import com.code4ro.legalconsultation.model.dto.DocumentView;
import com.code4ro.legalconsultation.model.persistence.*;
import com.code4ro.legalconsultation.repository.DocumentBreakdownRepository;
import com.code4ro.legalconsultation.repository.DocumentConsolidatedRepository;
import com.code4ro.legalconsultation.repository.DocumentMetadataRepository;
import com.code4ro.legalconsultation.service.api.DocumentService;
import com.code4ro.legalconsultation.util.RandomObjectFiller;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DocumentControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Autowired
    private DocumentMetadataRepository documentMetadataRepository;
    @Autowired
    private DocumentBreakdownRepository documentBreakdownRepository;
    @Autowired
    private DocumentConsolidatedRepository documentConsolidatedRepository;
    @Autowired
    private DocumentService documentService;

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

    @Test
    @WithMockUser
    public void getDocument() throws Exception {

        DocumentConsolidated consolidated = saveSingleConsolidated();

        mvc.perform(MockMvcRequestBuilders.get("/api/document/" + consolidated.getId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(consolidated.getDocumentMetadata().getId().toString()))
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/api/document/" + consolidated.getId().toString() + "/consolidated")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(consolidated.getId().toString()))
                .andExpect(status().isOk());

        assertThat(documentMetadataRepository.count()).isEqualTo(1);
        assertThat(documentBreakdownRepository.count()).isEqualTo(1);
        assertThat(documentConsolidatedRepository.count()).isEqualTo(1);
    }

    @Test
    @WithMockUser
    public void listDocuments() throws Exception{
        DocumentConsolidated consolidated1 = saveSingleConsolidated();
        DocumentConsolidated consolidated2 = saveSingleConsolidated();

        mvc.perform(MockMvcRequestBuilders.get("/api/document")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(consolidated1.getDocumentMetadata().getId().toString()))
                .andExpect(jsonPath("$[1].id").value(consolidated2.getDocumentMetadata().getId().toString()))
                .andExpect(status().isOk());

        assertThat(documentMetadataRepository.count()).isEqualTo(2);
        assertThat(documentBreakdownRepository.count()).isEqualTo(2);
        assertThat(documentConsolidatedRepository.count()).isEqualTo(2);
    }

    @Test
    @WithMockUser
    public void deleteDocument() throws Exception{
        DocumentConsolidated consolidated = saveSingleConsolidated();

        mvc.perform(MockMvcRequestBuilders.delete("/api/document/" + consolidated.getId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertThat(documentMetadataRepository.count()).isEqualTo(0);
        assertThat(documentBreakdownRepository.count()).isEqualTo(0);
        assertThat(documentConsolidatedRepository.count()).isEqualTo(0);
    }

    private DocumentConsolidated saveSingleConsolidated(){
        DocumentConsolidated consolidated = RandomObjectFiller.buildRandomDocumentConsolidated();
        return documentConsolidatedRepository.save(consolidated);
    }
}
