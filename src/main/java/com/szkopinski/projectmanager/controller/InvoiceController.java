package com.szkopinski.projectmanager.controller;

import com.szkopinski.projectmanager.model.Invoice;
import com.szkopinski.projectmanager.service.InvoiceService;
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
@RequestMapping("api/invoices/")
public class InvoiceController {

  private InvoiceService invoiceService;

  @Autowired
  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping
  public ResponseEntity<Iterable<Invoice>> findAllInvoices() {
    return ResponseEntity.ok(invoiceService.findAllInvoices());
  }

  @GetMapping("{id}")
  public ResponseEntity findInvoiceById(@PathVariable int id) {
    Optional<Invoice> invoice = invoiceService.findInvoiceById(id);
    if (invoice.isPresent()) {
      return ResponseEntity.ok(invoice);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity addInvoice(@NonNull @RequestBody Invoice invoice) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.addInvoice(invoice));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity deleteInvoice(@PathVariable("id") int id) {
    try {
    invoiceService.deleteInvoice(id);
    return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PutMapping("{id}")
  public ResponseEntity updateInvoice(@PathVariable("id") int id, @NonNull @RequestBody Invoice invoice) {
    try {
      Invoice updatedInvoice = invoiceService.updateInvoice(id, invoice);
      if (updatedInvoice == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(invoice);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
