package com.szkopinski.projectmanager.controller;

import com.szkopinski.projectmanager.model.CostEstimate;
import com.szkopinski.projectmanager.service.CostEstimateService;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/estimates/")
public class CostEstimateController {

  private CostEstimateService costEstimateService;

  @Autowired
  public CostEstimateController(CostEstimateService costEstimateService) {
    this.costEstimateService = costEstimateService;
  }

  @GetMapping
  public ResponseEntity<Iterable<CostEstimate>> findAllCostEstimates() {
    return ResponseEntity.ok(costEstimateService.findAllCostEstimates());
  }

  @GetMapping("{id}")
  public ResponseEntity findCostEstimateById(@PathVariable("id") int id) {
    Optional<CostEstimate> costEstimate = costEstimateService.findCostEstimateById(id);
    if (costEstimate.isPresent()) {
      return ResponseEntity.ok(costEstimate);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity addCostEstimate(@RequestBody @NonNull CostEstimate costEstimate) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(costEstimateService.addCostEstimate(costEstimate));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity deleteCostEstimate(@PathVariable int id) {
    try {
      costEstimateService.deleteCostEstimate(id);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PutMapping("{id}")
  public ResponseEntity updateCostEstimate(@PathVariable int id, @RequestBody @NonNull CostEstimate costEstimate) {
    try {
      CostEstimate updatedCostEstimate = costEstimateService.updateCostEstimate(id, costEstimate);
      if (updatedCostEstimate == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(updatedCostEstimate);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
