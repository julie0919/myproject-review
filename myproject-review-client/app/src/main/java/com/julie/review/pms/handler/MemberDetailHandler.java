package com.julie.review.pms.handler;

import com.julie.review.driver.Statement;
import com.julie.review.util.Prompt;

public class MemberDetailHandler implements Command {

  Statement stmt;

  public MemberDetailHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[멤버 상세보기]");

    int no = Prompt.printInt("번호> ");

    String[] fields = stmt.executeQuery("member/select", Integer.toString(no)).next().split(",");

    System.out.printf("이름: %s\n", fields[1]);
    System.out.printf("이메일: %s\n", fields[2]);
    System.out.printf("연락처: %s\n", fields[3]);
  }
}
