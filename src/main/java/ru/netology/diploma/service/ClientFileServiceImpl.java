package ru.netology.diploma.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diploma.client.ClientFiles;
import ru.netology.diploma.repository.ClientFilesRepository;
import ru.netology.diploma.response.ListResponse;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//@AllArgsConstructor
@Service
public class ClientFileServiceImpl implements ClientFileService {

    private ClientFilesRepository clientFilesRepository;
    private ClientService clientService;
    private ClientFileServiceImpl clientFileService;

    public ClientFileServiceImpl(ClientFilesRepository clientFilesRepository,
                                 ClientService clientService,
                                 @Lazy ClientFileServiceImpl clientFileService) {
        this.clientFilesRepository = clientFilesRepository;
        this.clientService = clientService;
        this.clientFileService = clientFileService;
    }

    @Override
    @Transactional
    public List<ClientFiles> findClientFilesByClientLogin(String login) {
        return clientFilesRepository.findByClient_Login(login);
    }

    @Override
    public boolean createNewFile(String fileName, MultipartFile file) throws IOException {

        var login = myAuthenticationLogin();
        if (clientFileService.existsClientFile(fileName, login)) {
            throw new IOException();
        }
        var dbClient = clientService.findClient(login);
        Optional<ClientFiles> clientFile = Optional.of(new ClientFiles());
        clientFile.get().setClient(dbClient);
        clientFile.get().setFileName(fileName);
        var fileContent = file.getBytes();
        clientFile.get().setFileContent(fileContent);
        clientFilesRepository.save(clientFile.get());
        return true;
    }

    @Override
    @Transactional
    public boolean deleteClientFilesByFileNameAndClient_Login(String fileName) throws IOException {
        var login = myAuthenticationLogin();
        if (!clientFileService.existsClientFile(fileName, login)) {
            throw new EntityNotFoundException();
        }
        clientFilesRepository.deleteClientFilesByFileNameAndClient_Login(fileName, login)
                .orElseThrow(IOException::new);
        return true;
    }

    @Override
    @Transactional
    public ClientFiles findClientFileByFileNameAndClient_Login(String fileName, String clientLogin) {
        return clientFilesRepository
                .findClientFilesByFileNameAndClient_Login(fileName, clientLogin)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public boolean existsClientFile(String fileName, String clientLogin) {
        return clientFilesRepository
                .existsClientFilesByFileNameAndClient_Login(fileName, clientLogin);
    }

    @Override
    public List<ListResponse> getList() {

        var login = myAuthenticationLogin();
        var dbClientFiles = clientFileService.findClientFilesByClientLogin(login);
        List<ListResponse> nameSize = new ArrayList<>();
        dbClientFiles.forEach(db -> {
            nameSize.add
                    (new ListResponse(
                            db.getFileName(),
                            db.getFileContent().length
                    ));
        });
        return nameSize;
    }

    @Override
    public String readFile(String fileName) {
        var login = myAuthenticationLogin();
        var dbClientFile = clientFileService
                .findClientFileByFileNameAndClient_Login(fileName, login);

        return Arrays.toString(dbClientFile.getFileContent());
    }

    @Override
    public ClientFiles changeFileName(String fileName, String newName) {

        var login = myAuthenticationLogin();
        var dbClientFile = clientFileService
                .findClientFileByFileNameAndClient_Login(fileName, login);
        dbClientFile.setFileName(newName);
        return clientFilesRepository.save(dbClientFile);
    }

    public String myAuthenticationLogin() {

        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}
