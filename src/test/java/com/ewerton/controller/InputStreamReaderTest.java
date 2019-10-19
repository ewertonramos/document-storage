package com.ewerton.controller;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertArrayEquals;

public class InputStreamReaderTest {

    private InputStreamReader inputStreamReader;

    @Before
    public void setUp() throws Exception {
        inputStreamReader = new DocumentReader();
    }

    @Test
    public void should_read_empy_inputStream() throws IOException {
        //given
        byte[] expected = new byte[0];
        InputStream inputStream = InputStream.nullInputStream();

        //when
        byte[] result = inputStreamReader.readDocumentBytes(inputStream);

        //then
        assertArrayEquals(expected, result);
    }

    @Test
    public void should_read_inputStream() throws IOException {
        //given
        String inputString = "test";
        byte[] expected = inputString.getBytes();
        InputStream inputStream = new ByteArrayInputStream(expected);

        //when
        byte[] result = inputStreamReader.readDocumentBytes(inputStream);

        //then
        assertArrayEquals(expected, result);
    }
}