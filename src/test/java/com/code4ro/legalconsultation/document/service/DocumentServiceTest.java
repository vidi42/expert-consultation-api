package com.code4ro.legalconsultation.document.service;

import com.code4ro.legalconsultation.common.exceptions.ResourceNotFoundException;
import com.code4ro.legalconsultation.model.persistence.DocumentConsolidated;
import com.code4ro.legalconsultation.service.impl.DocumentConsolidatedService;
import com.code4ro.legalconsultation.service.impl.DocumentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class DocumentServiceTest {

    @Mock
    private DocumentConsolidatedService documentConsolidatedService;

    @InjectMocks
    private DocumentServiceImpl documentService;


    @Test
    public void getDocument(){
        final String uuid = UUID.randomUUID().toString();
        when(documentConsolidatedService.findOne(any(String.class))).thenReturn(Optional.of(new DocumentConsolidated()));
        documentService.fetchOne(uuid);

        verify(documentConsolidatedService).findOne(uuid);
    }

    @Test
    public void getDocumentConsolidated(){
        final String uuid = UUID.randomUUID().toString();
        when(documentConsolidatedService.findOne(any(String.class))).thenReturn(Optional.of(new DocumentConsolidated()));
        documentService.fetchOneConsolidated(uuid);

        verify(documentConsolidatedService).findOne(uuid);
    }

    @Test
    public void getAllDocuments(){
        documentService.fetchAll();

        verify(documentConsolidatedService).findAll();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteDocument(){
        final String uuid = UUID.randomUUID().toString();
        documentService.deleteById(uuid);

        verify(documentConsolidatedService).deleteById(uuid);
    }
}
