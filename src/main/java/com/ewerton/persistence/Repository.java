package com.ewerton.persistence;

import com.ewerton.model.Document;

import java.util.Optional;

public interface Repository {

    Document create(Document document);

    Optional<Document> findBy(String docId);

    void update(String docId, Document document);

    void remove(String docId);

    void print();
}
