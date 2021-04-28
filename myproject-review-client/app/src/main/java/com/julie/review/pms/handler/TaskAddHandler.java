package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

    Task task = new Task();
    task.setNo(Prompt.printInt("번호> "));
    task.setContent(Prompt.printString("작업 내용> "));
    task.setDeadline(Prompt.printDate("마감일> "));
    task.setLeader(memberValidator.inputMember("담당자 (취소: 빈 문자열) > "));
    if (task.getLeader() == null) {
      System.out.println("작업 등록을 취소합니다.");
      return;
    } 

    task.setProgress(Prompt.printInt("진행 상태:\n1. 신규\n2. 진행중\n3. 완료\n> "));

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "insert into review_pms_task(content,deadline,leader,progress) values (?,?,?,?)")) {
      stmt.setString(1, task.getContent());
      stmt.setDate(2, task.getDeadline());
      stmt.setInt(3, task.getLeader().getNo());
      stmt.setInt(4, task.getProgress());
      stmt.executeUpdate();

      System.out.println("작업 등록을 완료하였습니다.");
    }
  }
}
