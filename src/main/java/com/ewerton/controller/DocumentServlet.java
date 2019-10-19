package com.ewerton.controller;

import com.ewerton.model.Document;
import com.ewerton.persistence.DocumentRepository;
import com.ewerton.persistence.Repository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/documents/*")
public class DocumentServlet extends HttpServlet {

    private Repository repository;
    private InputStreamReader inputStreamReader;
    private OutputStreamWriter outputStreamWriter;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = new DocumentRepository();
        inputStreamReader = new DocumentReader();
        outputStreamWriter = new DocumentWriter();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] documentBytes = inputStreamReader.readDocumentBytes(request.getInputStream());
        String contentType = request.getContentType();
        Document document = new Document(contentType, documentBytes);

        document = repository.create(document);
        String resultId = document.getDocId();

        response.setContentType("text/plain");
        response.getWriter().println(resultId);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String docId = PathHandler.getResourceId(request.getPathInfo()).orElse(null);
        Optional<Document> document = repository.findBy(docId);
        if (document.isPresent()) {
            outputStreamWriter.writeOutput(response.getOutputStream(), document.get().getContent());
            response.setContentType(document.get().getDocumentType());
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String docId = PathHandler.getResourceId(request.getPathInfo()).orElse(null);
        byte[] documentBytes = inputStreamReader.readDocumentBytes(request.getInputStream());
        String documentType = request.getContentType();
        Document document = new Document(documentType, documentBytes);
        if (repository.update(docId, document)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String docId = PathHandler.getResourceId(request.getPathInfo()).orElse(null);
        if (repository.remove(docId)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
