package com.szkopinski.projectmanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.szkopinski.projectmanager.model.Client;
import com.szkopinski.projectmanager.model.CostEstimate;
import com.szkopinski.projectmanager.model.Project;
import com.szkopinski.projectmanager.model.Status;
import com.szkopinski.projectmanager.repository.CostEstimateRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CostEstimateServiceTest {

  @Mock
  private CostEstimateRepository costEstimateRepository;

  private CostEstimateService costEstimateService;

  @BeforeEach
  void setUp() {
    costEstimateService = new CostEstimateService(costEstimateRepository);
  }

  @Test
  @DisplayName("Should return all cost estimates")
  void shouldReturnAllCostEstimates() {
    // given
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", new HashSet<>());
    Project project = new Project(client, "Sample title", "Sample description", Status.FINISHED, LocalDate.of(2019, 5, 1), LocalDate.of(2019, 6, 20));
    CostEstimate costEstimate1 = new CostEstimate(1, project, Lists.emptyList());
    CostEstimate costEstimate2 = new CostEstimate(2, project, Lists.emptyList());
    CostEstimate costEstimate3 = new CostEstimate(3, project, Lists.emptyList());
    List<CostEstimate> expectedCostEstimates = Arrays.asList(costEstimate1, costEstimate2, costEstimate3);
    Mockito.when(costEstimateRepository.findAll()).thenReturn(expectedCostEstimates);

    // when
    Iterable<CostEstimate> actualCostEstimates = costEstimateService.findAllCostEstimates();

    // then
    Mockito.verify(costEstimateRepository, Mockito.times(1)).findAll();
    assertEquals(expectedCostEstimates, actualCostEstimates);
  }

  @Test
  @DisplayName("Should return specified cost estimate")
  void shouldReturnCostEstimate() {
    // given
    int costEstimateId = 1;
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project project = new Project(client, "Sample title", "Sample description", Status.FINISHED, LocalDate.of(2019, 5, 1), LocalDate.of(2019, 6, 20));
    Optional<CostEstimate> expectedCostEstimate = Optional.of(new CostEstimate(costEstimateId, project, Lists.emptyList()));
    Mockito.when(costEstimateRepository.findById(costEstimateId)).thenReturn(expectedCostEstimate);

    // when
    Optional<CostEstimate> actualCostEstimate = costEstimateService.findCostEstimateById(costEstimateId);

    // then
    Mockito.verify(costEstimateRepository, Mockito.times(1)).findById(costEstimateId);
    assertEquals(expectedCostEstimate, actualCostEstimate);
  }

  @Test
  @DisplayName("Should add specified cost estimate")
  void shouldAddCostEstimate() {
    // given
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project project = new Project(client, "Sample title", "Sample description", Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    CostEstimate expectedCostEstimate = new CostEstimate(1, project, Lists.emptyList());
    Mockito.when(costEstimateRepository.save(expectedCostEstimate)).thenReturn(expectedCostEstimate);

    // when
    CostEstimate actualCostEstimate = costEstimateService.addCostEstimate(expectedCostEstimate);

    // then
    Mockito.verify(costEstimateRepository, Mockito.times(1)).save(expectedCostEstimate);
    assertEquals(expectedCostEstimate, actualCostEstimate);
  }

  @Test
  @DisplayName("Should delete specified cost estimate")
  void shouldDeleteCostEstimate() {
    // given
    int costEstimateId = 1;
    Mockito.doNothing().when(costEstimateRepository).deleteById(costEstimateId);

    // when
    costEstimateService.deleteCostEstimate(costEstimateId);

    // then
    Mockito.verify(costEstimateRepository, Mockito.times(1)).deleteById(costEstimateId);
  }

  @Test
  @DisplayName("Should update specified cost estimate")
  void shouldUpdateCostEstimate() {
    // given
    int costEstimateId = 1;
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project project1 = new Project(client, "Sample title1", "Sample description1", Status.FINISHED, LocalDate.of(2019, 5, 3),
        LocalDate.of(2019, 6, 20));
    Project project2 = new Project(client, "Sample title2", "Sample description2", Status.FINISHED, LocalDate.of(2019, 5, 5),
        LocalDate.of(2019, 6, 22));
    CostEstimate costEstimate = new CostEstimate(costEstimateId, project1, Lists.emptyList());
    CostEstimate updatedCostEstimate = new CostEstimate(costEstimateId, project2, Lists.emptyList());
    Mockito.when(costEstimateRepository.findById(costEstimateId)).thenReturn(Optional.of(costEstimate));
    Mockito.when(costEstimateRepository.save(updatedCostEstimate)).thenReturn(updatedCostEstimate);

    // when
    CostEstimate actualUpdatedCostEstimate = costEstimateService.updateCostEstimate(costEstimateId, updatedCostEstimate);

    // then
    Mockito.verify(costEstimateRepository, Mockito.times(1)).findById(costEstimateId);
    Mockito.verify(costEstimateRepository, Mockito.times(1)).save(updatedCostEstimate);
    assertEquals(updatedCostEstimate, actualUpdatedCostEstimate);
  }
}
