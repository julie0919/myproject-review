package com.julie.review.pms.table;

import java.io.File;
import java.sql.Date;
import java.util.List;
import com.julie.review.pms.domain.Task;
import com.julie.review.util.JsonFileHandler;
import com.julie.review.util.Request;
import com.julie.review.util.Response;

// JSON 포맷의 파일을 로딩한
public class TaskTable implements DataTable {

  File jsonFile = new File("tasks.json");
  List<Task> list;

  public TaskTable() {
    list = JsonFileHandler.loadObjects(jsonFile, Task.class);
  }

  @Override
  public void service(Request request, Response response) throws Exception {
    Task task = null;
    String[] fields = null;

    switch (request.getCommand()) {
      case "task/insert":

        fields = request.getData().get(0).split(",");

        task = new Task();

        // 새 회원의 번호
        if (list.size() > 0) {
          task.setNo(list.get(list.size() - 1).getNo() + 1);
        } else {
          task.setNo(1);
        }

        task.setContent(fields[0]);
        task.setEndDate(Date.valueOf(fields[1]));
        task.setProgress(Integer.parseInt(fields[2]));
        task.setLeader(fields[3]);

        list.add(task);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;
      case "task/selectall":
        for (Task p : list) {
          response.appendData(String.format("%s,%s,%s,%s,%s", 
              p.getNo(), 
              p.getContent(), 
              p.getEndDate(), 
              p.getProgress(), 
              p.getLeader()));
        }
        break;
      case "task/select":
        int no = Integer.parseInt(request.getData().get(0));

        task = getTask(no);
        if (task != null) {
          response.appendData(String.format("%d,%s,%s,%s,%s", 
              task.getNo(), 
              task.getContent(),
              task.getEndDate(),
              task.getProgress(),
              task.getLeader()));
        } else {
          throw new Exception("해당 번호의 작업이 없습니다.");
        }
        break;
      case "task/update":
        fields = request.getData().get(0).split(",");

        task = getTask(Integer.parseInt(fields[0]));
        if (task == null) {
          throw new Exception("해당 번호의 작업이 없습니다.");
        }

        task.setContent(fields[1]);
        task.setEndDate(Date.valueOf(fields[2]));
        task.setProgress(Integer.parseInt(fields[3]));
        task.setLeader(fields[4]);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;
      case "task/delete":
        no = Integer.parseInt(request.getData().get(0));
        task = getTask(no);
        if (task == null) {
          throw new Exception("해당 번호의 작업이 없습니다.");
        }

        list.remove(task);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;
      default:
        throw new Exception("해당 명령을 처리할 수 없습니다.");
    }
  }

  private Task getTask(int taskNo) {
    for (Task t : list) {
      if (t.getNo() == taskNo) {
        return t;
      }
    }
    return null;
  }
}

