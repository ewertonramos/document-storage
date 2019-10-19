package com.ewerton.model;

public class Document {

    private java.lang.String docId;
    private String documentType;
    private byte[] content;

    public Document() {
    }

    public Document(String documentType, byte[] content) {
        this.documentType = documentType;
        this.content = content;
    }

    public java.lang.String getDocId() {
        return docId;
    }

    public void setDocId(java.lang.String docId) {
        this.docId = docId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public java.lang.String toString() {
        return "Document{" +
                "docId='" + docId + '\'' +
                ", documentType=" + documentType +
                '}';
    }
}
