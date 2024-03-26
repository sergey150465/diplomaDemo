package ru.netology.diploma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.diploma.client.Client;

import java.util.Optional;


@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findClientByLogin(String login);

    Client findByLogin(String login);

    boolean existsClientByLogin(String clientLogin);
}
