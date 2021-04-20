package com.julie.review.pms.handler;

import java.util.Iterator;
import com.julie.review.driver.Statement;
import com.julie.review.pms.domain.Task;

public class TaskListHandler implements Command {

  Statement stmt;

  public TaskListHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("-------------------------------");
    System.out.println("[작업 목록]");

    Iterator<String> results = stmt.executeQuery("task/selectall");

    while (results.hasNext()) {
      String[] fields = results.next().split(",");

      System.out.printf("%d) 작업 내용: %s, 마감일: %s, 담당자: %s, 진행상태: %s\n", 
          fields[0], fields[1], fields[2], fields[3], Task.getStatusLabel(Integer.parseInt(fields[4])));      
    }
  }
}
