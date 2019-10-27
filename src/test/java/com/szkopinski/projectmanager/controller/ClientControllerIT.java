package com.szkopinski.projectmanager.controller;

import static com.szkopinski.projectmanager.helpers.TestHelpers.convertToJson;
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
public class ClientControllerIT {

  private static final String URL_TEMPLATE = "/api/clients/";
  private static final MediaType CONTENT_TYPE_JSON = MediaType.APPLICATION_JSON_UTF8;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ClientService clientService;

  @Test
  @DisplayName("Should return all clients present in the repository")
  void shouldReturnAllClient() throws Exception {
    // given
    Client client1 = new Client("Alpha", "Alpha's address", "123456789012345678901211", "09876543", "contact@alpha.pl", "123234531",
        Sets.newHashSet());
    Client client2 = new Client("Beta", "Beta's address", "123456789012345678901234", "09876541", "contact@beta.pl", "123234532",
        Sets.newHashSet());
    Client client3 = new Client("Gamma", "Beta's address", "123456789012345678901222", "09876546", "contact@gamma.pl", "123234533",
        Sets.newHashSet());
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
  @DisplayName("Should return empty list when there are no clients in repository")
  void shouldReturnEmptyListOfClients() throws Exception {
    // given
    List<Client> clients = Lists.emptyList();
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
  @DisplayName("Should add client to the repository")
  void shouldAddClient() throws Exception {
    // given
    Client client = new Client("Alpha", "Alpha's address", "123456789012345678901211", "09876543", "contact@alpha.pl", "123234531",
        Sets.newHashSet());
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
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value(client.getName()))
        .andExpect(jsonPath("$.address").value(client.getAddress()))
        .andExpect(jsonPath("$.accountNumber").value(client.getAccountNumber()))
        .andExpect(jsonPath("$.telephone").value(client.getTelephone()))
        .andExpect(jsonPath("$.email").value(client.getEmail()))
        .andExpect(jsonPath("$.taxId").value(client.getTaxId()))
        .andExpect(jsonPath("$.projects").isEmpty());
  }

  @Test
  @DisplayName("Should delete client from the repository")
  void shouldDeleteClient() throws Exception {
    // given
    Client client1 = new Client("Alpha", "Alpha's address", "123456789012345678901211", "09876543", "contact@alpha.pl", "123234531",
        Sets.newHashSet());
    Client client2 = new Client("Beta", "Beta's address", "123456789012345678901234", "09876541", "contact@beta.pl", "123234532",
        Sets.newHashSet());
    Client client3 = new Client("Gamma", "Beta's address", "123456789012345678901222", "09876546", "contact@gamma.pl", "123234533",
        Sets.newHashSet());
    clientService.addClient(client1);
    clientService.addClient(client2);
    clientService.addClient(client3);

    // when
    mockMvc
        .perform(delete(URL_TEMPLATE + client2.getId()))
        .andDo(print())
        // then
        .andExpect(status().isNoContent());
  }

  @Test
  @DisplayName("Should update client present in the repository")
  void shouldUpdateClient() throws Exception {
    // given
    Client client = new Client("Alpha", "Alpha's address", "123456789012345678901211", "09876543", "contact@alpha.pl", "123234531",
        Sets.newLinkedHashSet());
    Client updatedClient = new Client("Beta", "Beta's address", "123456789012345678901234", "09876541", "contact@beta.pl", "123234532",
        client.getProjects());
    clientService.addClient(client);
    String updatedClientAsJson = convertToJson(updatedClient);

    // when
    mockMvc
        .perform(put(URL_TEMPLATE + client.getId())
            .contentType(CONTENT_TYPE_JSON)
            .content(updatedClientAsJson))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value(updatedClient.getName()))
        .andExpect(jsonPath("$.address").value(updatedClient.getAddress()))
        .andExpect(jsonPath("$.accountNumber").value(updatedClient.getAccountNumber()))
        .andExpect(jsonPath("$.telephone").value(updatedClient.getTelephone()))
        .andExpect(jsonPath("$.email").value(updatedClient.getEmail()))
        .andExpect(jsonPath("$.taxId").value(updatedClient.getTaxId()))
        .andExpect(jsonPath("$.projects").isEmpty());
  }
}
