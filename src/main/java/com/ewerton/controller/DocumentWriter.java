package com.ewerton.controller;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

public class DocumentWriter implements OutputStreamWriter {
    @Override
    public void writeOutput(ServletOutputStream outputStream, byte[] content) throws IOException {
        outputStream.write(content);
    }
}
