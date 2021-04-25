package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.julie.review.pms.domain.Task;

public class TaskListHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("-------------------------------");
    System.out.println("[작업 목록]");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select * from review_pms_task order by content asc");
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        System.out.printf("%d) 작업 내용: %s, 마감일: %s, 담당자: %s, 진행상태: %s\n", 
            rs.getInt("no"),
            rs.getString("content"),
            rs.getDate("edt"),
            rs.getString("leader"),
            Task.getStatusLabel(rs.getInt("progress")));      
      }
    }
  }
}
