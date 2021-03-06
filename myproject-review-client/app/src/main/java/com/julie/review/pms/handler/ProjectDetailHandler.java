package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.julie.review.util.Prompt;

public class ProjectDetailHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[프로젝트 상세보기]");
    int no = Prompt.printInt("번호> ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt1 = con.prepareStatement(
            "select"
                + " p.no,"
                + " p.title,"
                + " p.content,"
                + " p.sdt,"
                + " p.edt,"
                + " m.no as leader_no,"
                + " m.name as leader_name"
                + " from review_pms_project p"
                + " inner join review_pms_member m on p.leader=m.no"
                + " where p.no=?");
        PreparedStatement stmt2 = con.prepareStatement(
            "select"
                + " m.no,"
                + " m.name"
                + " from review_pms_member_project mp"
                + " inner join review_pms_member m on mp.member_no=m.no"
                + " where"
                + " mp.project_no=?")) {

      stmt1.setInt(1, no);

      try (ResultSet rs = stmt1.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 프로젝트가 없습니다.");
          return;
        }

        System.out.printf("프로젝트명: %s\n", rs.getString("title"));
        System.out.printf("내용: %s\n", rs.getString("content"));
        System.out.printf("시작일: %s\n", rs.getDate("sdt"));
        System.out.printf("종료일: %s\n", rs.getDate("edt"));
        System.out.printf("조장: %s\n", rs.getString("leader_name"));

        StringBuilder strings = new StringBuilder();

        stmt2.setInt(1, no);
        try (ResultSet memberRs = stmt2.executeQuery()) {
          while (memberRs.next()) {
            if (strings.length() > 0) {
              strings.append(",");
            }
            strings.append(memberRs.getString("name"));
          }
        }
        System.out.printf("팀원: %s\n", strings);
      }
    }
  }
}
