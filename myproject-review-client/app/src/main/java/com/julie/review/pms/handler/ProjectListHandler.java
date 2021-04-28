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
        PreparedStatement stmt1 = con.prepareStatement(
            "select"
                + " p.no,"
                + " p.title,"
                + " p.sdt,"
                + " p.edt,"
                + " m.no as leader_no,"
                + " m.name as leader_name"
                + " from review_pms_project p"
                + " inner join review_pms_member m on p.leader=m.no"
                + " order by title asc");
        PreparedStatement stmt2 = con.prepareStatement(
            "select"
                + " m.no,"
                + " m.name"
                + " from review_pms_member_project mp"
                + " inner join review_pms_member m on mp.member_no=m.no"
                + " where"
                + " mp.project_no=?");
        ResultSet rs = stmt1.executeQuery()) {

      while (rs.next()) {
        // 1) 프로젝트의 팀원 목록 가져오기
        stmt2.setInt(1, rs.getInt("no"));
        String members = "";
        try (ResultSet memberRs = stmt2.executeQuery()) {
          while (memberRs.next()) {
            if (members.length() > 0) {
              members += "/";
            }
            members += memberRs.getString("name");
          }
        }

        // 2) 프로젝트 정보를 출력
        System.out.printf("%d) 프로젝트명: %s, 시작일: %s, 종료일: %s, 조장: %s, 팀원: [%s]\n", 
            rs.getInt("no"), 
            rs.getString("title"), 
            rs.getDate("sdt"), 
            rs.getDate("edt"), 
            rs.getString("leader_name"), 
            members);
      }
    }
  }
}
