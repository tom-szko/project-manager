package com.szkopinski.projectmanager.service;

import com.szkopinski.projectmanager.model.Client;
import com.szkopinski.projectmanager.repository.ClientRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  private ClientRepository clientRepository;

  @Autowired
  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public Iterable<Client> findAllProjects() {
    Sort sortById = new Sort(Sort.Direction.ASC, "id");
    return clientRepository.findAll();
  }

  public Optional<Client> findClientById(int id) {
    return clientRepository.findById(id);
  }

  @Transactional
  public Client addClient(Client client) {
    return clientRepository.save(client);
  }

  @Transactional
  public void deleteClient(int id) {
    clientRepository.deleteById(id);
  }

  @Transactional
  public Client updateClient(int id, Client updatedClient) {
    return clientRepository.findById(id)
        .map(clientData -> {
          clientData.setName(updatedClient.getName());
          clientData.setAddress(updatedClient.getAddress());
          clientData.setAccountNumber(updatedClient.getAccountNumber());
          clientData.setTelephone(updatedClient.getTelephone());
          clientData.setEmail(updatedClient.getEmail());
          clientData.setTaxId(updatedClient.getTaxId());
          clientData.setProjects(updatedClient.getProjects());
          return clientRepository.save(clientData);
        }).orElse(null);
  }
}
