package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.julie.review.pms.domain.Board;
import com.julie.review.pms.domain.Member;
import com.julie.review.util.Prompt;

public class BoardAddHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[게시글 등록]");

    Board b = new Board();

    b.setTitle(Prompt.printString("제목> "));
    b.setContent(Prompt.printString("내용> "));

    Member writer = new Member();
    writer.setNo(Prompt.printInt("작성자 번호> "));
    b.setWriter(writer);

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "insert into review_pms_board(title, content, writer) values(?,?,?)")) {
      stmt.setString(1, b.getTitle());
      stmt.setString(2, b.getContent());
      stmt.setInt(3, b.getWriter().getNo());

      stmt.executeUpdate();

      System.out.println("게시글 등록을 완료했습니다.");
    }
  }
}
