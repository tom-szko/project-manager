package com.szkopinski.projectmanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sun.tools.javac.util.List;
import com.szkopinski.projectmanager.model.Client;
import com.szkopinski.projectmanager.model.Invoice;
import com.szkopinski.projectmanager.model.Project;
import com.szkopinski.projectmanager.model.Status;
import com.szkopinski.projectmanager.repository.InvoiceRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
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
class InvoiceServiceTest {

  @Mock
  private InvoiceRepository invoiceRepository;

  private InvoiceService invoiceService;

  @BeforeEach
  void setUp() {
    invoiceService = new InvoiceService(invoiceRepository);
  }

  @Test
  @DisplayName("Should return all invoices")
  void shouldReturnAllInvoices() {
    // given
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project project = new Project(client, "Sample title", "Sample description", Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    Invoice invoice1 = new Invoice(1, LocalDate.of(2019, 4, 22), client, project, "Sample description", new BigDecimal(300));
    Invoice invoice2 = new Invoice(2, LocalDate.of(2019, 4, 23), client, project, "Sample description2", new BigDecimal(500));
    Invoice invoice3 = new Invoice(3, LocalDate.of(2019, 4, 23), client, project, "Sample description3", new BigDecimal(1000));
    List<Invoice> expectedInvoices = List.of(invoice1, invoice1, invoice1);
    Mockito.when(invoiceRepository.findAll()).thenReturn(expectedInvoices);

    // when
    Iterable<Invoice> actualInvoices = invoiceService.findAllInvoices();

    // then
    Mockito.verify(invoiceRepository, Mockito.times(1)).findAll();
    assertEquals(expectedInvoices, actualInvoices);
  }

  @Test
  @DisplayName("Should return specified invoice")
  void shouldReturnSpecifiedInvoice() {
    // given
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project project = new Project(client, "Sample title", "Sample description", Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    Invoice invoice = new Invoice(3, LocalDate.of(2019, 4, 23), client, project, "Sample description3", new BigDecimal(1000));
    int id = invoice.getId();
    Mockito.when(invoiceRepository.findById(id)).thenReturn(Optional.of(invoice));

    // when
    Optional<Invoice> actualInvoice = invoiceService.findInvoiceById(id);

    // then
    Mockito.verify(invoiceRepository, Mockito.times(1)).findById(id);
    assertEquals(Optional.of(invoice), actualInvoice);
  }

  @Test
  @DisplayName("Should add invoice")
  void shouldAddInvoice() {
    // given
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project project = new Project(client, "Sample title", "Sample description", Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    Invoice invoice = new Invoice(1, LocalDate.of(2019, 4, 22), client, project, "Sample description", new BigDecimal(300));
    Mockito.when(invoiceRepository.save(invoice)).thenReturn(invoice);

    // when
    Invoice actualInvoice = invoiceService.addInvoice(invoice);

    // then
    Mockito.verify(invoiceRepository, Mockito.times(1)).save(invoice);
    assertEquals(invoice, actualInvoice);
  }

  @Test
  @DisplayName("Should delete specified invoice")
  void shouldDeleteInvoice() {
    // given
    int invoiceId = 1;
    Mockito.doNothing().when(invoiceRepository).deleteById(invoiceId);

    // when
    invoiceService.deleteInvoice(invoiceId);

    // then
    Mockito.verify(invoiceRepository, Mockito.times(1)).deleteById(invoiceId);
  }

  @Test
  @DisplayName("Should update specified invoice")
  void shouldUpdateInvoice() {
    // given
    int invoiceId = 2;
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project project = new Project(client, "Sample title", "Sample description", Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    Invoice invoice = new Invoice(invoiceId, LocalDate.of(2019, 4, 23), client, project, "Sample description", new BigDecimal(500));
    Invoice updatedInvoice = new Invoice(invoiceId, LocalDate.of(2019, 4, 25), client, project, "Updated description", new BigDecimal(700));
    Mockito.when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
    Mockito.when(invoiceRepository.save(updatedInvoice)).thenReturn(updatedInvoice);

    // when
    Invoice actualInvoice = invoiceService.updateInvoice(invoiceId, updatedInvoice);

    // then
    Mockito.verify(invoiceRepository, Mockito.times(1)).save(updatedInvoice);
    Mockito.verify(invoiceRepository, Mockito.times(1)).findById(invoiceId);
    assertEquals(updatedInvoice, actualInvoice);
  }
}
