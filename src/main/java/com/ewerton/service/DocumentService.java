package com.ewerton.service;

import com.ewerton.dto.DocumentDto;
import com.ewerton.model.Document;
import com.ewerton.persistence.Repository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    private final Repository repository;

    public DocumentService(Repository repository) {
        this.repository = repository;
    }

    public String createDocument(MediaType contentType, byte[] body) {
        String contentTypeString = null;
        if (contentType != null) {
            contentTypeString = MediaType.toString(List.of(contentType));
        }
        Document document = new Document(contentTypeString, body);
        document = repository.create(document);
        return document.getDocId();

    }

    public Optional<DocumentDto> getDocument(String docId) {
        Optional<Document> documentOpt = repository.findBy(docId);
        return documentOpt.map(this::convertToDto);
    }

    private DocumentDto convertToDto(Document document) {
        DocumentDto dto = new DocumentDto();
        BeanUtils.copyProperties(document, dto);
        return dto;
    }

    public boolean updateDocumentById(String docId, MediaType contentType, byte[] body) {
        Document document = new Document(MediaType.toString(List.of(contentType)), body);
        return repository.update(docId, document);
    }

    public boolean deleteDocument(String docId) {
        return repository.remove(docId);
    }
}
