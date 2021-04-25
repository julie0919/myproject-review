package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.julie.review.util.Prompt;

public class BoardDeleteHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[게시글 삭제하기]");

    int no = Prompt.printInt("번호> ");

    String input = Prompt.printString("해당 게시글을 삭제하시겠습니까? (Y/N)");

    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("게시글 삭제를 취소하였습니다.");
      return;
    }

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "delete from review_pms_board where no=?")) {

      stmt.setInt(1, no);
      if (stmt.executeUpdate() == 0) {
        System.out.println("해당 번호의 게시글이 없습니다.");
      } else {
        System.out.println("게시글을 삭제하였습니다.");
      }
    }
  }
}