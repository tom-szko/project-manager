package com.szkopinski.projectmanager.controller;

import static com.szkopinski.projectmanager.helpers.TestHelpers.convertToJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.szkopinski.projectmanager.model.Client;
import com.szkopinski.projectmanager.service.ClientService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ClientController.class)
class ClientControllerTest {

  private static final String URL_TEMPLATE = "/api/clients/";
  private static final MediaType CONTENT_TYPE_JSON = MediaType.APPLICATION_JSON_UTF8;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ClientService clientService;

  @Test
  @DisplayName("Should return all clients")
  void shouldReturnAllClients() throws Exception {
    // given
    Client client1 = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543",
        Sets.newHashSet());
    Client client2 = new Client(2, "Beta", "Beta address", "908765432112345678901234", "19230192301", "beta@mail.com", "00432423", Sets.newHashSet());
    Client client3 = new Client(3, "Gamma", "Gamma address", "100765432112345678901234", "10030192301", "gamma@mail.com", "99432423",
        Sets.newHashSet());
    List<Client> clients = Arrays.asList(client1, client2, client3);
    String clientsAsJson = convertToJson(clients);
    Mockito.when(clientService.findAllClients()).thenReturn(clients);

    // when
    mockMvc
        .perform(get(URL_TEMPLATE)
            .accept(CONTENT_TYPE_JSON))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(clientsAsJson));

    Mockito.verify(clientService, Mockito.times(1)).findAllClients();
  }

  @Test
  @DisplayName("Should return specified client")
  void shouldReturnClient() throws Exception {
    // given
    int clientId = 1;
    Client client = new Client(clientId, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543",
        Sets.newHashSet());
    String clientAsJson = convertToJson(client);
    Mockito.when(clientService.findClientById(clientId)).thenReturn(Optional.of(client));

    // when
    mockMvc
        .perform(get(URL_TEMPLATE + clientId)
            .accept(CONTENT_TYPE_JSON))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(clientAsJson));

    Mockito.verify(clientService, Mockito.times(1)).findClientById(clientId);
  }

  @Test
  @DisplayName("Should add specified client")
  void shouldAddClient() throws Exception {
    // given
    Client client = new Client("Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543");
    String clientAsJson = convertToJson(client);
    Mockito.when(clientService.addClient(client)).thenReturn(client);

    // when
    mockMvc
        .perform(post(URL_TEMPLATE)
            .contentType(CONTENT_TYPE_JSON)
            .content(clientAsJson))
        .andDo(print())
        // then
        .andExpect(status().isCreated())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(clientAsJson));

    Mockito.verify(clientService, Mockito.times(1)).addClient(client);
  }

  @Test
  @DisplayName("Should delete specified client")
  void shouldDeleteClient() throws Exception {
    // given
    int clientId = 1;

    // when
    mockMvc
        .perform(delete(URL_TEMPLATE + clientId))
        .andDo(print())
        // then
        .andExpect(status().isNoContent());

    Mockito.verify(clientService, Mockito.times(1)).deleteClient(clientId);
  }

  @Test
  @DisplayName("Should update specified client")
  void shouldUpdateClient() throws Exception {
    // given
    int clientId = 1;
    Client client = new Client(clientId, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543",
        Sets.newHashSet());
    Client updatedClient = new Client(clientId, "Updated Alpha", "Updated Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com",
        "1234543",
        Sets.newHashSet());
    String updatedClientAsJson = convertToJson(updatedClient);
    Mockito.when(clientService.updateClient(clientId, updatedClient)).thenReturn(updatedClient);

    // when
    mockMvc
        .perform(put(URL_TEMPLATE + clientId)
            .contentType(CONTENT_TYPE_JSON)
            .content(updatedClientAsJson))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(updatedClientAsJson));

    Mockito.verify(clientService, Mockito.times(1)).updateClient(clientId, updatedClient);
  }
}
