package com.store.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.DTO.ClientDTO;
import com.store.entities.Client;
import com.store.exceptions.MiException;
import com.store.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ObjectMapper mapper;
    @Transactional
    public ClientDTO createClient(ClientDTO clientDTO) throws MiException {
        validate(clientDTO.getName(), clientDTO.getEmail(), clientDTO.getPhone());
        Client client;
        client = mapper.convertValue(clientDTO, Client.class); //Json.mapper.convertValue(map, type);
        client.setUploadedDate(LocalDate.now());
        clientRepository.save(client);
        return clientDTO;
    }
    @Transactional
    public ClientDTO modifyClient(String id, ClientDTO clientDTO) throws MiException{
        validate(clientDTO.getName(), clientDTO.getEmail(), clientDTO.getPhone());
        Optional<Client> response = clientRepository.findById(id) ;
        if (response.isPresent()){
            Client client = response.get();
            client.setName(clientDTO.getName());
            client.setEmail(clientDTO.getEmail());
            client.setPhone(clientDTO.getPhone());
            clientRepository.save(client);
        }
        return clientDTO;
    }
    @Transactional
    public void deactivateClient(String id) throws MiException{
        if(id == null || id.trim().isEmpty()){
            throw new MiException("Id cannot be empty or null");
        }
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        client.setActive(false);
        clientRepository.save(client);
    }
    public Client getClientByID(String id) throws MiException{
        if(id == null || id.trim().isEmpty()){
            throw new MiException("Id cannot be empty or null");
        }
        return clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
    }
    public List<Client> getClientList(){
        return (ArrayList<Client>) clientRepository.findAll();
    }

    public void validate(String name, String email, String phone) throws MiException {
        if(name == null || name.trim().isEmpty()){
            throw new MiException("Name cannot be empty or null");
        }
        if(email == null || email.trim().isEmpty()){
            throw new MiException("email cannot be empty");
        }
        if(phone == null || phone.trim().isEmpty()){
            throw new MiException("phone cannot be empty");
        }
    }
}
