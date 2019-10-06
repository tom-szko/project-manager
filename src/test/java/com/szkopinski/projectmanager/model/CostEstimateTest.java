//package com.szkopinski.projectmanager.models;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.List;
//import org.assertj.core.util.Lists;
//import org.junit.jupiter.api.Test;
//
//class CostEstimateTest {
//
//  @Test
//  void shouldCheckFullInitialization() {
//    //given
//    int id = 1;
//    Project project = new Project();
//    Client client = new Client();
//    List<Item> itemList = Lists.emptyList();
//
//    //when
//    CostEstimate costEstimate = new CostEstimate(id, project, client, itemList);
//
//    //then
//    assertEquals(id, costEstimate.getId());
//    assertEquals(project, costEstimate.getProject());
//    assertEquals(client, costEstimate.getClient());
//    assertEquals(itemList, costEstimate.getItemList());
//  }
//
//}