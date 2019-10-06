package com.szkopinski.projectmanager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ItemTest {

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
    Item item = new Item(id, name, quantity, unitType, unitPrice, discount, tax, costEstimate);

    //then
    assertEquals(id, item.getId());
    assertEquals(name, item.getName());
    assertEquals(quantity, item.getQuantity());
    assertEquals(unitType, item.getUnitType());
    assertEquals(unitPrice, item.getUnitPrice());
    assertEquals(discount, item.getDiscount());
    assertEquals(tax, item.getTax());
    assertEquals(costEstimate, item.getCostEstimate());
  }
}
