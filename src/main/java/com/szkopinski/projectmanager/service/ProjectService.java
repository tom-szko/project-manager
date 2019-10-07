package com.szkopinski.projectmanager.service;

import com.szkopinski.projectmanager.model.Project;
import com.szkopinski.projectmanager.repository.ProjectRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

  private ProjectRepository repository;

  @Autowired
  public ProjectService(ProjectRepository repository) {
    this.repository = repository;
  }

  public Optional<Project> findProjectById(int id) {
    return repository.findById(id);
  }

  @Transactional
  public boolean deleteProject(int id) {
    if (findProjectById(id).isPresent()) {
      repository.deleteById(id);
      return true;
    }
    return false;
  }

  @Transactional
  public Project addProject(@NonNull Project project) {
    return repository.save(project);
  }

  public Iterable<Project> findAllProjects() {
    Sort sortByTitle = new Sort(Sort.Direction.ASC, "title");
    return repository.findAll(sortByTitle);
  }
}
