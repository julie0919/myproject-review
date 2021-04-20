package com.julie.review.pms;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import com.julie.review.driver.Statement;
import com.julie.review.pms.handler.BoardAddHandler;
import com.julie.review.pms.handler.BoardDeleteHandler;
import com.julie.review.pms.handler.BoardDetailHandler;
import com.julie.review.pms.handler.BoardListHandler;
import com.julie.review.pms.handler.BoardUpdateHandler;
import com.julie.review.pms.handler.Command;
import com.julie.review.pms.handler.MemberAddHandler;
import com.julie.review.pms.handler.MemberDeleteHandler;
import com.julie.review.pms.handler.MemberDetailHandler;
import com.julie.review.pms.handler.MemberListHandler;
import com.julie.review.pms.handler.MemberUpdateHandler;
import com.julie.review.pms.handler.MemberValidator;
import com.julie.review.pms.handler.ProjectAddHandler;
import com.julie.review.pms.handler.ProjectDeleteHandler;
import com.julie.review.pms.handler.ProjectDetailHandler;
import com.julie.review.pms.handler.ProjectListHandler;
import com.julie.review.pms.handler.ProjectUpdateHandler;
import com.julie.review.pms.handler.TaskAddHandler;
import com.julie.review.pms.handler.TaskDeleteHandler;
import com.julie.review.pms.handler.TaskDetailHandler;
import com.julie.review.pms.handler.TaskListHandler;
import com.julie.review.pms.handler.TaskUpdateHandler;
import com.julie.review.util.Prompt;


public class ClientApp {

  // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
  ArrayDeque<String> commandStack = new ArrayDeque<>();
  LinkedList<String> commandQueue = new LinkedList<>();

  String serverAddress;
  int port;

  public static void main(String[] args) {

    ClientApp app = new ClientApp("localhost", 8888);

    try {
      app.execute();

    } catch (Exception e) {
      System.out.println("클라이언트 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }

  public ClientApp(String serverAddress, int port) {
    this.serverAddress = serverAddress;
    this.port = port;
  }

  public void execute() throws Exception {

    // 서버와 통신하는 것을 대행해 줄 객체를 준비한다.
    Statement stmt = new Statement(serverAddress, port);

    // 사용자 명령을 처리하는 객체를 맵에 보관한다.
    HashMap<String,Command> commandMap = new HashMap<>();

    commandMap.put("/board/add", new BoardAddHandler(stmt));
    commandMap.put("/board/list", new BoardListHandler(stmt));
    commandMap.put("/board/detail", new BoardDetailHandler(stmt));
    commandMap.put("/board/update", new BoardUpdateHandler(stmt));
    commandMap.put("/board/delete", new BoardDeleteHandler(stmt));

    commandMap.put("/member/add", new MemberAddHandler(stmt));
    commandMap.put("/member/list", new MemberListHandler(stmt));
    commandMap.put("/member/detail", new MemberDetailHandler(stmt));
    commandMap.put("/member/update", new MemberUpdateHandler(stmt));
    commandMap.put("/member/delete", new MemberDeleteHandler(stmt));
    MemberValidator memberValidator = new MemberValidator(stmt);

    commandMap.put("/project/add", new ProjectAddHandler(stmt, memberValidator));
    commandMap.put("/project/list", new ProjectListHandler(stmt));
    commandMap.put("/project/detail", new ProjectDetailHandler(stmt));
    commandMap.put("/project/update", new ProjectUpdateHandler(stmt, memberValidator));
    commandMap.put("/project/delete", new ProjectDeleteHandler(stmt));

    commandMap.put("/task/add", new TaskAddHandler(stmt, memberValidator));
    commandMap.put("/task/list", new TaskListHandler(stmt));
    commandMap.put("/task/detail", new TaskDetailHandler(stmt));
    commandMap.put("/task/update", new TaskUpdateHandler(stmt, memberValidator));
    commandMap.put("/task/delete", new TaskDeleteHandler(stmt));

    try {

      while (true) {
        String command = Prompt.printString("명령> ");

        if (command.length() == 0) {
          continue;
        }

        // 사용자가 입력한 명령을 보관해둔다.
        commandStack.push(command);
        commandQueue.offer(command);

        try {  
          Command commandHandler = commandMap.get(command);

          if (command.equals("history")) {
            printCommandHistory(commandStack.iterator());
          } else if (command.equals("history2")) {
            printCommandHistory(commandQueue.iterator());
          } else if (command.equalsIgnoreCase("exit")) {
            System.out.println("안녕!");
            return;

          } else if (commandHandler == null) {
            System.out.println("실행할 수 없는 명령입니다.");
          } else {
            commandHandler.service();
          }
        } catch (Exception e) {
          System.out.println("-----------------------------------------");
          System.out.printf("명령어 실행 중 오류 발생: %s\n", e.getMessage());
          System.out.println("-----------------------------------------");
        }
        System.out.println();
      } 
    } catch (Exception e) {
      System.out.println("서버와 통신하는 중에 오류 발생!");
    }
    Prompt.close();
    stmt.close();
  }

  private void printCommandHistory(Iterator<String> iterator) {

    int size = 0;
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
      if ((++size % 5) == 0) {
        String input = Prompt.printString(": ");
        if(input.equalsIgnoreCase("q")) {
          break;
        }
      }
    }
  }
}
