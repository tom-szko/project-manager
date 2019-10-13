package com.szkopinski.projectmanager.controller;

import com.szkopinski.projectmanager.model.Client;
import com.szkopinski.projectmanager.service.ClientService;
import java.net.URI;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/clients/")
public class ClientController {

  private ClientService clientService;

  @Autowired
  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping
  public ResponseEntity<Iterable<Client>> getAllClients() {
    return ResponseEntity.ok(clientService.findAllProjects());
  }

  @GetMapping("{id}")
  public ResponseEntity getClientById(@PathVariable("id") int id) {
    Optional<Client> client = clientService.findClientById(id);
    if (client.isPresent()) {
      return ResponseEntity.ok(client);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("{id}")
  public ResponseEntity deleteClient(@PathVariable("id") int id) {
    try {
      clientService.deleteClient(id);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping
  public ResponseEntity addClient(@NonNull @RequestBody Client client) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(clientService.addClient(client));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PutMapping("{id}")
  public ResponseEntity updateClient(@PathVariable("id") int id, @NonNull @RequestBody Client client) {
    try {
      Client updatedClient = clientService.updateClient(id, client);
      if (updatedClient == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(updatedClient);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
