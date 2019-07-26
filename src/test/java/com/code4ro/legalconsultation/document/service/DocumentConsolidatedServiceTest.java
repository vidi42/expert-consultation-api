package com.code4ro.legalconsultation.document.service;

import com.code4ro.legalconsultation.model.persistence.DocumentConsolidated;
import com.code4ro.legalconsultation.repository.DocumentConsolidatedRepository;
import com.code4ro.legalconsultation.service.impl.DocumentConsolidatedService;
import com.code4ro.legalconsultation.util.RandomObjectFiller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DocumentConsolidatedServiceTest {

    @Mock
    private DocumentConsolidatedRepository documentConsolidatedRepository;

    @InjectMocks
    private DocumentConsolidatedService documentConsolidatedService;

    @Test
    public void getDocument(){
        final UUID uuid = UUID.randomUUID();
        when(documentConsolidatedRepository.findById(any(UUID.class))).thenReturn(Optional.of(new DocumentConsolidated()));

        documentConsolidatedService.findOne(uuid.toString());
        verify(documentConsolidatedRepository).findById(uuid);
    }

    @Test
    public void getAllDocuments(){
        documentConsolidatedService.findAll();
        verify(documentConsolidatedRepository).findAll();
    }

    @Test
    public void saveDocument(){
        DocumentConsolidated documentConsolidated = RandomObjectFiller.buildRandomDocumentConsolidated();
        documentConsolidatedService.saveOne(documentConsolidated);

        verify(documentConsolidatedRepository).save(documentConsolidated);
    }

    @Test
    public void deleteDocument(){
        final String id = UUID.randomUUID().toString();
        documentConsolidatedService.deleteById(id);

        verify(documentConsolidatedRepository).deleteById(UUID.fromString(id));
    }
}
