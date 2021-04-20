package com.julie.review.pms.handler;

import com.julie.review.driver.Statement;
import com.julie.review.util.Prompt;

public class BoardDeleteHandler implements Command {

  Statement stmt;

  public BoardDeleteHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[게시글 삭제하기]");

    int no = Prompt.printInt("번호> ");

    stmt.executeQuery("board/select", Integer.toString(no));

    String input = Prompt.printString("해당 게시글을 삭제하시겠습니까? (Y/N)");

    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("게시글 삭제를 취소하였습니다.");
      return;
    }

    stmt.executeUpdate("board/delete", Integer.toString(no));

    System.out.println("게시글을 삭제하였습니다.");
  }
}
