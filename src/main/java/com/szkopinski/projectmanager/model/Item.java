package com.szkopinski.projectmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;
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
@Table(name = "items")
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;

  private int quantity;

  private String unitType;

  private BigDecimal unitPrice;

  private BigDecimal discount;

  private BigDecimal tax;

  @ManyToOne
  @JoinColumn(name = "cost_estimate_id")
  @JsonBackReference
  private CostEstimate costEstimate;

  public Item(String name, int quantity, String unitType, BigDecimal unitPrice, BigDecimal discount, BigDecimal tax, CostEstimate costEstimate) {
    this.name = name;
    this.quantity = quantity;
    this.unitType = unitType;
    this.unitPrice = unitPrice;
    this.discount = discount;
    this.tax = tax;
    this.costEstimate = costEstimate;
  }
}
