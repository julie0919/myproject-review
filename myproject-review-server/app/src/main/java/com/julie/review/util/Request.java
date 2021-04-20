package com.julie.review.util;

import java.util.List;

public class Request {
  private String command;
  private List<String> data;

  @Override
  public String toString() {
    return "Requeset [command=" + command + "]";
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public List<String> getData() {
    return data;
  }

  public void setData(List<String> data) {
    this.data = data;
  }

}
