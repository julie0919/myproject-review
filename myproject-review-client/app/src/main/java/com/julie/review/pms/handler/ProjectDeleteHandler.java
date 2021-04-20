package com.julie.review.pms.handler;

import com.julie.review.driver.Statement;
import com.julie.review.util.Prompt;

public class ProjectDeleteHandler implements Command {

  Statement stmt;

  public ProjectDeleteHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[프로젝트 삭제하기]");

    int no = Prompt.printInt("번호> ");

    stmt.executeQuery("project/select", Integer.toString(no));

    String input = Prompt.printString("프로젝트를 삭제하시겠습니까? (Y/N)");

    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("프로젝트 삭제를 취소하였습니다.");
      return;
    }    

    stmt.executeUpdate("project/delete", Integer.toString(no));

    System.out.println("프로젝트를 삭제하였습니다.");
  }
}
