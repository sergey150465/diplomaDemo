package ru.netology.diploma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.diploma.client.ClientFiles;

import java.util.List;
import java.util.Optional;

public interface ClientFilesRepository extends JpaRepository<ClientFiles, Integer> {

    List<ClientFiles> findByClient_Login(String log);

    Optional <ClientFiles> findClientFilesByFileNameAndClient_Login(String fileName, String clientLogin);

    Optional<ClientFiles> deleteClientFilesByFileNameAndClient_Login(String fileName, String clientLogin);

    boolean existsClientFilesByFileNameAndClient_Login (String fileName, String clientLogin);
}
