package com.szkopinski.projectmanager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.collections.Sets;

class InvoiceTest {

  @Test
  void shouldCheckFullInitialization() {
    //given
    int id = 1;
    LocalDate issueDate = LocalDate.of(2019, 10, 20);
    Client recipient = new Client(2, "Publicis NY", "Broadway St 34", "123456789012345678901234", "123456456", "contact@publicisny.com", "12345678"
        , Sets.newSet());
    Project project = new Project();
    String description = "Sample description of a project";
    BigDecimal amount = new BigDecimal(1200);

    //when
    Invoice invoice = new Invoice(id, issueDate, recipient, project, description, amount);

    //then
    assertEquals(id, invoice.getId());
    assertEquals(issueDate, invoice.getIssueDate());
    assertEquals(recipient, invoice.getRecipient());
    assertEquals(project, invoice.getProject());
    assertEquals(description, invoice.getDescription());
    assertEquals(amount, invoice.getAmount());
  }
}
