package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.julie.review.pms.domain.Project;
import com.julie.review.pms.domain.Task;
import com.julie.review.util.Prompt;

public class TaskAddHandler implements Command {

  MemberValidator memberValidator;

  public TaskAddHandler(MemberValidator memberValidator) {
    this.memberValidator = memberValidator;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[작업 등록]");

    // 1) 현재 등록된 프로젝트 목록을 가져온다.
    List<Project> projects = new ArrayList<>();
    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt1 = con.prepareStatement(
            "select no, title from review_pms_project order by title asc");
        ResultSet rs = stmt1.executeQuery()) {

      while (rs.next()) {
        Project p = new Project();
        p.setNo(rs.getInt("no"));
        p.setTitle(rs.getString("title"));
        projects.add(p);
      }

      // 2) 프로젝트 목록을 출력한다.
      System.out.println("프로젝트 목록: ");
      if (projects.size() == 0) {
        System.out.println("현재 등록된 프로젝트가 없습니다!");
        return;
      }
      for (Project p : projects) {
        System.out.printf("     %d) %s\n", p.getNo(), p.getTitle());
      }

      // 3) 작업을 등록할 프로젝트를 선택한다.
      int selectedProjectNo = 0;
      loop: while (true) {
        String input = Prompt.printString("프로젝트 번호 (취소: 빈 문자열) >");
        if (input.length() == 0) {
          System.out.println("작업 등록을 취소합니다.");
          return;
        }

        try {
          selectedProjectNo = Integer.parseInt(input);
        } catch (Exception e) {
          System.out.println("숫자를 입력하세요!");
          continue;
        }
        for (Project p : projects) {
          if (p.getNo() == selectedProjectNo) {
            break loop;
          }
        }
        System.out.println("유효하지 않은 프로젝트 번호 입니다.");
      }

      // 4) 작업 정보를 입력받는다.
      Task task = new Task();
      task.setContent(Prompt.printString("작업 내용> "));
      task.setDeadline(Prompt.printDate("마감일> "));
      task.setLeader(memberValidator.inputMember("담당자 (취소: 빈 문자열) > "));
      if (task.getLeader() == null) {
        System.out.println("작업 등록을 취소합니다.");
        return;
      } 

      task.setProgress(Prompt.printInt("진행 상태:\n1. 신규\n2. 진행중\n3. 완료\n> "));

      try (PreparedStatement stmt2 = con.prepareStatement(
          "insert into review_pms_task(content,deadline,leader,progress,project_no) values (?,?,?,?,?)")) {
        stmt2.setString(1, task.getContent());
        stmt2.setDate(2, task.getDeadline());
        stmt2.setInt(3, task.getLeader().getNo());
        stmt2.setInt(4, task.getProgress());
        stmt2.setInt(5, selectedProjectNo);
        stmt2.executeUpdate();

        System.out.println("작업 등록을 완료하였습니다.");
      }
    }
  }
}