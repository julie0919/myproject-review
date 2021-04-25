package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.julie.review.util.Prompt;

public class MemberDetailHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[멤버 상세보기]");

    int no = Prompt.printInt("번호> ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select * from review_pms_member where no=?")) {
      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 멤버가 없습니다.");
          return;
        }
        System.out.printf("이름: %s\n", rs.getString("name"));
        System.out.printf("이메일: %s\n", rs.getString("mail"));
        System.out.printf("연락처: %s\n", rs.getString("tel"));
      }
    }
  }
}
