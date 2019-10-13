package com.szkopinski.projectmanager.repository;

import com.szkopinski.projectmanager.model.CostEstimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostEstimateRepository extends JpaRepository<CostEstimate, Integer> {

}
