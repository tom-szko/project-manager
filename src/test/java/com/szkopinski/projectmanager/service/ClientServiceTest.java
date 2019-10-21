package com.szkopinski.projectmanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sun.tools.javac.util.List;
import com.szkopinski.projectmanager.model.Client;
import com.szkopinski.projectmanager.repository.ClientRepository;
import java.util.Optional;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

  @Mock
  private ClientRepository clientRepository;

  private ClientService clientService;

  @BeforeEach
  void setUp() {
    clientService = new ClientService(clientRepository);
  }

  @Test
  @DisplayName("Should find all clients")
  void shouldFindAllClients() {
    // given
    Client client1 = new Client("Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543");
    Client client2 = new Client("Beta", "Beta address", "908765432112345678901234", "19230192301", "beta@mail.com", "00432423");
    Client client3 = new Client("Gamma", "Gamma address", "100765432112345678901234", "10030192301", "gamma@mail.com", "99432423");
    List<Client> expectedClients = List.of(client1, client2, client3);
    Mockito.when(clientRepository.findAll()).thenReturn(expectedClients);

    // when
    Iterable<Client> actualClients = clientService.findAllClients();

    // then
    Mockito.verify(clientRepository, Mockito.times(1)).findAll();
    assertEquals(expectedClients, actualClients);
  }

  @Test
  @DisplayName("Should find specified client")
  void shouldFindSingleClient() {
    // given
    int clientId = 1;
    Client client = new Client(clientId, "Gamma", "Gamma address", "100765432112345678901234", "10030192301", "gamma@mail.com", "99432423",
        Sets.newHashSet());
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

    // when
    Optional<Client> actualClient = clientService.findClientById(clientId);

    // then
    Mockito.verify(clientRepository, Mockito.times(1)).findById(clientId);
    assertEquals(Optional.of(client), actualClient);
  }

  @Test
  @DisplayName("Should add client")
  void shouldAddClient() {
    // given
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Mockito.when(clientRepository.save(client)).thenReturn(client);

    // when
    Client actualClient = clientService.addClient(client);

    // then
    Mockito.verify(clientRepository, Mockito.times(1)).save(client);
    assertEquals(client, actualClient);
  }

  @Test
  @DisplayName("Should delete client")
  void shouldDeleteClient() {
    // given
    int clientId = 1;
    Mockito.doNothing().when(clientRepository).deleteById(clientId);

    // when
    clientService.deleteClient(clientId);

    // then
    Mockito.verify(clientRepository, Mockito.times(1)).deleteById(clientId);
  }

  @Test
  @DisplayName("Should update client")
  void shouldUpdateClient() {
    // given
    int clientId = 1;
    Client client = new Client(clientId, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543",
        Sets.newHashSet());
    Client updatedClient = new Client(clientId, "Beta", "Beta address", "1234567890123456789012334", "048213898", "beta@mail.com", "1234543",
        Sets.newHashSet());
    Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
    Mockito.when(clientRepository.save(updatedClient)).thenReturn(updatedClient);

    // when
    Client actualUpdatedClient = clientService.updateClient(clientId, updatedClient);

    // then
    assertEquals(updatedClient, actualUpdatedClient);
    Mockito.verify(clientRepository, Mockito.times(1)).findById(clientId);
    Mockito.verify(clientRepository, Mockito.times(1)).save(updatedClient);
  }
}
