package com.julie.review.pms.handler;

import com.julie.review.driver.Statement;
import com.julie.review.util.Prompt;


public class MemberDeleteHandler implements Command {

  Statement stmt;

  public MemberDeleteHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[멤버 삭제하기]");

    int no = Prompt.printInt("번호> ");

    stmt.executeQuery("member/select", Integer.toString(no));

    String input = Prompt.printString("멤버를 삭제하시겠습니까? (Y/N)");

    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("멤버 삭제를 취소하였습니다.");
      return;
    } 

    stmt.executeUpdate("member/delete", Integer.toString(no));
    System.out.println("멤버를 삭제하였습니다.");
  } 

}
