package ru.netology.diploma.service;

import ru.netology.diploma.client.Client;


public interface ClientService {
    Client findClient(String clientLog);

    boolean existClient(String clientLogin);
}
