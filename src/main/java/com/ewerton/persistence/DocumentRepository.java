package com.ewerton.persistence;

import com.ewerton.model.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Repository
public class DocumentRepository implements Repository {

    private final Map<String, Document> repository = new HashMap<>();

    public DocumentRepository() {
    }

    public Document create(Document document) {
        document.setDocId(generateId());
        repository.put(document.getDocId(), document);
        return document;
    }

    private String generateId() {
        String replaceUuid = UUID.randomUUID().toString().replace("-", "");
        return replaceUuid.substring(replaceUuid.length()-20).toUpperCase();
    }

    public Optional<Document> findBy(String docId) {
        return Optional.ofNullable(repository.get(docId));
    }

    public boolean update(String docId, Document document) {
        Optional<Document> documentById = findBy(docId);
        if (documentById.isPresent()) {
            Document documentUpdate = documentById.get();
            documentUpdate.setDocumentType(document.getDocumentType());
            documentUpdate.setContent(document.getContent());
            repository.put(docId, documentUpdate);
            return true;
        } else {
            return false;
        }
    }

    public boolean remove(String docId) {
        return repository.remove(docId)!= null;
    }

}
