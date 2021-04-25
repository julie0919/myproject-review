package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.julie.review.util.Prompt;

public class MemberValidator {

  public String inputMember(String promptTitle) throws Exception {

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select count(*) from review_pms_member where name=?")) {

      while (true) {
        String name = Prompt.printString(promptTitle);
        if (name.length() == 0) {
          return null;
        } 
        stmt.setString(1, name);

        try (ResultSet rs = stmt.executeQuery()) {
          rs.next();
          if (rs.getInt(1) > 0) {
            return name;
          }
          System.out.println("등록된 회원이 아닙니다.");          
        }
      }
    }
  }

  public String inputMembers(String promptTitle) throws Exception {
    String members = "";
    while (true) {
      String name = inputMember(promptTitle);
      if (name == null) {
        return members;
      } else {
        if (!members.isEmpty()) {
          members += "/";
        }
        members += name;
      }
    }
  }

}
