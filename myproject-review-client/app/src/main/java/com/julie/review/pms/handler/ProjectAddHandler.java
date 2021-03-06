package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import com.julie.review.pms.domain.Member;
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
            "insert into review_pms_project(title,content,sdt,edt,leader) values(?,?,?,?,?)",
            Statement.RETURN_GENERATED_KEYS);
        PreparedStatement stmt2 = con.prepareStatement(
            "insert into review_pms_member_project(member_no,project_no) values (?,?)")) {

      // 수동 커밋으로 설정한다.
      // - review_pms_project 테이블과 review_pms_member_project 테이블에 모두 성공적으로 데이터를 저장했을때
      //    작업을 완료한다.
      con.setAutoCommit(false); // 의미 => 트랜잭션 시작

      // 1) 프로젝트를 추가한다.
      stmt.setString(1, p.getTitle());
      stmt.setString(2, p.getContent());
      stmt.setDate(3, p.getStartDate());
      stmt.setDate(4, p.getEndDate());
      stmt.setInt(5, p.getLeader().getNo());
      stmt.executeUpdate();

      // 프로젝트 데이터의 PK값 알아내기
      try (ResultSet keyRs = stmt.getGeneratedKeys()) {
        keyRs.next();
        p.setNo(keyRs.getInt(1));
      }

      // 2) 프로젝트에 팀원들을 추가한다.
      for (Member member : p.getTeam()) {
        stmt2.setInt(1, member.getNo());
        stmt2.setInt(2, p.getNo());
        stmt2.executeUpdate();
      }

      // 프로젝트 정보뿐만 아니라 팀원 정보도 정상적으로 입력되었다면,
      // 실제 테이블에 데이터를 적용한다.
      con.commit(); // 의미: 트랜잭션 종료
      System.out.println("프로젝트를 등록하였습니다.");
    }
  }
}
