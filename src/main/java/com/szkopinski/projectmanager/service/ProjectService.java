package com.szkopinski.projectmanager.service;

import com.szkopinski.projectmanager.model.Project;
import com.szkopinski.projectmanager.repository.ProjectRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

  private ProjectRepository repository;

  @Autowired
  public ProjectService(ProjectRepository repository) {
    this.repository = repository;
  }

  public Iterable<Project> findAllProjects() {
    return repository.findAll();
  }

  public Optional<Project> findProjectById(int id) {
    return repository.findById(id);
  }

  @Transactional
  public void deleteProject(int id) {
    repository.deleteById(id);
  }

  @Transactional
  public Project addProject(@NonNull Project project) {
    return repository.save(project);
  }

  @Transactional
  public Project updateProject(int id, @NonNull Project updatedProject) {
    return repository.findById(id)
        .map(projectData -> {
          projectData.setClient(updatedProject.getClient());
          projectData.setTitle(updatedProject.getTitle());
          projectData.setDescription(updatedProject.getDescription());
//          projectData.setCostEstimates(updatedProject.getCostEstimates());
          projectData.setStatus(updatedProject.getStatus());
          projectData.setStartDate(updatedProject.getStartDate());
          projectData.setEndDate(updatedProject.getEndDate());
          return repository.save(projectData);
        }).orElse(null);
  }
}
