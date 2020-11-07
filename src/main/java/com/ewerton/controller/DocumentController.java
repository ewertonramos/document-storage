package com.ewerton.controller;

import com.ewerton.dto.DocumentDto;
import com.ewerton.service.DocumentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1.0/storage")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(path = "/documents")
    public ResponseEntity<Object> create(@RequestHeader HttpHeaders headers, @RequestBody byte[] body) {
        String documentId = documentService.createDocument(headers.getContentType(), body);
        return ResponseEntity.status(HttpStatus.CREATED).body(documentId);
    }

    @GetMapping(path = "documents/{docId}")
    public ResponseEntity<Object> find(@PathVariable String docId, @RequestHeader HttpHeaders headers) {
        Optional<DocumentDto> documentOpt = documentService.getDocument(docId);
        if (documentOpt.isPresent()) {
            DocumentDto documentDto = documentOpt.get();
            String contentType;
            if (!headers.containsKey(HttpHeaders.ACCEPT)) {
                contentType = documentDto.getDocumentType() != null ?
                        documentDto.getDocumentType(): MediaType.APPLICATION_OCTET_STREAM_VALUE;
            } else {
                contentType = headers.getAccept().get(0).toString();
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(h -> h.add(HttpHeaders.CONTENT_TYPE, contentType))
                    .body(documentDto.getContent());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(path = "documents/{docId}")
    public ResponseEntity<Object> update(@PathVariable String docId, @RequestHeader HttpHeaders headers, @RequestBody byte[] body) {
        boolean updated = documentService.updateDocumentById(docId, headers.getContentType(), body);
        if (updated) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "documents/{docId}")
    public ResponseEntity<Object> delete(@PathVariable String docId) {
        boolean deleted = documentService.deleteDocument(docId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
