package com.julie.review.pms.handler;

import com.julie.review.driver.Statement;
import com.julie.review.util.Prompt;

public class ProjectDetailHandler implements Command {

  Statement stmt;

  public ProjectDetailHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[프로젝트 상세보기]");
    int no = Prompt.printInt("번호> ");

    String[] fields = stmt.executeQuery("project/select", Integer.toString(no)).next().split(",");

    System.out.printf("프로젝트명: %s\n", fields[1]);
    System.out.printf("내용: %s\n", fields[2]);
    System.out.printf("시작일: %s\n", fields[3]);
    System.out.printf("종료일: %s\n", fields[4]);
    System.out.printf("조장: %s\n", fields[5]);
    System.out.printf("팀원: %s\n", fields[6]);
  }
}
