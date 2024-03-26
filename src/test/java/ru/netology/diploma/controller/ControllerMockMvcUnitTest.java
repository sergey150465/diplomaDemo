package ru.netology.diploma.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import ru.netology.diploma.client.Client;
import ru.netology.diploma.client.ClientFiles;
import ru.netology.diploma.request.PutRequest;
import ru.netology.diploma.response.ListResponse;
import ru.netology.diploma.security.JWTUtil;
import ru.netology.diploma.service.ClientFileService;
import ru.netology.diploma.service.ClientService;
import ru.netology.diploma.service.CustomUserDetailsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(Controller.class)
public class ControllerMockMvcUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientFileService clientFileService;

    @MockBean
    private ClientService clientService;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private Controller controller;


    @Test
    @DisplayName("Post /file сохраняет файл в базе и возвращает HTTP статус 200 ОК")
    void postNewFileThenGivenOkStatus() throws Exception {
        String fieldName = "filename";
        String fileName = "Serg.png";
        byte[] content = new byte[]{(byte) 12345};

        MockMultipartFile file = new MockMultipartFile(
                fileName,
                fileName + "test",
                "multipart/form-data/plain",
                content);

        when(clientFileService.createNewFile(fileName, file))
                .thenReturn(true);


        mockMvc.perform(post("/file")
                        .param(fieldName, fileName)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .content(String.valueOf(file)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get /list возвращает список имен файлов и их размер")
    void getListOfFileNamesAndSize() throws Exception {
        byte[] content1 = new byte[]{(byte) 54321};
        byte[] content2 = new byte[]{(byte) 12345};
        List<ListResponse> listResponse = new ArrayList<>();
        listResponse.add(new ListResponse("Serg.png", content1.length));
        listResponse.add(new ListResponse("Olga.png", content2.length));
        when(clientFileService.getList()).thenReturn(listResponse);
        mockMvc.perform(get("/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(listResponse)))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].filename", containsInAnyOrder("Serg.png", "Olga.png")));
    }

    @Test
    @DisplayName("DELETE /file удаляет файл и возвращает HTTP статус 200 ОК")
    void deleteByName() throws Exception {


        Client client = new Client(1, "log", "pass", "USER1");
        byte[] content = new byte[]{(byte) 12345};
        ClientFiles clientFile = new ClientFiles(
                1,
                "Serg.png",
                content,
                client);

        when(clientFileService
                .deleteClientFilesByFileNameAndClient_Login("Serg.png"))
                .thenReturn(true);

        mockMvc.perform(delete("/file")
                        .param("filename", "Serg.png")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /file возвращает имя файла и содержимое по имени")
    void getFileNameAndContentByFileName() throws Exception {
        byte[] content = new byte[]{(byte) 12345};
        when(clientFileService
                .readFile(anyString()))
                .thenReturn(Arrays.toString(content));
        mockMvc.perform(get("/file")
                        .param("filename", "Serg.png")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(Arrays.toString(content)));
    }


    @Test
    @DisplayName("PUT /file меняет имя файла и возвращает HTTP статус 200 ОК")
    void putByName() throws Exception {
        String fileName = "Serg.png";
        Client client = new Client(1, "log", "pass", "USER1");
        byte[] content = new byte[]{(byte) 12345};
        PutRequest body = new PutRequest("475.png");
        ClientFiles clientFile = new ClientFiles(
                1,
                "Serg.png",
                content,
                client);
        when(clientFileService.changeFileName(fileName, body.getFilename()))
                .thenReturn(clientFile);
        mockMvc.perform(put("/file")
                        .param("filename", fileName)
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void test(){
        assertThat(controller).isNotNull();
    }

    @Test
    void accessDenied() throws Exception {
        mockMvc.perform(post("/login"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void accessGranted() throws Exception {
        mockMvc.perform(get("/list"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
