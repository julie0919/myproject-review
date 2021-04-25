package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.julie.review.pms.domain.Board;
import com.julie.review.util.Prompt;

public class BoardUpdateHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[게시글 수정하기]");

    int no = Prompt.printInt("번호> ");

    try(Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt1 = con.prepareStatement(
            "select no,title,content from review_pms_board where no=?");
        PreparedStatement stmt2 = con.prepareStatement(
            "update review_pms_board set title=?, content=? where no=?")) {

      Board board = new Board();

      // 1) 기존 데이터 조회
      stmt1.setInt(1, no);
      try (ResultSet rs = stmt1.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 게시글이 없습니다.");
          return;
        }

        board.setNo(no);
        board.setTitle(rs.getString("title"));
        board.setContent(rs.getString("content"));
      }

      // 사용자에게서 변경할 데이터를 입력 받는다.
      board.setTitle(Prompt.printString(String.format("제목 (%s)> \n", board.getTitle())));
      board.setContent(Prompt.printString(String.format("내용 (%s)> \n", board.getContent())));

      String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

      if (!input.equalsIgnoreCase("Y")) {
        System.out.println("게시글 수정을 취소하였습니다.");
        return;
      } 

      // 3) DBMS에게 게시글 변경을 요청한다.
      stmt2.setString(1, board.getTitle());
      stmt2.setString(2, board.getContent());
      stmt2.setInt(3, board.getNo());
      stmt2.executeUpdate();
      System.out.println("게시글을 수정하였습니다.");
    }
  }
}
