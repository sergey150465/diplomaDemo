package ru.netology.diploma.service;

import org.springframework.web.multipart.MultipartFile;
import ru.netology.diploma.client.ClientFiles;
import ru.netology.diploma.response.ListResponse;

import java.io.IOException;
import java.util.List;

public interface ClientFileService {

    List<ClientFiles> findClientFilesByClientLogin(String login) throws IOException;

    boolean createNewFile(String fileName, MultipartFile file) throws IOException;

    boolean deleteClientFilesByFileNameAndClient_Login(String fileName) throws IOException;

    ClientFiles findClientFileByFileNameAndClient_Login(String fileName, String clientLogin);

    boolean existsClientFile(String fileName, String clientLogin);

    List<ListResponse> getList();

    String readFile(String fileName);

    ClientFiles changeFileName(String fileName, String newName);
}
