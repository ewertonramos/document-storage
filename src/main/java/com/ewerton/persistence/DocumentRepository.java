package com.ewerton.persistence;

import com.ewerton.model.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class DocumentRepository implements Repository {

    private Map<String, Document> repository = new HashMap<>();

    public Document create(Document document) {
        document.setDocId(UUID.randomUUID().toString());
        repository.put(document.getDocId(), document);
        return document;
    }

    public Optional<Document> findBy(String docId) {
        return Optional.ofNullable(repository.get(docId));
    }

    public void update(String docId, Document document) {
        Optional<Document> documentById = findBy(docId);
        if (documentById.isPresent()) {
            Document documentUpdate = documentById.get();
            documentUpdate.setDocumentType(document.getDocumentType());
            documentUpdate.setContent(document.getContent());
            repository.put(docId, documentUpdate);
        } else {
            throw new IllegalStateException("Not present");
        }
    }

    public void remove(String docId) {
        repository.remove(docId);
    }

    public void print() {
        System.out.println("++++++++++++++++++++++++++++++");
        repository.forEach((k, v) -> System.out.println(v));
        System.out.println("++++++++++++++++++++++++++++++");
    }
}
