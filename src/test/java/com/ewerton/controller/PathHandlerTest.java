package com.ewerton.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PathHandlerTest {

    private String input;
    private String expectedResult;

    public PathHandlerTest(String input, String expectedResult) {
        this.input = input;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][] {
                { "", null },
                { "/", null },
                { "/test", "test" },
                { "/test/second", "test" },
                { "//dummy", "" },
                { "/one/two/three", "one" }
        });
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void should_split_input() {
        assertEquals(expectedResult, PathHandler.getResourceId(input).orElse(null));
    }
}