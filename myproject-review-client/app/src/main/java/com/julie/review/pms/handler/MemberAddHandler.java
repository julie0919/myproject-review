package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.julie.review.pms.domain.Member;
import com.julie.review.util.Prompt;


public class MemberAddHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[멤버 등록]");

    Member member = new Member();
    member.setName(Prompt.printString("이름> "));
    member.setMail(Prompt.printString("이메일> "));
    member.setTel(Prompt.printString("연락처> "));
    member.setPw(Prompt.printString("비밀번호> "));

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "insert into review_pms_member(name,mail,tel,pw) values(?,?,?,password(?)")) {

      stmt.setString(1, member.getName());
      stmt.setString(2, member.getMail());
      stmt.setString(3, member.getTel());
      stmt.setString(4, member.getPw());
      stmt.executeUpdate();

      System.out.println("멤버 등록을 완료했습니다.");
    }
  }
}
