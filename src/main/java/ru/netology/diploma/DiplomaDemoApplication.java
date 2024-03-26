package ru.netology.diploma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.netology.diploma.client.Client;

@SpringBootApplication
public class DiplomaDemoApplication{
    public static void main(String[] args) {
        SpringApplication.run(DiplomaDemoApplication.class, args);}
}
