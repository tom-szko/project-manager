package com.szkopinski.projectmanager.service;

import com.szkopinski.projectmanager.model.CostEstimate;
import com.szkopinski.projectmanager.repository.CostEstimateRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CostEstimateService {

  private CostEstimateRepository costEstimateRepository;

  @Autowired
  public CostEstimateService(CostEstimateRepository costEstimateRepository) {
    this.costEstimateRepository = costEstimateRepository;
  }

  public Iterable<CostEstimate> findAllCostEstimates() {
    return costEstimateRepository.findAll();
  }

  public Optional<CostEstimate> findCostEstimateById(int id) {
    return costEstimateRepository.findById(id);
  }

  @Transactional
  public CostEstimate addCostEstimate(CostEstimate costEstimate) {
    return costEstimateRepository.save(costEstimate);
  }

  @Transactional
  public void deleteCostEstimate(int id) {
    costEstimateRepository.deleteById(id);
  }

  @Transactional
  public CostEstimate updateCostEstimate(int id, CostEstimate costEstimate) {
    return costEstimateRepository.findById(id)
        .map(costEstimateData -> {
          costEstimateData.setProject(costEstimate.getProject());
          costEstimateData.setCostEstimateItemList(costEstimate.getCostEstimateItemList());
          return costEstimateRepository.save(costEstimateData);
        }).orElse(null);
  }
}
