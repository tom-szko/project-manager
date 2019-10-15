package com.szkopinski.projectmanager.service;

import com.szkopinski.projectmanager.model.Invoice;
import com.szkopinski.projectmanager.repository.InvoiceRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

  private InvoiceRepository invoiceRepository;

  @Autowired
  public InvoiceService(InvoiceRepository invoiceRepository) {
    this.invoiceRepository = invoiceRepository;
  }

  public Iterable<Invoice> findAllInvoices() {
    return invoiceRepository.findAll();
  }

  public Optional<Invoice> findInvoiceById(int id) {
    return invoiceRepository.findById(id);
  }

  @Transactional
  public Invoice addInvoice(Invoice invoice) {
    return invoiceRepository.save(invoice);
  }

  @Transactional
  public void deleteInvoice(int id) {
    invoiceRepository.deleteById(id);
  }

  @Transactional
  public Invoice updateInvoice(int id, Invoice invoice) {
    return invoiceRepository.findById(id)
        .map(invoiceData -> {
          invoiceData.setIssueDate(invoice.getIssueDate());
          invoiceData.setRecipient(invoice.getRecipient());
          invoiceData.setProject(invoice.getProject());
          invoiceData.setDescription(invoice.getDescription());
          invoiceData.setAmount(invoice.getAmount());
          return invoiceRepository.save(invoiceData);
        }).orElse(null);
  }
}
