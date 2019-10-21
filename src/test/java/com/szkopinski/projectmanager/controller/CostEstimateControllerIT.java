package com.szkopinski.projectmanager.controller;

import static com.szkopinski.projectmanager.helpers.TestHelpers.convertToJson;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.szkopinski.projectmanager.model.Client;
import com.szkopinski.projectmanager.model.CostEstimate;
import com.szkopinski.projectmanager.model.Item;
import com.szkopinski.projectmanager.model.Project;
import com.szkopinski.projectmanager.model.Status;
import com.szkopinski.projectmanager.service.ClientService;
import com.szkopinski.projectmanager.service.CostEstimateService;
import com.szkopinski.projectmanager.service.ProjectService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CostEstimateControllerIT {

  private static final String URL_TEMPLATE = "/api/estimates/";
  private static final MediaType CONTENT_TYPE_JSON = MediaType.APPLICATION_JSON_UTF8;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CostEstimateService costEstimateService;

  @Autowired
  private ProjectService projectService;

  @Autowired
  private ClientService clientService;

  @Test
  @DisplayName("Should return all cost estimates present in repository")
  void shouldReturnAllCostEstimates() throws Exception {
    // given
    Client client = new Client("Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543");
    clientService.addClient(client);
    Project project1 = new Project(client, "Sample title1", "Sample description1", Status.FINISHED, LocalDate.of(2019, 5, 3),
        LocalDate.of(2019, 6, 20));
    Project project2 = new Project(client, "Sample title2", "Sample description2", Status.FINISHED, LocalDate.of(2019, 5, 5),
        LocalDate.of(2019, 6, 22));
    Project project3 = new Project(client, "Sample title3", "Sample description3", Status.FINISHED, LocalDate.of(2019, 5, 5),
        LocalDate.of(2019, 6, 22));
    projectService.addProject(project1);
    projectService.addProject(project2);
    projectService.addProject(project3);
    CostEstimate costEstimate1 = new CostEstimate(project1);
    CostEstimate costEstimate2 = new CostEstimate(project2);
    CostEstimate costEstimate3 = new CostEstimate(project3);
    costEstimateService.addCostEstimate(costEstimate1);
    costEstimateService.addCostEstimate(costEstimate2);
    costEstimateService.addCostEstimate(costEstimate3);
    List<CostEstimate> costEstimates = Arrays.asList(costEstimate1, costEstimate2, costEstimate3);
    String costEstimatesAsJson = convertToJson(costEstimates);

    // when
    mockMvc
        .perform(get(URL_TEMPLATE)
            .accept(CONTENT_TYPE_JSON))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(costEstimatesAsJson));
  }

  @Test
  @DisplayName("Should return empty list when there are no cost estimates in repository")
  void shouldReturnEmptyListOfCostEstimates() throws Exception {
    // given
    String costEstimatesAsJson = convertToJson(Lists.emptyList());

    // when
    mockMvc
        .perform(get(URL_TEMPLATE)
            .accept(CONTENT_TYPE_JSON))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(costEstimatesAsJson));
  }

  @Test
  @DisplayName("Should return specified cost estimate")
  void shouldReturnCostEstimate() throws Exception {
    // given
    Client client = new Client("Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543");
    clientService.addClient(client);
    Project project1 = new Project(client, "Sample title1", "Sample description1", Status.FINISHED, LocalDate.of(2019, 5, 3),
        LocalDate.of(2019, 6, 20));
    Project project2 = new Project(client, "Sample title2", "Sample description2", Status.FINISHED, LocalDate.of(2019, 5, 5),
        LocalDate.of(2019, 6, 22));
    Project project3 = new Project(client, "Sample title3", "Sample description3", Status.FINISHED, LocalDate.of(2019, 5, 5),
        LocalDate.of(2019, 6, 22));
    projectService.addProject(project1);
    projectService.addProject(project2);
    projectService.addProject(project3);
    CostEstimate costEstimate1 = new CostEstimate(project1);
    CostEstimate costEstimate2 = new CostEstimate(project2);
    CostEstimate costEstimate3 = new CostEstimate(project3);
    costEstimateService.addCostEstimate(costEstimate1);
    costEstimateService.addCostEstimate(costEstimate2);
    costEstimateService.addCostEstimate(costEstimate3);
    int costEstimateId = costEstimate2.getId();
    String costEstimate2AsJson = convertToJson(costEstimate2);

    // when
    mockMvc
        .perform(get(URL_TEMPLATE + costEstimateId)
            .accept(CONTENT_TYPE_JSON))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(costEstimate2AsJson));
  }

  @Test
  @DisplayName("Should add cost estimate to repository")
  void shouldAddCostEstimate() throws Exception {
    // given
    Client client = new Client("Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543");
    clientService.addClient(client);
    Project project = new Project(client, "Sample title3", "Sample description3", Status.FINISHED, LocalDate.of(2019, 5, 5),
        LocalDate.of(2019, 6, 22));
    projectService.addProject(project);
    CostEstimate costEstimate = new CostEstimate(project);
    String costEstimateAsJson = convertToJson(costEstimate);

    // when
    mockMvc
        .perform(post(URL_TEMPLATE)
            .contentType(CONTENT_TYPE_JSON)
            .content(costEstimateAsJson))
        .andDo(print())
        // then
        .andExpect(status().isCreated())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.itemList").value(costEstimate.getItemList()));
  }

  @Test
  @DisplayName("Should delete specified cost estimate from repository")
  void shouldDeleteCostEstimate() throws Exception {
    // given
    Client client = new Client("Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543");
    clientService.addClient(client);
    Project project = new Project(client, "Sample title", "Sample description", Status.FINISHED, LocalDate.of(2019, 5, 5),
        LocalDate.of(2019, 6, 22));
    projectService.addProject(project);
    CostEstimate costEstimate = new CostEstimate(project);
    costEstimateService.addCostEstimate(costEstimate);
    int costEstimateId = costEstimate.getId();

    // when
    mockMvc
        .perform(delete(URL_TEMPLATE + costEstimateId))
        .andDo(print())
        // then
        .andExpect(status().isNoContent());

    assertFalse(costEstimateService.findCostEstimateById(costEstimateId).isPresent());
  }

  @Test
  @DisplayName("Should update specified cost estimate")
  void shouldUpdateCostEstimate() throws Exception {
    // given
    Client client1 = new Client("Alpha1", "Alpha address1", "1234567890123456789012331", "048213891", "alpha1@mail.com", "1234541");
    Client client2 = new Client("Alpha2", "Alpha address2", "1234567890123456789012332", "048213892", "alpha2@mail.com", "1234542");
    clientService.addClient(client1);
    clientService.addClient(client2);
    Project project1 = new Project(client1, "Sample title1", "Sample description1", Status.NOT_STARTED, LocalDate.of(2019, 4, 4),
        LocalDate.of(2019, 6, 22));
    Project project2 = new Project(client2, "Sample title2", "Sample description2", Status.FINISHED, LocalDate.of(2019, 5, 5),
        LocalDate.of(2019, 7, 7));
    projectService.addProject(project1);
    projectService.addProject(project2);
    CostEstimate costEstimate = new CostEstimate(project1);
    costEstimateService.addCostEstimate(costEstimate);
    CostEstimate updatedCostEstimate = new CostEstimate(project2);
    String updatedCostEstimateAsJson = convertToJson(updatedCostEstimate);

    // when
    mockMvc
        .perform(put(URL_TEMPLATE + costEstimate.getId())
        .contentType(CONTENT_TYPE_JSON)
        .content(updatedCostEstimateAsJson))
        .andDo(print())
    // then
    .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(jsonPath("$.id").value(costEstimate.getId()))
        .andExpect(jsonPath("$.itemList").value(updatedCostEstimate.getItemList()));
  }
}
