package com.szkopinski.projectmanager.controller;

import com.szkopinski.projectmanager.model.Project;
import com.szkopinski.projectmanager.service.ProjectService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
@RequestMapping("api/")
public class ProjectController {

  private ProjectService projectService;

  @Autowired
  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @GetMapping("projects")
  public ResponseEntity<Iterable<Project>> getAllProjects() {
    Sort ascendingOrder = new Sort(Sort.Direction.ASC);
    return ResponseEntity.ok(projectService.findAllProjects());
  }

  @GetMapping("projects/{id}")
  public ResponseEntity getProjectById(@PathVariable("id") int id) {
    Optional<Project> project = projectService.findProjectById(id);
    if (project.isPresent()) {
      return ResponseEntity.ok(project);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("projects")
  public ResponseEntity addProject(@RequestBody Project project) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(projectService.addProject(project));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @DeleteMapping("projects/{id}")
  public ResponseEntity deleteProject(@PathVariable("id") int id) {
    try {
      projectService.deleteProject(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PutMapping("projects/{id}")
  public ResponseEntity updateProject(@PathVariable("id") int id, @RequestBody Project project) {
    try {
      Project updatedProject = projectService.updateProject(id, project);
      if (updatedProject == null) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(updatedProject);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
