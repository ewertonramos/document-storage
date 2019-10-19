package com.ewerton.controller;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamReader {
    byte[] readDocumentBytes(InputStream inputStream) throws IOException;
}
