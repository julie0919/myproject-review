package com.julie.review.pms.handler;

import com.julie.review.driver.Statement;
import com.julie.review.pms.domain.Project;
import com.julie.review.util.Prompt;

public class ProjectAddHandler implements Command {

  Statement stmt;
  MemberValidator memberValidator;

  public ProjectAddHandler(Statement stmt, MemberValidator memberValidator) {
    this.stmt = stmt;
    this.memberValidator = memberValidator;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[프로젝트 등록]");

    Project p = new Project();
    p.setTitle(Prompt.printString("프로젝트명> "));
    p.setContent(Prompt.printString("내용> "));
    p.setStartDate(Prompt.printDate("시작일> "));
    p.setEndDate(Prompt.printDate("종료일> "));
    p.setLeader(memberValidator.inputMember("조장 (취소: 빈 문자열)> "));
    if (p.getLeader() == null) {
      System.out.println("프로젝트 등록을 취소합니다.");
      return;
    } 
    p.setTeam(memberValidator.inputMembers("팀원 (완료: 빈 문자열)> "));

    stmt.executeUpdate("project/insert", String.format("%s,%s,%s,%s,%s,%s",
        p.getTitle(), p.getContent(), p.getStartDate(), p.getEndDate(), p.getLeader(), p.getTeam()));

    System.out.println("프로젝트를 등록하였습니다.");
  }
}
