package com.szkopinski.projectmanager.controller;

import static com.szkopinski.projectmanager.helpers.TestHelpers.convertToJson;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.szkopinski.projectmanager.model.Client;
import com.szkopinski.projectmanager.service.ClientService;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ClientControllerIT {

  private static final String URL_TEMPLATE = "/api/clients/";
  private static final MediaType CONTENT_TYPE_JSON = MediaType.APPLICATION_JSON_UTF8;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ClientService clientService;

  @Test
  @DisplayName("Should return all clients present in repository")
  void shouldReturnAllClients() throws Exception {
    // given
    Client client1 = new Client("Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Client client2 = new Client("Beta", "Beta address", "908765432112345678901234", "19230192301", "beta@mail.com", "00432423", Sets.newHashSet());
    Client client3 = new Client("Gamma", "Gamma address", "100765432112345678901234", "10030192301", "gamma@mail.com", "99432423", Sets.newHashSet());
    clientService.addClient(client1);
    clientService.addClient(client2);
    clientService.addClient(client3);
    List<Client> clients = Arrays.asList(client1, client2, client3);
    String clientsAsJson = convertToJson(clients);

    // when
    mockMvc
        .perform(get(URL_TEMPLATE)
            .accept(CONTENT_TYPE_JSON))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(clientsAsJson));
  }

  @Test
  @DisplayName("Should return empty list when repository is empty GET request is invoked")
  void shouldReturnEmptyListOfClients() throws Exception {
    // given
    String clientsAsJson = convertToJson(Lists.emptyList());

    // when
    mockMvc
        .perform(get(URL_TEMPLATE)
            .accept(CONTENT_TYPE_JSON))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(clientsAsJson));
  }

  @Test
  @DisplayName("Should return client with specified id")
  void shouldReturnClient() throws Exception {
    // given
    int clientId = 2;
    Client client1 = new Client("Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Client client2 = new Client("Beta", "Beta address", "908765432112345678901234", "19230192301", "beta@mail.com", "00432423", Sets.newHashSet());
    Client client3 = new Client("Gamma", "Gamma address", "100765432112345678901234", "10030192301", "gamma@mail.com", "99432423", Sets.newHashSet());
    clientService.addClient(client1);
    clientService.addClient(client2);
    clientService.addClient(client3);
    String client2AsJson = convertToJson(client2);

    // when
    mockMvc
        .perform(get(URL_TEMPLATE + clientId)
            .accept(CONTENT_TYPE_JSON))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(client2AsJson));
  }

  @Test
  @DisplayName("Should add client to repository")
  void shouldAddClient() throws Exception {
    // given
    Client client = new Client("Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    String clientAsJson = convertToJson(client);

    // when
    mockMvc
        .perform(post(URL_TEMPLATE)
            .contentType(CONTENT_TYPE_JSON)
            .content(clientAsJson))
        .andDo(print())
        // then
        .andExpect(status().isCreated())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(jsonPath("$.name").value(client.getName()))
        .andExpect(jsonPath("$.address").value(client.getAddress()))
        .andExpect(jsonPath("$.accountNumber").value(client.getAccountNumber()))
        .andExpect(jsonPath("$.telephone").value(client.getTelephone()))
        .andExpect(jsonPath("$.email").value(client.getEmail()))
        .andExpect(jsonPath("$.taxId").value(client.getTaxId()))
        .andExpect(jsonPath("$.projects").isEmpty());
  }

  @Test
  @DisplayName("Should delete client from repository")
  void shouldDeleteClient() throws Exception {
    // given
    Client client1 = new Client("Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Client client2 = new Client("Beta", "Beta address", "908765432112345678901234", "19230192301", "beta@mail.com", "00432423", Sets.newHashSet());
    Client client3 = new Client("Gamma", "Gamma address", "100765432112345678901234", "10030192301", "gamma@mail.com", "99432423", Sets.newHashSet());
    clientService.addClient(client1);
    clientService.addClient(client2);
    clientService.addClient(client3);
    int clientId = client2.getId();

    // when
    mockMvc
        .perform(delete(URL_TEMPLATE + clientId))
        .andDo(print())
        // then
        .andExpect(status().isNoContent());

    assertFalse(clientService.findClientById(clientId).isPresent());
  }

  @Test
  @DisplayName("Should update client in repository")
  void shouldUpdateClient() throws Exception {
    // given
    Client client1 = new Client("Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Client client2 = new Client("Beta", "Beta address", "908765432112345678901234", "19230192301", "beta@mail.com", "00432423", Sets.newHashSet());
    Client client3 = new Client("Gamma", "Gamma address", "100765432112345678901234", "10030192301", "gamma@mail.com", "99432423", Sets.newHashSet());
    clientService.addClient(client1);
    clientService.addClient(client2);
    clientService.addClient(client3);
    int clientId = client2.getId();
    Client updatedClient = new Client(clientId, "Updated Beta", "Updated address", "908765432112345678901234", "19230192301", "beta@mail.com",
        "00432423", Sets.newHashSet());
    String updatedClientAsJson = convertToJson(updatedClient);

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
  }
}
