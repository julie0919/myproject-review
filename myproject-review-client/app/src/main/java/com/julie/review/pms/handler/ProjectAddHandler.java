package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.julie.review.pms.domain.Project;
import com.julie.review.util.Prompt;

public class ProjectAddHandler implements Command {

  MemberValidator memberValidator;

  public ProjectAddHandler(MemberValidator memberValidator) {
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

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "insert into review_pms_project(title,content,sdt,edt,leader,team"
                + "values(?,?,?,?,?,?")) {

      stmt.setString(1, p.getTitle());
      stmt.setString(2, p.getContent());
      stmt.setDate(3, p.getStartDate());
      stmt.setDate(4, p.getEndDate());
      stmt.setString(5, p.getLeader());
      stmt.setString(6, p.getTeam());
      stmt.executeUpdate();

      System.out.println("프로젝트를 등록하였습니다.");
    }
  }
}
