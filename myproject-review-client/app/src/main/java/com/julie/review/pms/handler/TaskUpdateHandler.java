package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.julie.review.pms.domain.Member;
import com.julie.review.pms.domain.Project;
import com.julie.review.pms.domain.Task;
import com.julie.review.util.Prompt;

public class TaskUpdateHandler implements Command {

  MemberValidator memberValidator;

  public TaskUpdateHandler(MemberValidator memberValidator) {
    this.memberValidator = memberValidator;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[작업 수정하기]");
    int no = Prompt.printInt("번호> ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt1 = con.prepareStatement(
            "select"
                + " t.no,"
                + " t.content,"
                + " t.deadline,"
                + " t.progress,"
                + " m.no as leader_no,"
                + " m.name as leader_name,"
                + " p.no as project_no,"
                + " p.title as project_title"
                + " from review_pms_task t"
                + " inner join review_pms_member m on t.leader=m.no"
                + " inner join review_pms_project p on t.project_no=p.no"
                + " where t.no=?");
        PreparedStatement stmt2 = con.prepareStatement(
            "update review_pms_task set content=?,deadline=?,leader=?,progress=? where no=?")) {

      Task task = new Task();

      // 1) 기존 데이터 조회
      stmt1.setInt(1, no);
      try (ResultSet rs = stmt1.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 작업이 없습니다.");
          return;
        }

        task.setNo(no);
        task.setContent(rs.getString("content"));
        task.setDeadline(rs.getDate("deadline"));

        Member leader = new Member();
        leader.setNo(rs.getInt("leader_no"));
        leader.setName(rs.getString("leader_name"));
        task.setLeader(leader);

        task.setProgress(rs.getInt("progress"));

        task.setProjectNo(rs.getInt("project_no"));
        task.setProjectTitle(rs.getString("project_title"));
      }

      // 2) 프로젝트 제목 출력
      System.out.printf("현재 프로젝트: %s\n", task.getProjectTitle());

      // 3) 현재 프로젝트 목록을 가져온다.
      List<Project> projects = new ArrayList<>();
      try (PreparedStatement stmt3 = con.prepareStatement(
          "select no,title from review_pms_project order by title asc");
          ResultSet rs = stmt3.executeQuery()) {

        while (rs.next()) {
          Project p = new Project();
          p.setNo(rs.getInt("no"));
          p.setTitle(rs.getString("title"));
          projects.add(p);
        }
      }

      // 4) 프로젝트 목록을 출력한다.
      System.out.println("프로젝트 목록: ");
      if (projects.size() == 0) {
        System.out.println("현재 등록된 프로젝트가 없습니다!");
        return;
      }

      for (Project p : projects) {
        System.out.printf("     %d, %s\n", p.getNo(), p.getTitle());
      }

      // 5) 현재 작업이 소속된 프로젝트를 변경한다.
      int selectedProjectNo = 0;
      loop: while (true) {
        try {
          selectedProjectNo = Prompt.printInt("변경할 프로젝트 번호 (취소: 0)> ");
          if (selectedProjectNo == 0) {
            System.out.println("기존 프로젝트를 유지합니다.");
            break loop;
          }
          for (Project p : projects) {
            if (p.getNo() == selectedProjectNo) {
              break loop;
            }
          }
          System.out.println("유효하지 않은 프로젝트 번호입니다.");
        } catch (Exception e) {
          System.out.println("숫자를 입력하세요!");
        }
      }

      if (selectedProjectNo != 0) {
        task.setProjectNo(selectedProjectNo);
      }

      // 6) 사용자에게서 변경할 데이터를 입력받는다.
      task.setContent(Prompt.printString(String.format("작업 내용 (%s)> \n", task.getContent())));
      task.setDeadline(Prompt.printDate(String.format("마감일 (%s)> \n", task.getDeadline())));

      task.setLeader(memberValidator.inputMember(String.format(
          "조장 (%s) (취소: 빈 문자열)> ", task.getLeader().getName())));
      if (task.getLeader() == null) {
        System.out.println("작업 수정을 취소합니다.");
        return; 
      }

      task.setProgress(Prompt.printInt(String.format(
          "진행 상태 (%s)\n1. 신규\n2. 진행중\n3. 완료\n> ", 
          Task.getStatusLabel(task.getProgress()))));   

      String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

      if (!input.equalsIgnoreCase("Y")) {
        System.out.println("작업 수정을 취소하였습니다.");
        return;
      } 

      // 3) DBMS에게 게시글 변경을 요청한다.
      stmt2.setString(1, task.getContent());
      stmt2.setDate(2, task.getDeadline());
      stmt2.setInt(3, task.getLeader().getNo());
      stmt2.setInt(4, task.getProgress());
      stmt2.setInt(5, task.getNo());
      stmt2.executeUpdate();

      System.out.println("작업 정보를 수정하였습니다.");
    }

  }
}
