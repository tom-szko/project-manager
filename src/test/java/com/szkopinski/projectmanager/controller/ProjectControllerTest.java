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
import com.szkopinski.projectmanager.model.Project;
import com.szkopinski.projectmanager.model.Status;
import com.szkopinski.projectmanager.service.ProjectService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ProjectController.class)
class ProjectControllerTest {

  private static final String URL_TEMPLATE = "/api/projects/";
  private static final MediaType CONTENT_TYPE_JSON = MediaType.APPLICATION_JSON_UTF8;

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ProjectService projectService;

  @Test
  @DisplayName("Should return all projects")
  void shouldReturnAllProjects() throws Exception {
    // given
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project project1 = new Project(client, "Sample title1", "Sample description1", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    Project project2 = new Project(client, "Sample title2", "Sample description2", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    Project project3 = new Project(client, "Sample title3", "Sample description3", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    List<Project> projects = Arrays.asList(project1, project2, project3);
    String projectsAsJson = convertToJson(projects);
    Mockito.when(projectService.findAllProjects()).thenReturn(projects);

    // when
    mockMvc
        .perform(get(URL_TEMPLATE)
            .accept(CONTENT_TYPE_JSON))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(projectsAsJson));

    Mockito.verify(projectService, Mockito.times(1)).findAllProjects();
  }

  @Test
  @DisplayName("Should return project with specified id")
  void shouldReturnProjectById() throws Exception {
    // given
    int projectId = 1;
    Client client = new Client(1, "Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project project = new Project(client, "Sample title1", "Sample description1", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    String projectAsJson = convertToJson(project);
    Mockito.when(projectService.findProjectById(projectId)).thenReturn(Optional.of(project));

    // when
    mockMvc
        .perform(get(URL_TEMPLATE + projectId)
            .accept(CONTENT_TYPE_JSON))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(projectAsJson));

    Mockito.verify(projectService, Mockito.times(1)).findProjectById(projectId);
  }

  @Test
  @DisplayName("Should add project")
  void shouldAddProject() throws Exception {
    // given
    Client client = new Client("Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project project = new Project(client, "Sample title1", "Sample description1", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019, 5, 1),
        LocalDate.of(2019, 6, 20));
    String projectAsJson = convertToJson(project);
    Mockito.when(projectService.addProject(project)).thenReturn(project);

    // when
    mockMvc
        .perform(post(URL_TEMPLATE)
            .contentType(CONTENT_TYPE_JSON)
            .content(projectAsJson))
        .andDo(print())
        // then
        .andExpect(status().isCreated())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(projectAsJson));

    Mockito.verify(projectService, Mockito.times(1)).addProject(project);
  }

  @Test
  @DisplayName("Should delete project")
  void shouldDeleteProject() throws Exception {
    // given
    int projectId = 1;
    Mockito.doNothing().when(projectService).deleteProject(projectId);

    // when
    mockMvc
        .perform(delete(URL_TEMPLATE + projectId)
            .accept(CONTENT_TYPE_JSON))
        .andDo(print())
        // then
        .andExpect(status().isNoContent());

    Mockito.verify(projectService, Mockito.times(1)).deleteProject(projectId);
  }

  @Test
  @DisplayName("Should update specified project")
  void shouldUpdateProject() throws Exception {
    // given
    int projectId = 1;
    Client client = new Client("Alpha", "Alpha address", "1234567890123456789012334", "048213898", "alpha@mail.com", "1234543", Sets.newHashSet());
    Project updatedProject = new Project(projectId, client, "Updated title", "Updated description", Sets.newHashSet(), Status.FINISHED, LocalDate.of(2019
        , 5, 3), LocalDate.of(2019, 6, 22));
    String projectAsJson = convertToJson(updatedProject);
    Mockito.when(projectService.updateProject(projectId, updatedProject)).thenReturn(updatedProject);

    // when
    mockMvc
        .perform(put(URL_TEMPLATE + projectId)
            .contentType(CONTENT_TYPE_JSON)
            .content(projectAsJson))
        .andDo(print())
        // then
        .andExpect(status().isOk())
        .andExpect(content().contentType(CONTENT_TYPE_JSON))
        .andExpect(content().json(projectAsJson));

    Mockito.verify(projectService, Mockito.times(1)).updateProject(projectId, updatedProject);
  }
}
