package com.szkopinski.projectmanager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Set;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;

class ProjectTest {

  @Test
  void shouldCheckFullInitialization() {
    //given
    int id = 1;
    Client client = new Client(2, "Publicis NY", "Broadway St 34", "123456789012345678901234", "123456456", "contact@publicisny.com", "12345678",
        Sets.newHashSet());
    String title = "20s video editing";
    String description = "Edit of 20s video for XYZ brand";
    Set<CostEstimate> costEstimates = Sets.newHashSet();
    Status status = Status.IN_PROGRESS;
    LocalDate startDate = LocalDate.of(2019, 10, 5);
    LocalDate endDate = LocalDate.of(2019, 10, 20);

    //when
    Project project = new Project(id, client, title, description, costEstimates, status, startDate, endDate);

    //then
    assertEquals(id, project.getId());
    assertEquals(client, project.getClient());
    assertEquals(title, project.getTitle());
    assertEquals(description, project.getDescription());
    assertEquals(costEstimates, project.getCostEstimates());
    assertEquals(status, project.getStatus());
    assertEquals(startDate, project.getStartDate());
    assertEquals(endDate, project.getEndDate());
  }
}
