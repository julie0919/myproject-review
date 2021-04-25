package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.julie.review.pms.domain.Project;
import com.julie.review.util.Prompt;

public class ProjectUpdateHandler implements Command {

  MemberValidator memberValidator;

  public ProjectUpdateHandler(MemberValidator memberValidator) {
    this.memberValidator = memberValidator;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[프로젝트 수정하기]");
    int no = Prompt.printInt("번호> ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt1 = con.prepareStatement(
            "select * from review_pms_project where no=?");
        PreparedStatement stmt2 = con.prepareStatement(
            "update review_pms_project set title=?,content=?,sdt=?,edt=?,leader=?,team=? where no=?")) {

      Project project = new Project();

      // 1) 기존 데이터 조회
      stmt1.setInt(1, no);
      try (ResultSet rs = stmt1.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 프로젝트가 없습니다.");
          return;
        }

        project.setNo(no);
        project.setTitle(rs.getString("title"));
        project.setContent(rs.getString("content"));
        project.setStartDate(rs.getDate("sdt"));
        project.setEndDate(rs.getDate("edt"));
        project.setLeader(rs.getString("leader"));
        project.setTeam(rs.getString("team"));
      }

      // 2) 사용자에게서 변경할 데이터를 입력 받는다.
      project.setTitle(Prompt.printString(String.format("프로젝트명 (%s)> \n", project.getTitle())));
      project.setContent(Prompt.printString(String.format("내용 (%s)> \n", project.getContent())));
      project.setStartDate(Prompt.printDate(String.format("시작일 (%s)> \n", project.getStartDate())));
      project.setEndDate(Prompt.printDate(String.format("종료일 (%s)> \n", project.getEndDate())));
      project.setLeader(memberValidator.inputMember(
          String.format("조장 (%s) (취소: 빈 문자열)> ", project.getLeader())));
      if (project.getLeader() == null) {
        System.out.println("프로젝트 수정을 취소합니다.");
        return;
      }

      project.setTeam(memberValidator.inputMembers(
          String.format("팀원 (%s) (완료: 빈 문자열)> \n", project.getTeam())));

      String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

      if (!input.equalsIgnoreCase("Y")) {
        System.out.println("프로젝트 수정을 취소하였습니다.");
        return;
      }

      // 3) DBMS에게 게시글 변경을 요청한다.
      stmt2.setString(1, project.getTitle());
      stmt2.setString(2, project.getContent());
      stmt2.setDate(3, project.getStartDate());
      stmt2.setDate(4, project.getEndDate());
      stmt2.setString(5, project.getLeader());
      stmt2.setString(6, project.getTeam());
      stmt2.setInt(7, project.getNo());
      stmt2.executeUpdate();

      System.out.println("프로젝트 정보를 수정하였습니다.");
    }

  }
}
