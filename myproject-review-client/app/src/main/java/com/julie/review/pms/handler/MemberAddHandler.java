package com.julie.review.pms.handler;

import com.julie.review.driver.Statement;
import com.julie.review.pms.domain.Member;
import com.julie.review.util.Prompt;


public class MemberAddHandler implements Command {

  Statement stmt;

  public MemberAddHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[멤버 등록]");

    Member m = new Member();
    m.setName(Prompt.printString("이름> "));
    m.setMail(Prompt.printString("이메일> "));
    m.setTel(Prompt.printString("연락처> "));
    m.setPw(Prompt.printString("비밀번호> "));

    stmt.executeUpdate("member/insert", String.format("%s, %s, %s, %s",
        m.getName(), m.getMail(), m.getTel(), m.getPw()));

    System.out.println("멤버 등록을 완료했습니다.");
  }
}
