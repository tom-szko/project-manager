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
import com.szkopinski.projectmanager.model.Invoice;
import com.szkopinski.projectmanager.model.Project;
import com.szkopinski.projectmanager.model.Status;
import com.szkopinski.projectmanager.service.InvoiceService;
import java.math.BigDecimal;
import java.time.LocalDate;
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

@WebMvcTest(controllers = InvoiceController.class)
class InvoiceControllerTest {

  private static final String URL_TEMPLATE = "/api/invoices/";
  private static final MediaType CONTENT_TYPE_JSON = MediaType.APPLICATION_JSON_UTF8;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private InvoiceService invoiceService;

  @Test
  @DisplayName("Should return all invoices")
  void shouldReturnAllInvoices() throws Exception {
    // given
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543",
        Sets.newHashSet());
    Project project = new Project(client, "Sample title1", "Sample description1", Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    Invoice invoice1 = new Invoice(1, LocalDate.of(2019, 5, 20), client, project, "Sample description", new BigDecimal(1000));
    Invoice invoice2 = new Invoice(1, LocalDate.of(2019, 5, 20), client, project, "Sample description2", new BigDecimal(2000));
    Invoice invoice3 = new Invoice(1, LocalDate.of(2019, 5, 20), client, project, "Sample description3", new BigDecimal(1500));
    List<Invoice> invoices = Arrays.asList(invoice1, invoice2, invoice3);
    String invoicesAsJson = convertToJson(invoices);
    Mockito.when(invoiceService.findAllInvoices()).thenReturn(invoices);

    // when
    mockMvc
        .perform(get(URL_TEMPLATE)
            .accept(CONTENT_TYPE_JSON))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(invoicesAsJson));

    Mockito.verify(invoiceService, Mockito.times(1)).findAllInvoices();
  }

  @Test
  @DisplayName("Should return specified invoice")
  void shouldReturnInvoice() throws Exception {
    // given
    int invoiceId = 1;
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543",
        Sets.newHashSet());
    Project project = new Project(client, "Sample title1", "Sample description1", Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    Invoice invoice = new Invoice(invoiceId, LocalDate.of(2019, 5, 20), client, project, "Sample description", new BigDecimal(1000));
    String invoiceAsJson = convertToJson(invoice);
    Mockito.when(invoiceService.findInvoiceById(invoiceId)).thenReturn(Optional.of(invoice));

    // when
    mockMvc
        .perform(get(URL_TEMPLATE + invoiceId)
            .accept(CONTENT_TYPE_JSON))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(invoiceAsJson));

    Mockito.verify(invoiceService, Mockito.times(1)).findInvoiceById(invoiceId);
  }

  @Test
  @DisplayName("Should add invoice")
  void shouldAddInvoice() throws Exception {
    // given
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543",
        Sets.newHashSet());
    Project project = new Project(client, "Sample title1", "Sample description1", Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    Invoice invoice = new Invoice(1, LocalDate.of(2019, 5, 20), client, project, "Sample description", new BigDecimal(1000));
    String invoiceAsJson = convertToJson(invoice);
    Mockito.when(invoiceService.addInvoice(invoice)).thenReturn(invoice);

    // when
    mockMvc
        .perform(post(URL_TEMPLATE)
            .contentType(CONTENT_TYPE_JSON)
            .content(invoiceAsJson))
        .andDo(print())
        // then
        .andExpect(status().isCreated())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(invoiceAsJson));

    Mockito.verify(invoiceService, Mockito.times(1)).addInvoice(invoice);
  }

  @Test
  @DisplayName("Should delete invoice")
  void shouldDeleteInvoice() throws Exception {
    // given
    int invoiceId = 1;
    Mockito.doNothing().when(invoiceService).deleteInvoice(invoiceId);

    // when
    mockMvc
        .perform(delete(URL_TEMPLATE + invoiceId))
        .andDo(print())
        // then
        .andExpect(status().isNoContent());

    Mockito.verify(invoiceService, Mockito.times(1)).deleteInvoice(invoiceId);
  }

  @Test
  @DisplayName("Should update invoice")
  void shouldUpdateInvoice() throws Exception {
    // given
    int invoiceId = 1;
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543",
        Sets.newHashSet());
    Project project = new Project(1, client, "Sample title1", "Sample description1", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    Invoice invoice = new Invoice(invoiceId, LocalDate.of(2019, 5, 20), client, project, "Sample description", new BigDecimal(1000));
    Invoice updatedInvoice = new Invoice(invoiceId, LocalDate.of(2019, 5, 22), client, project, "Updated description", new BigDecimal(2000));
    String updatedInvoiceAsJson = convertToJson(updatedInvoice);
    Mockito.when(invoiceService.updateInvoice(invoiceId, updatedInvoice)).thenReturn(updatedInvoice);

    // when
    mockMvc
        .perform(put(URL_TEMPLATE + invoiceId)
            .contentType(CONTENT_TYPE_JSON)
            .content(updatedInvoiceAsJson))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(updatedInvoiceAsJson));

    Mockito.verify(invoiceService, Mockito.times(1)).updateInvoice(invoiceId, updatedInvoice);
  }
}
