package com.store.services;

import static org.junit.jupiter.api.Assertions.*;
import com.store.DTO.ClientDTO;
import com.store.entities.Client;
import com.store.exceptions.MiException;
import com.store.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClientServiceTest {
    @Autowired
    ClientService clientService;
    @MockBean
    private ClientRepository clientRepository;
    @Test
    public void shouldCreateClient() throws MiException {
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> {
            Client client = invocation.getArgument(0);
            client.setName("Natalia");
            return client;
        });
        ClientDTO expectedClientDTO = new ClientDTO("Natalia", "test@test.com", "0123456789");
        ClientDTO createdClientDTO = clientService.createClient(expectedClientDTO);
        assertEquals(expectedClientDTO.getName(),createdClientDTO.getName());
        assertEquals(expectedClientDTO,createdClientDTO);
    }
    @Test
    public void shouldUpdateClient() throws MiException{
        when(clientRepository.findById(any(String.class))).thenAnswer(invocation -> {
            String id = invocation.getArgument(0);
            Client mockClient = new Client(id, "Natalia Update", "test1@test.com", "0123456");
            return Optional.of(mockClient);
        });
        Client mockClient = new Client("NA000001", "Natalia Update", "test1@test.com", "0123456");
        when(clientRepository.save(any(Client.class))).thenReturn(mockClient);
        ClientDTO expectedClientDT0 = new ClientDTO("Natalia Update", "test1@test.com", "0123456");
        ClientDTO updatedClientDTO = clientService.modifyClient("NA000001", expectedClientDT0);
        assertEquals(expectedClientDT0, updatedClientDTO);
    }
    @Test
    public void shouldReturnOneClient() throws MiException{
        when(clientRepository.findById(any(String.class))).thenAnswer(invocation -> {
            String id = invocation.getArgument(0);
            Client mockClient = new Client(id,"Natalia", "test@test.com", "0123456789");
            mockClient.setId("NA000001");
            return Optional.of(mockClient);
        });
        Client expectedClient = new Client("NA000001","Natalia", "test@test.com", "0123456789");
        Client foundClient = clientService.getClientByID("NA000001");
        assertEquals(expectedClient, foundClient);
    }
    @Test
    public void shouldDeactivateAClient() throws MiException{
        Client mockClient = new Client("NA000001","Natalia", "test@test.com", "0123456789");
        when(clientRepository.findById("NA000001")).thenReturn(Optional.of(mockClient));
        clientService.deactivateClient("NA000001");
        assertEquals(false, mockClient.getActive());
    }

}
