package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProjectListHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("-------------------------------");
    System.out.println("[프로젝트 목록]");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select no,title,sdt,edt,leader,team from review_pms_project order by title asc");
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        System.out.printf("%d) 프로젝트명: %s, 시작일: %s, 종료일: %s, 조장: %s, 팀원: [%s]\n", 
            rs.getInt("no"), 
            rs.getString("title"), 
            rs.getDate("sdt"), 
            rs.getDate("edt"), 
            rs.getString("leader"), 
            rs.getString("team"));
      }
    }
  }
}
