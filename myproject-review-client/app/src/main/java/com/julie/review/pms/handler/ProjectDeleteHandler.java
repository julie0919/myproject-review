package com.julie.review.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.julie.review.util.Prompt;

public class ProjectDeleteHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[프로젝트 삭제하기]");

    int no = Prompt.printInt("번호> ");

    String input = Prompt.printString("프로젝트를 삭제하시겠습니까? (Y/N)");

    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("프로젝트 삭제를 취소하였습니다.");
      return;
    }    

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt1 = con.prepareStatement(
            "delete from review_pms_member_project where project_no=?");
        PreparedStatement stmt2 = con.prepareStatement(
            "delete from review_pms_project where no=?")) {

      con.setAutoCommit(false);

      // 1) 프로젝트에 소속된 팀원 정보 삭제
      stmt1.setInt(1, no);
      stmt1.executeUpdate();

      // 2) 프로젝트 정보 삭제
      stmt2.setInt(1, no);
      if (stmt2.executeUpdate() == 0) {
        System.out.println("해당 번호의 프로젝트가 없습니다.");
      } else {
        con.commit();
        System.out.println("프로젝트를 삭제하였습니다.");
      }
    }
  }
}
