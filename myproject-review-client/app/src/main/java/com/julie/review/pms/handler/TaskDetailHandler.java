package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.julie.review.pms.domain.Task;
import com.julie.review.util.Prompt;

public class TaskDetailHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[작업 상세보기]");
    int no = Prompt.printInt("번호> ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select * from review_pms_task where no=?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 작업이 없습니다.");
          return;
        }
        System.out.printf("작업 내용: %s\n", rs.getString("content"));
        System.out.printf("마감일: %s\n", rs.getDate("endDate"));
        System.out.printf("담당자: %s\n", rs.getString("leader"));
        System.out.printf("진행상태: %s\n", Task.getStatusLabel(rs.getInt("progress")));
      }
    }
  }
}
