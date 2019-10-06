package com.szkopinski.projectmanager.model;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;

class ClientTest {

  @Test
  void shouldCheckFullInitialization() {
    //given
    int id = 2;
    String name = "Sample Client";
    String address = "Sample address";
    String accountNumber = "123456789012345678901234";
    String telephone = "123456789";
    String email = "sample@sample.com";
    String taxId = "123163123";

    //when
    Client client = new Client(id, name, address, accountNumber, telephone, email, taxId, Sets.newHashSet());

    //then
    assertEquals(id, client.getId());
    assertEquals(name, client.getName());
    assertEquals(address, client.getAddress());
    assertEquals(accountNumber, client.getAccountNumber());
    assertEquals(telephone, client.getTelephone());
    assertEquals(email, client.getEmail());
    assertEquals(taxId, client.getTaxId());
  }
}
