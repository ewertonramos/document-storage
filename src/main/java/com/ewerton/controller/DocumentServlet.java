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
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/documents/*")
public class DocumentServlet extends HttpServlet {

    private Repository repository;
    private ServletReader servletReader;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = new DocumentRepository();
        servletReader = new DocumentReader();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        byte[] documentBytes = servletReader.readDocumentBytes(request.getInputStream());
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
        if (hasOneResourceId(request.getPathInfo())) {
            String docId = request.getPathInfo().substring(1);

            String responseContentType;
            Optional<Document> document = repository.findBy(docId);
            if (document.isPresent()) {
                responseContentType = document.get().getDocumentType();
                writeDocument(response.getWriter(), document.get());
            } else {
                throw new IllegalStateException("sl");
            }

            response.setContentType(responseContentType);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (hasOneResourceId(request.getPathInfo())) {
            String docId = request.getPathInfo().substring(1);

            byte[] documentBytes = servletReader.readDocumentBytes(request.getInputStream());
            String documentType = request.getContentType();
            Document document = new Document(documentType, documentBytes);

            repository.update(docId, document);

            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (hasOneResourceId(request.getPathInfo())) {
            String docId = request.getPathInfo().substring(1);

            repository.remove(docId);

            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response);
        System.out.println("===============================");
        System.out.println(request.getPathInfo());
        System.out.println(request.getContextPath());
        request.getHeaderNames().asIterator().forEachRemaining(s -> System.out.println(s + " " + request.getHeader(s)));
        repository.print();
        System.out.println("===============================");
    }

    private boolean hasOneResourceId(String pathInfo) {
        return pathInfo.split("/").length == 1;
    }

    private void writeDocument(PrintWriter writer, Document document) throws IOException {
        writer.println(new String(document.getContent()));
    }
}
