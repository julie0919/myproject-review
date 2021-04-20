package com.julie.review.pms.handler;

import com.julie.review.driver.Statement;
import com.julie.review.pms.domain.Task;
import com.julie.review.util.Prompt;

public class TaskDetailHandler implements Command {

  Statement stmt;

  public TaskDetailHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[작업 상세보기]");
    int no = Prompt.printInt("번호> ");

    String[] fields = stmt.executeQuery("task/select", Integer.toString(no)).next().split(",");

    System.out.printf("작업 내용: %s\n", fields[1]);
    System.out.printf("마감일: %s\n", fields[2]);
    System.out.printf("담당자: %s\n", fields[3]);
    System.out.printf("진행상태: %s\n", Task.getStatusLabel(Integer.parseInt(fields[4])));
  }
}
