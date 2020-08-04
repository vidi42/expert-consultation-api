package com.code4ro.legalconsultation.document.node.repository;

import com.code4ro.legalconsultation.document.node.model.persistence.DocumentNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentNodeRepository extends JpaRepository<DocumentNode, UUID> {
}
