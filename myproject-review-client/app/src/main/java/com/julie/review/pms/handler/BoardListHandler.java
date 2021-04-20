package com.julie.review.pms.handler;

import java.util.Iterator;
import com.julie.review.driver.Statement;

public class BoardListHandler implements Command {

  Statement stmt;

  public BoardListHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[게시글 목록]");

    Iterator<String> results = stmt.executeQuery("board/selectall");

    while (results.hasNext()) {
      String[] fields = results.next().split(",");

      System.out.printf("제목: %s, 내용: %s, 작성자: %s, 등록일: %s, 조회수: %s\n", 
          fields[0], fields[1], fields[2], fields[3], fields[4]);
    }
  }

}
