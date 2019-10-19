package com.szkopinski.projectmanager.controller;

import static com.szkopinski.projectmanager.helpers.TestHelpers.convertToJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.szkopinski.projectmanager.model.Client;
import com.szkopinski.projectmanager.model.CostEstimate;
import com.szkopinski.projectmanager.model.Project;
import com.szkopinski.projectmanager.model.Status;
import com.szkopinski.projectmanager.service.CostEstimateService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CostEstimateController.class)
class CostEstimateControllerTest {

  private static final String URL_TEMPLATE = "/api/estimates/";
  private static final MediaType CONTENT_TYPE_JSON = MediaType.APPLICATION_JSON_UTF8;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CostEstimateService costEstimateService;

  @Test
  @DisplayName("Should return all cost estimates")
  void shouldReturnAllCostEstimates() throws Exception {
    // given
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project project = new Project(1, client, "Sample title", "Sample description", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    CostEstimate costEstimate1 = new CostEstimate(1, project, Lists.emptyList());
    CostEstimate costEstimate2 = new CostEstimate(2, project, Lists.emptyList());
    CostEstimate costEstimate3 = new CostEstimate(3, project, Lists.emptyList());
    List<CostEstimate> costEstimates = Arrays.asList(costEstimate1, costEstimate2, costEstimate3);
    String costEstimatesAsJson = convertToJson(costEstimates);
    Mockito.when(costEstimateService.findAllCostEstimates()).thenReturn(costEstimates);

    // when
    mockMvc
        .perform(get(URL_TEMPLATE)
            .accept(CONTENT_TYPE_JSON))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(costEstimatesAsJson));

    Mockito.verify(costEstimateService, Mockito.times(1)).findAllCostEstimates();
  }

  @Test
  @DisplayName("Should return cost estimate")
  void shouldReturnCostEstimate() throws Exception {
    // given
    int costEstimateId = 1;
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project project = new Project(1, client, "Sample title", "Sample description", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    CostEstimate costEstimate = new CostEstimate(costEstimateId, project, Lists.emptyList());
    String costEstimateAsJson = convertToJson(costEstimate);
    Mockito.when(costEstimateService.findCostEstimateById(costEstimateId)).thenReturn(Optional.of(costEstimate));

    // when
    mockMvc
        .perform(get(URL_TEMPLATE + costEstimateId)
            .accept(CONTENT_TYPE_JSON))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(costEstimateAsJson));

    Mockito.verify(costEstimateService, Mockito.times(1)).findCostEstimateById(costEstimateId);
  }

  @Test
  @DisplayName("Should add cost estimate")
  void shouldAddCostEstimate() throws Exception {
    // given
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project project = new Project(1, client, "Sample title", "Sample description", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    CostEstimate costEstimate = new CostEstimate(project, Lists.emptyList());
    String costEstimateAsJson = convertToJson(costEstimate);
    Mockito.when(costEstimateService.addCostEstimate(costEstimate)).thenReturn(costEstimate);

    // when
    mockMvc
        .perform(post(URL_TEMPLATE)
            .contentType(CONTENT_TYPE_JSON)
            .content(costEstimateAsJson))
        .andDo(print())
        // then
        .andExpect(status().isCreated())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(costEstimateAsJson));

    Mockito.verify(costEstimateService, Mockito.times(1)).addCostEstimate(costEstimate);
  }

  @Test
  @DisplayName("Should delete specified cost estimate")
  void shouldDeleteCostEstimate() throws Exception {
    // given
    int costEstimateId = 1;
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project project = new Project(1, client, "Sample title", "Sample description", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    CostEstimate costEstimate = new CostEstimate(costEstimateId, project, Lists.emptyList());
    Mockito.doNothing().when(costEstimateService).deleteCostEstimate(costEstimateId);

    // when
    mockMvc
        .perform(delete(URL_TEMPLATE + costEstimateId))
        .andDo(print())
        // then
        .andExpect(status().isNoContent());

    Mockito.verify(costEstimateService, Mockito.times(1)).deleteCostEstimate(costEstimateId);
  }

  @Test
  @DisplayName("Should update specified cost estimate")
  void shouldUpdateCostEstimate() throws Exception {
    // given
    int costEstimateId = 1;
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project project1 = new Project(1, client, "Sample title", "Sample description", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    Project project2 = new Project(2, client, "Sample title2", "Sample description2", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 25));
    CostEstimate updatedCostEstimate = new CostEstimate(costEstimateId, project2, Lists.emptyList());
    String updatedCostEstimateAsJson = convertToJson(updatedCostEstimate);
    Mockito.when(costEstimateService.updateCostEstimate(costEstimateId, updatedCostEstimate)).thenReturn(updatedCostEstimate);

    // when
    mockMvc
        .perform(put(URL_TEMPLATE + costEstimateId)
            .contentType(CONTENT_TYPE_JSON)
            .content(updatedCostEstimateAsJson))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(updatedCostEstimateAsJson));

    Mockito.verify(costEstimateService, Mockito.times(1)).updateCostEstimate(costEstimateId, updatedCostEstimate);
  }
}
