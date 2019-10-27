package com.szkopinski.projectmanager.model;

import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class CostEstimateItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;

  private int quantity;

  private String unitType;

  private BigDecimal unitPrice;

  private BigDecimal discount;

  private BigDecimal tax;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cost_estimate_id")
  private CostEstimate costEstimate;

  public CostEstimateItem(String name, int quantity, String unitType, BigDecimal unitPrice, BigDecimal discount, BigDecimal tax) {
    this.name = name;
    this.quantity = quantity;
    this.unitType = unitType;
    this.unitPrice = unitPrice;
    this.discount = discount;
    this.tax = tax;
  }
}
