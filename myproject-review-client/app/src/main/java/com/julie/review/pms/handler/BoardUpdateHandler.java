package com.julie.review.pms.handler;

import com.julie.review.driver.Statement;
import com.julie.review.util.Prompt;

public class BoardUpdateHandler implements Command {

  Statement stmt;

  public BoardUpdateHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[게시글 수정하기]");

    int no = Prompt.printInt("번호> ");

    String[] fields = stmt.executeQuery("board/select", Integer.toString(no)).next().split(",");

    String title = Prompt.printString(String.format("제목 (%s)> \n", fields[1]));
    String content = Prompt.printString(String.format("내용 (%s)> \n", fields[2]));

    String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("게시글 수정을 취소하였습니다.");
      return;
    } 

    stmt.executeUpdate("board/update", String.format("%d,%s,%s", no, title, content));

    System.out.println("게시글을 수정하였습니다.");
  }
}
