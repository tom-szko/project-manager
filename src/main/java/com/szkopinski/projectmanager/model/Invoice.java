package com.szkopinski.projectmanager.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoices")
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private LocalDate issueDate;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "recipient_id")
  private Client recipient;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "project_id")
  private Project project;

  private String description;

  private BigDecimal amount;

  public Invoice(LocalDate issueDate, Client recipient, Project project, String description, BigDecimal amount) {
    this.issueDate = issueDate;
    this.recipient = recipient;
    this.project = project;
    this.description = description;
    this.amount = amount;
  }
}
