package com.julie.review.pms.handler;

import com.julie.review.driver.Statement;
import com.julie.review.util.Prompt;

public class MemberUpdateHandler implements Command {

  Statement stmt;

  public MemberUpdateHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[멤버 수정하기]");
    int no = Prompt.printInt("번호> ");

    String[] fields = stmt.executeQuery("member/select", Integer.toString(no)).next().split(",");

    String name = Prompt.printString(String.format("이름 (%s)> \n", fields[1]));
    String mail = Prompt.printString(String.format("이메일 (%s)> \n", fields[2]));
    String tel = Prompt.printString(String.format("연락처 (%s)> \n", fields[3]));
    String pw = Prompt.printString(String.format("비밀번호 (%s)> \n", fields[4]));

    String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("멤버 수정을 취소하였습니다.");
      return;
    }

    stmt.executeUpdate("member/update", String.format("%d,%s,%s,%s,%s", no, name, mail, tel, pw));

    System.out.println("멤버 정보를 수정하였습니다.");
  }
}
