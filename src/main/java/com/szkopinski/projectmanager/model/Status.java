package com.szkopinski.projectmanager.model;

public enum Status {
  NOT_STARTED("not started"),
  IN_PROGRESS("in progress"),
  FINISHED("finished");

  private String value;

  Status(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
