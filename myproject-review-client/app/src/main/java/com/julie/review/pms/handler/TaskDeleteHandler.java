package com.julie.review.pms.handler;

import com.julie.review.driver.Statement;
import com.julie.review.util.Prompt;

public class TaskDeleteHandler implements Command {

  Statement stmt;

  public TaskDeleteHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[작업 삭제하기]");

    int no = Prompt.printInt("번호> ");

    stmt.executeQuery("task/select", Integer.toString(no));

    String input = Prompt.printString("작업을 삭제하시겠습니까? (Y/N)");

    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("작업 삭제를 취소하였습니다.");
      return;
    }    

    stmt.executeUpdate("task/delete", Integer.toString(no));

    System.out.println("작업을 삭제하였습니다.");
  }
}
