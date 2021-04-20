package com.julie.review.pms.handler;

import java.util.Iterator;
import com.julie.review.driver.Statement;

public class MemberListHandler implements Command {

  Statement stmt;

  public MemberListHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("-------------------------------");
    System.out.println("[멤버 목록]");

    Iterator<String> results = stmt.executeQuery("member/selectall");

    while (results.hasNext()) {
      String[] fields = results.next().split(",");

      System.out.printf("%d) 이름: %s, 이메일: %s, 연락처: %s\n",
          fields[0], fields[1], fields[2], fields[3]);
    }
  }
}
