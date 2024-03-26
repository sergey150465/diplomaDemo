package ru.netology.diploma.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.diploma.client.Client;
import org.springframework.stereotype.Service;
import ru.netology.diploma.repository.ClientRepository;

@AllArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    @Override
    @Transactional
    public Client findClient(String clientLogin) {
        return clientRepository.findClientByLogin(clientLogin).orElseThrow();
    }

    @Override
    public boolean existClient(String clientLogin) {
        return clientRepository.existsClientByLogin(clientLogin);
    }
}