package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberListHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("-------------------------------");
    System.out.println("[멤버 목록]");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select no,name,mail,tel from review_pms_member order by name asc");
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        System.out.printf("%d) 이름: %s, 이메일: %s, 연락처: %s\n",
            rs.getInt("no"), rs.getString("name"), rs.getString("mail"), rs.getString("tel"));
      }
    }
  }
}
