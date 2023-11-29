package com.store.controllers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.store.DTO.ClientDTO;
import com.store.entities.Client;
import com.store.exceptions.MiException;
import com.store.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //No devuelve ninguna vista, solo info
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    ClientService clientService;
    @GetMapping("/")
    public List<Client> getUsers(){
        return clientService.getClientList();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO createUser(@RequestBody ClientDTO clientDTO) throws MiException {
        return clientService.createClient(clientDTO);
    }
    @GetMapping("/{id}")
    public ClientDTO getClientById(@PathVariable String id) throws MiException {
        return clientService.getClientByID(id);
    }
    @PutMapping("/modify/{id}")
    public ClientDTO modifyById(@PathVariable String id, @RequestBody ClientDTO clientDTO) throws MiException, JsonMappingException {
        return clientService.modifyClient(id, clientDTO);
    }
}
