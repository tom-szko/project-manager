package com.szkopinski.projectmanager.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StatusTest {

  @Test
  @DisplayName("Should return \"not started\" value")
  void shouldReturnNotStartedValue() {
    //given
    Status status = Status.NOT_STARTED;

    //when
    String value = status.getValue();

    //then
    assertEquals("not started", value);
  }

  @Test
  @DisplayName("Should return \"in progress\"")
  void shouldReturnInProgressValue() {
    //given
    Status status = Status.IN_PROGRESS;

    //when
    String value = status.getValue();

    //then
    assertEquals("in progress", value);
  }

  @Test
  @DisplayName("Should return \"finished\" value")
  void shouldReturnFinishedValue() {
    //given
    Status status = Status.FINISHED;

    //when
    String value = status.getValue();

    //then
    assertEquals("finished", value);
  }
}
