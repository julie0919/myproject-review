package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.julie.review.pms.domain.Member;
import com.julie.review.util.Prompt;

public class MemberValidator {

  public Member inputMember(String promptTitle) throws Exception {

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select no,name,mail from review_pms_member where name=?")) {

      while (true) {
        String name = Prompt.printString(promptTitle);
        if (name.length() == 0) {
          return null;
        } 
        stmt.setString(1, name);

        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) {
            Member member = new Member();
            member.setNo(rs.getInt("no"));
            member.setName(rs.getString("name"));
            member.setMail(rs.getString("mail"));
            return member;
          }
          System.out.println("등록된 회원이 아닙니다.");          
        }
      }
    }
  }

  public List<Member> inputMembers(String promptTitle) throws Exception {

    ArrayList<Member> members = new ArrayList<>();

    while (true) {
      Member member = inputMember(promptTitle);
      if (member == null) {
        return members;
      } else {
        members.add(member);
      }
    }
  }

}
