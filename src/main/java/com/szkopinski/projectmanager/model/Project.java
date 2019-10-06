package com.szkopinski.projectmanager.model;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "client_id")
  private Client client;

  private String title;

  private String description;

  @OneToMany(mappedBy = "project")
  private Set<CostEstimate> costEstimates;

  @Enumerated(EnumType.STRING)
  private Status status;

  private LocalDate startDate;

  private LocalDate endDate;


  public Project(Client client, String title, String description, Set<CostEstimate> costEstimates, Status status, LocalDate startDate,
      LocalDate endDate) {
    this.client = client;
    this.title = title;
    this.description = description;
    this.costEstimates = costEstimates;
    this.status = status;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
