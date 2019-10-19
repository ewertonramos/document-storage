package com.ewerton.persistence;

import com.ewerton.model.Document;

import java.util.Optional;

public interface Repository {

    Document create(Document document);

    Optional<Document> findBy(String docId);

    boolean update(String docId, Document document);

    boolean remove(String docId);

}
