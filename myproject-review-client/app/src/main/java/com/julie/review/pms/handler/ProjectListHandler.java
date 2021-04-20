package com.julie.review.pms.handler;

import java.util.Iterator;
import com.julie.review.driver.Statement;

public class ProjectListHandler implements Command {

  Statement stmt;

  public ProjectListHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("-------------------------------");
    System.out.println("[프로젝트 목록]");

    Iterator<String> results = stmt.executeQuery("project/selectall");

    while (results.hasNext()) {
      String[] fields = results.next().split(",");

      System.out.printf("%d) 프로젝트명: %s, 내용: %s, 시작일: %s, 종료일: %s, 조장: %s, 팀원: [%s]\n", 
          fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6]);
    }

  }
}
