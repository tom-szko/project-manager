package com.szkopinski.projectmanager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CostEstimateItemTest {

  @Test
  void shouldCheckFullInitialization() {
    //given
    int id = 1;
    String name = "Video editing";
    int quantity = 10;
    String unitType = "hour";
    BigDecimal unitPrice = new BigDecimal(250);
    BigDecimal discount = new BigDecimal(0.2);
    BigDecimal tax = new BigDecimal(0.23);
    CostEstimate costEstimate = new CostEstimate();

    //when
    CostEstimateItem costEstimateItem = new CostEstimateItem(id, name, quantity, unitType, unitPrice, discount, tax, costEstimate);

    //then
    assertEquals(id, costEstimateItem.getId());
    assertEquals(name, costEstimateItem.getName());
    assertEquals(quantity, costEstimateItem.getQuantity());
    assertEquals(unitType, costEstimateItem.getUnitType());
    assertEquals(unitPrice, costEstimateItem.getUnitPrice());
    assertEquals(discount, costEstimateItem.getDiscount());
    assertEquals(tax, costEstimateItem.getTax());
    assertEquals(costEstimate, costEstimateItem.getCostEstimate());
  }
}
