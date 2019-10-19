package com.ewerton;

public class Document {

    private String docId;
    private DocumentType documentType;
    private byte[] content;

    public Document() {
    }

    public Document(DocumentType documentType, byte[] content) {
        this.documentType = documentType;
        this.content = content;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Document{" +
                "docId='" + docId + '\'' +
                ", documentType=" + documentType +
                '}';
    }
}
