package com.ewerton.controller;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

public interface OutputStreamWriter {
    void writeOutput(ServletOutputStream outputStream, byte[] content) throws IOException;
}
