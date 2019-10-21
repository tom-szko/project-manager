package com.szkopinski.projectmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDate;
import java.util.HashSet;
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

  @ManyToOne
  @JoinColumn(name = "client_id")
  @JsonBackReference
  private Client client;

  private String title;

  private String description;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
  @JsonManagedReference
  private Set<CostEstimate> costEstimates = new HashSet<>();

  @Enumerated(EnumType.STRING)
  private Status status;

  private LocalDate startDate;

  private LocalDate endDate;


  public Project(Client client, String title, String description, Status status, LocalDate startDate, LocalDate endDate) {
    this.client = client;
    this.title = title;
    this.description = description;
    this.status = status;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
