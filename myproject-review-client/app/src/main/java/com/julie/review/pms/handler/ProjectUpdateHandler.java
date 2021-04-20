package com.julie.review.pms.handler;

import java.sql.Date;
import com.julie.review.driver.Statement;
import com.julie.review.util.Prompt;

public class ProjectUpdateHandler implements Command {

  Statement stmt;
  MemberValidator memberValidator;

  public ProjectUpdateHandler(Statement stmt, MemberValidator memberValidator) {
    this.stmt = stmt;
    this.memberValidator = memberValidator;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[프로젝트 수정하기]");
    int no = Prompt.printInt("번호> ");

    String[] fields = stmt.executeQuery("project/select", Integer.toString(no)).next().split(",");

    String title = Prompt.printString(String.format("프로젝트명 (%s)> \n", fields[1]));
    String content = Prompt.printString(String.format("내용 (%s)> \n", fields[2]));
    Date startDate = Prompt.printDate(String.format("시작일 (%s)> \n", fields[3]));
    Date endDate = Prompt.printDate(String.format("종료일 (%s)> \n", fields[4]));
    String leader = memberValidator.inputMember(String.format("조장 (%s) (취소: 빈 문자열)> ", fields[5]));
    if (leader == null) {
      System.out.println("프로젝트 수정을 취소합니다.");
      return;
    }

    String team = memberValidator.inputMembers(String.format("팀원 (%s) (완료: 빈 문자열)> \n", fields[6]));

    String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("프로젝트 수정을 취소하였습니다.");
      return;
    }

    stmt.executeUpdate("project/update", String.format("%d,%s,%s,%s,%s,%s,%s", 
        no, title, content, startDate, endDate, leader, team));

    System.out.println("프로젝트 정보를 수정하였습니다.");
  }
}
