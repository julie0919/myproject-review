package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            "select * from review_pms_task where no=?");
        PreparedStatement stmt2 = con.prepareStatement(
            "update review_pms_task set content=?,edt=?,leader=?,progress=? where no=?")) {

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
        task.setEndDate(rs.getDate("edt"));
        task.setLeader(rs.getString("leader"));
        task.setProgress(rs.getInt("progress"));
      }

      // 2) 사용자에게서 변경할 데이터를 입력받는다.
      task.setContent(Prompt.printString(String.format("작업 내용 (%s)> \n", task.getContent())));
      task.setEndDate(Prompt.printDate(String.format("마감일 (%s)> \n", task.getEndDate())));
      task.setLeader(memberValidator.inputMember(String.format("조장 (%s) (취소: 빈 문자열)> ", task.getLeader())));
      if (task.getLeader() == null) {
        System.out.println("작업 수정을 취소합니다.");
        return;
      }

      task.setProgress(Prompt.printInt(String.format("진행 상태 (%s)\n1. 신규\n2. 진행중\n3. 완료\n> ", 
          Task.getStatusLabel(task.getProgress()))));

      String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

      if (!input.equalsIgnoreCase("Y")) {
        System.out.println("작업 수정을 취소하였습니다.");
        return;
      } 

      // 3) DBMS에게 게시글 변경을 요청한다.
      stmt2.setString(1, task.getContent());
      stmt2.setDate(2, task.getEndDate());
      stmt2.setString(3, task.getLeader());
      stmt2.setInt(4, task.getProgress());
      stmt2.setInt(5, task.getNo());
      stmt2.executeUpdate();
      System.out.println("작업 정보를 수정하였습니다.");
    }

  }
}
