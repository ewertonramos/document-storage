package com.ewerton.controller;

import java.io.IOException;
import java.io.InputStream;

public class DocumentReader implements InputStreamReader {
    @Override
    public byte[] readDocumentBytes(InputStream inputStream) throws IOException {
        return inputStream.readAllBytes();
    }
}
