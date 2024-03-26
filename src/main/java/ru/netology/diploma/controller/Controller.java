package ru.netology.diploma.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diploma.response.ListResponse;
import ru.netology.diploma.request.PutRequest;
import ru.netology.diploma.service.ClientFileService;

import java.io.*;
import java.util.List;


@RestController
@AllArgsConstructor
public class Controller {

    private ClientFileService clientFileService;

    @PostMapping(value = "/file")
    @ResponseStatus(value = HttpStatus.OK)
    public void post(@RequestParam(name = "filename") String fileName,
                     @RequestBody MultipartFile file) throws IOException {

        clientFileService.createNewFile(fileName, file);
    }

    @GetMapping("/list")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ListResponse> get() {

        return clientFileService.getList();
    }

    @GetMapping(value = "/file")
    @ResponseStatus(value = HttpStatus.OK)
    public String read(@RequestParam(name = "filename") String fileName) {

        return clientFileService.readFile(fileName);
    }

    @PutMapping(value = "/file")
    @ResponseStatus(value = HttpStatus.OK)
    public void put(@RequestParam(name = "filename") String fileName,
                    @RequestBody PutRequest newName) {

        clientFileService.changeFileName(fileName, newName.getFilename());
    }

    @DeleteMapping(value = "/file")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@RequestParam(name = "filename") String fileName) throws IOException {

        clientFileService.deleteClientFilesByFileNameAndClient_Login(fileName);
    }
}
