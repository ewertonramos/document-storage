package com.ewerton.controller;

import com.ewerton.persistence.DocumentRepository;
import com.ewerton.service.DocumentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.ewerton.controller.DocumentControllerTest.DocumentControllerTestConfig;

@WebMvcTest(DocumentController.class)
@ContextConfiguration(classes = {DocumentControllerTestConfig.class})
public class DocumentControllerTest {

    public static final String BASE_PATH = "/v1.0/storage/documents";
    public static final String DOCUMENT_ID_PATH = BASE_PATH +"/{docId}";
    public static final String TEXT_PLAIN_UTF8 = MediaType.TEXT_PLAIN + ";charset=UTF8";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DocumentController controller;

    @Test
    public void should_create_document() throws Exception {
        //given
        String message = "hello world";

        //when
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.post(BASE_PATH)
                    .contentType(TEXT_PLAIN_UTF8).content(message.getBytes()))
                .andDo(MockMvcResultHandlers.print());

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(TEXT_PLAIN_UTF8))
                .andExpect(MockMvcResultMatchers.header().longValue(HttpHeaders.CONTENT_LENGTH, 20));
    }

    @Test
    public void should_fetch_document() throws Exception {
        //given
        String message = "hello world";
        String docId = createDocument(message);

        //when
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.get(DOCUMENT_ID_PATH, docId))
                .andDo(MockMvcResultHandlers.print());

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TEXT_PLAIN_UTF8))
                .andExpect(MockMvcResultMatchers.header().longValue(HttpHeaders.CONTENT_LENGTH, message.length()))
                .andExpect(MockMvcResultMatchers.content().string(message));

        mockMvc
                .perform(MockMvcRequestBuilders.get(DOCUMENT_ID_PATH, "docId"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void should_update_document() throws Exception {
        //given
        String message = "hello world";
        String docId = createDocument(message);
        String messageUpdate = "goodbye world";

        //when
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.put(DOCUMENT_ID_PATH, docId)
                        .contentType(TEXT_PLAIN_UTF8).content(messageUpdate))
                .andDo(MockMvcResultHandlers.print());

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc
                .perform(MockMvcRequestBuilders.get(DOCUMENT_ID_PATH, docId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TEXT_PLAIN_UTF8))
                .andExpect(MockMvcResultMatchers.header().longValue(HttpHeaders.CONTENT_LENGTH, messageUpdate.length()))
                .andExpect(MockMvcResultMatchers.content().string(messageUpdate));
    }

    @Test
    public void should_delete_document() throws Exception {
        //given
        String message = "hello world";
        String docId = createDocument(message);

        //when
        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders.delete(DOCUMENT_ID_PATH, docId))
                .andDo(MockMvcResultHandlers.print());

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc
                .perform(MockMvcRequestBuilders.get(DOCUMENT_ID_PATH, docId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private String createDocument(String message) throws Exception {
        return mockMvc
                .perform(MockMvcRequestBuilders.post(BASE_PATH)
                        .contentType(TEXT_PLAIN_UTF8)
                        .content(message))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
    }

    @Configuration
    @ComponentScan
    static class DocumentControllerTestConfig {

        @Bean
        DocumentService getDocumentService() {
            return new DocumentService(new DocumentRepository());
        }
    }
}