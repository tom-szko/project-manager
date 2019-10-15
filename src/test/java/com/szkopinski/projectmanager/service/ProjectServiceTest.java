package com.szkopinski.projectmanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.szkopinski.projectmanager.model.Client;
import com.szkopinski.projectmanager.model.Project;
import com.szkopinski.projectmanager.model.Status;
import com.szkopinski.projectmanager.repository.ProjectRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

  @Mock
  private ProjectRepository projectRepository;

  private ProjectService projectService;

  @BeforeEach
  void setUp() {
    projectService = new ProjectService(projectRepository);
  }

  @Test
  @DisplayName("Should return all projects")
  void shouldReturnAllProjects() {
    // given
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project project1 = new Project(client, "Sample title1", "Sample description1", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    Project project2 = new Project(client, "Sample title2", "Sample description2", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    Project project3 = new Project(client, "Sample title3", "Sample description3", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    List<Project> expectedProjects = Arrays.asList(project1, project2, project3);
    Mockito.when(projectRepository.findAll()).thenReturn(expectedProjects);

    // when
    Iterable<Project> actualProjects = projectService.findAllProjects();

    // then
    Mockito.verify(projectRepository, Mockito.times(1)).findAll();
    assertEquals(expectedProjects, actualProjects);
  }

  @Test
  @DisplayName("Should return specified project")
  void shouldReturnSpecifiedInvoice() {
    // given
    int projectId = 1;
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project expectedProject = new Project(projectId, client, "Sample title1", "Sample description1", Sets.newHashSet(), Status.FINISHED,
        LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(expectedProject));

    // when
    Optional<Project> actualProject = projectService.findProjectById(projectId);

    // then
    Mockito.verify(projectRepository, Mockito.times(1)).findById(projectId);
    assertEquals(Optional.of(expectedProject), actualProject);
  }

  @Test
  @DisplayName("Should add specified project")
  void shouldAddProject() {
    // given
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project project = new Project(1, client, "Sample title1", "Sample description1", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    Mockito.when(projectRepository.save(project)).thenReturn(project);

    // when
    Project actualProject = projectService.addProject(project);

    // then
    Mockito.verify(projectRepository, Mockito.times(1)).save(project);
    assertEquals(project, actualProject);
  }

  @Test
  @DisplayName("Should delete specified project")
  void shouldDeleteInvoice() {
    // given
    int projectId = 1;
    Mockito.doNothing().when(projectRepository).deleteById(projectId);

    // when
    projectService.deleteProject(projectId);

    // then
    Mockito.verify(projectRepository, Mockito.times(1)).deleteById(projectId);
  }

  @Test
  @DisplayName("Should update specified invoice")
  void shouldUpdateInvoice() {
    // given
    int projectId = 1;
    Client client = new Client(projectId, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543",
        Sets.newHashSet());
    Project project = new Project(projectId, client, "Sample title", "Sample description", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019, 5,
        1), LocalDate.of(2019, 6, 20));
    Project updatedProject = new Project(projectId, client, "Updated title", "Updated description", Sets.newHashSet(), Status.FINISHED,
        LocalDate.of(2019, 5, 1), LocalDate.of(2019, 6, 20));
    Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
    Mockito.when(projectRepository.save(updatedProject)).thenReturn(updatedProject);

    // when
    Project actualProject = projectService.updateProject(projectId, updatedProject);

    // then
    Mockito.verify(projectRepository, Mockito.times(1)).save(updatedProject);
    Mockito.verify(projectRepository, Mockito.times(1)).findById(projectId);
    assertEquals(updatedProject, actualProject);
  }
}
