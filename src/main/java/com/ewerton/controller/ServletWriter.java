package com.ewerton.controller;

import com.ewerton.model.Document;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public interface ServletWriter {
    void writeDocument(PrintWriter writer, Document document) throws IOException;
}
