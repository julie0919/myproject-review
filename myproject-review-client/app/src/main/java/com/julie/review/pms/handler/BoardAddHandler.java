package com.julie.review.pms.handler;

import com.julie.review.driver.Statement;
import com.julie.review.pms.domain.Board;
import com.julie.review.util.Prompt;

public class BoardAddHandler implements Command {

  Statement stmt;

  public BoardAddHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[새 게시글]");

    Board b = new Board();

    b.setNo(Prompt.printInt("번호> "));
    b.setTitle(Prompt.printString("제목> "));
    b.setContent(Prompt.printString("내용> "));
    b.setWriter(Prompt.printString("작성자> "));

    stmt.executeUpdate("board/insert", String.format("%s,%s,%s", b.getTitle(), b.getContent(), b.getWriter()));

    System.out.println("게시글 등록을 완료했습니다.");
  }
}
