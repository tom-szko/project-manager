package com.szkopinski.projectmanager.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "costEstimates")
public class CostEstimate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id")
  private Project project;

  @OneToMany(mappedBy = "costEstimate", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CostEstimateItem> costEstimateItemList;

  public CostEstimate(Project project, List<CostEstimateItem> costEstimateItemList) {
    this.project = project;
    this.costEstimateItemList = costEstimateItemList;
  }
}
