package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.julie.review.pms.domain.Member;
import com.julie.review.util.Prompt;

public class MemberUpdateHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[멤버 수정하기]");
    int no = Prompt.printInt("번호> ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt1 = con.prepareStatement(
            "select * from review_pms_member where no=?");
        PreparedStatement stmt2 = con.prepareStatement(
            "update review_pms_member set name=?,mail=?,tel=?,pw=password(?) where no=?")) {
      Member member = new Member();

      // 1) 기존 데이터 조회
      stmt1.setInt(1, no);
      try (ResultSet rs = stmt1.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 멤버가 없습니다.");
          return;
        }

        member.setNo(no);
        member.setName(rs.getString("name"));
        member.setMail(rs.getString("mail"));
        member.setTel(rs.getString("tel"));
      }

      // 2) 사용자에게서 변경할 데이터를 입력 받는다.
      member.setName(Prompt.printString(String.format("이름 (%s)> \n", member.getName())));
      member.setMail(Prompt.printString(String.format("이메일 (%s)> \n", member.getMail())));
      member.setTel(Prompt.printString(String.format("연락처 (%s)> \n", member.getTel())));
      member.setPw(Prompt.printString("새 비밀번호> "));

      String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

      if (!input.equalsIgnoreCase("Y")) {
        System.out.println("멤버 수정을 취소하였습니다.");
        return;
      }

      // 3) DBMS 에게 데이터 변경을 요청한다.
      stmt2.setString(1, member.getName());
      stmt2.setString(2, member.getMail());
      stmt2.setString(3, member.getTel());
      stmt2.setString(4, member.getPw());
      stmt2.executeUpdate();

      System.out.println("멤버 정보를 수정하였습니다.");
    }
  }
}
