package com.julie.review.pms.handler;

import com.julie.review.driver.Statement;
import com.julie.review.pms.domain.Task;
import com.julie.review.util.Prompt;

public class TaskAddHandler implements Command {

  Statement stmt;
  MemberValidator memberValidator;

  public TaskAddHandler(Statement stmt, MemberValidator memberValidator) {
    this.stmt = stmt;
    this.memberValidator = memberValidator;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[작업 등록]");

    Task t = new Task();
    t.setNo(Prompt.printInt("번호> "));
    t.setContent(Prompt.printString("작업 내용> "));
    t.setEndDate(Prompt.printDate("마감일> "));
    t.setLeader(memberValidator.inputMember("담당자 (취소: 빈 문자열) > "));
    if (t.getLeader() == null) {
      System.out.println("작업 등록을 취소합니다.");
      return;
    } 

    t.setProgress(Prompt.printInt("진행 상태:\n1. 신규\n2. 진행중\n3. 완료\n> "));

    stmt.executeUpdate("task/insert", String.format("%s,%s,%s,%s",
        t.getContent(),
        t.getEndDate(),
        t.getLeader(),
        t.getProgress()));

    System.out.println("작업 등록을 완료하였습니다.");
  }
}
