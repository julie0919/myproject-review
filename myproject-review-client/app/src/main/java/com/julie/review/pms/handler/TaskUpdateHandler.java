package com.julie.review.pms.handler;

import java.sql.Date;
import com.julie.review.driver.Statement;
import com.julie.review.pms.domain.Task;
import com.julie.review.util.Prompt;

public class TaskUpdateHandler implements Command {

  Statement stmt;
  MemberValidator memberValidator;

  public TaskUpdateHandler(Statement stmt, MemberValidator memberValidator) {
    this.stmt = stmt;
    this.memberValidator = memberValidator;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[작업 수정하기]");
    int no = Prompt.printInt("번호> ");

    String[] fields = stmt.executeQuery("task/select", Integer.toString(no)).next().split(",");

    String content = Prompt.printString(String.format("작업 내용 (%s)> \n", fields[1]));
    Date endDate = Prompt.printDate(String.format("마감일 (%s)> \n", fields[2]));
    String leader = memberValidator.inputMember(String.format("조장 (%s) (취소: 빈 문자열)> ", fields[3]));
    if (leader == null) {
      System.out.println("작업 수정을 취소합니다.");
      return;
    }

    int progress = Prompt.printInt(String.format("진행 상태 (%s)\n1. 신규\n2. 진행중\n3. 완료\n> ", 
        Task.getStatusLabel(Integer.parseInt(fields[4]))));

    String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("작업 수정을 취소하였습니다.");
      return;
    } 

    stmt.executeUpdate("task/update", String.format("%s,%s,%s,%s,%s", no, content, endDate, leader, progress));
    System.out.println("작업 정보를 수정하였습니다.");
  }
}
