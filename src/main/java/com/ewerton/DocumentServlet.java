package com.ewerton;

import com.google.common.io.ByteStreams;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/documents/*")
public class DocumentServlet extends HttpServlet {

    private DocumentRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = new DocumentRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println(request.getPathInfo());
        //System.out.println(request.getContextPath());
        //request.getHeaderNames().asIterator().forEachRemaining(s -> System.out.println(s + " " + request.getHeader(s)));

        String contentType = request.getContentType();
        ServletInputStream inputStream = request.getInputStream();

        byte[] documentBytes = getBytes(inputStream);
        DocumentType documentType = ContentType.TEXT.equals(contentType) ? DocumentType.TEXT : DocumentType.FILE;

        Document document = new Document(documentType, documentBytes);
        document = repository.create(document);
        String resultId = document.getDocId();

        response.setStatus(201);
        response.setContentType(ContentType.TEXT);
        response.getWriter().println(resultId);

        repository.print();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getPathInfo());
        System.out.println(request.getContextPath());
        request.getHeaderNames().asIterator().forEachRemaining(s -> System.out.println(s + " " + request.getHeader(s)));

        String docId = request.getPathInfo().substring(1);

        Optional<Document> document = repository.findByDocId(docId);

        PrintWriter writer = response.getWriter();
        document.ifPresent(d -> writer.println(new String(d.getContent())));


        response.setStatus(200);
        response.setContentType(ContentType.TEXT);

        repository.print();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String docId = request.getPathInfo().substring(1);

        String contentType = request.getContentType();
        ServletInputStream inputStream = request.getInputStream();
        byte[] documentBytes = getBytes(inputStream);
        DocumentType documentType = ContentType.TEXT.equals(contentType) ? DocumentType.TEXT : DocumentType.FILE;

        Document document = new Document(documentType, documentBytes);

        repository.update(docId, document);

        response.setStatus(204);

        repository.print();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String docId = request.getPathInfo().substring(1);

        repository.remove(docId);

        response.setStatus(204);

        repository.print();
    }

    private byte[] getBytes(ServletInputStream inputStream) throws IOException {
        return ByteStreams.toByteArray(inputStream);
    }
}
