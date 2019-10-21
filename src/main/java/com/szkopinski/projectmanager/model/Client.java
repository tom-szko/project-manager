package com.szkopinski.projectmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;

  private String address;

  private String accountNumber;

  private String telephone;

  private String email;

  private String taxId;

  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
  @JsonManagedReference
  private Set<Project> projects = new HashSet<>();

  public Client(String name, String address, String accountNumber, String telephone, String email, String taxId) {
    this.name = name;
    this.address = address;
    this.accountNumber = accountNumber;
    this.telephone = telephone;
    this.email = email;
    this.taxId = taxId;
  }
}
