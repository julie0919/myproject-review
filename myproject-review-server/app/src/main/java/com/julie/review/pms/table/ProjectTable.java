package com.julie.review.pms.table;

import java.io.File;
import java.sql.Date;
import java.util.List;
import com.julie.review.pms.domain.Project;
import com.julie.review.util.JsonFileHandler;
import com.julie.review.util.Request;
import com.julie.review.util.Response;

// JSON 포맷의 파일을 로딩한
public class ProjectTable implements DataTable {

  File jsonFile = new File("projects.json");
  List<Project> list;

  public ProjectTable() {
    list = JsonFileHandler.loadObjects(jsonFile, Project.class);
  }

  @Override
  public void service(Request request, Response response) throws Exception {
    Project project = null;
    String[] fields = null;

    switch (request.getCommand()) {
      case "project/insert":

        fields = request.getData().get(0).split(",");

        project = new Project();

        // 새 회원의 번호
        if (list.size() > 0) {
          project.setNo(list.get(list.size() - 1).getNo() + 1);
        } else {
          project.setNo(1);
        }

        project.setTitle(fields[0]);
        project.setContent(fields[1]);
        project.setStartDate(Date.valueOf(fields[2]));
        project.setEndDate(Date.valueOf(fields[3]));
        project.setLeader(fields[4]);
        project.setTeam(fields[5]);

        list.add(project);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;
      case "project/selectall":
        for (Project p : list) {
          response.appendData(String.format("%d,%s,%s,%s,%s,%s", 
              p.getNo(), 
              p.getTitle(), 
              p.getStartDate(), 
              p.getEndDate(), 
              p.getLeader(),
              p.getTeam()));
        }
        break;
      case "project/select":
        int no = Integer.parseInt(request.getData().get(0));

        project = getProject(no);
        if (project != null) {
          response.appendData(String.format("%d,%s,%s,%s,%s,%s,%s", 
              project.getNo(), 
              project.getTitle(), 
              project.getContent(),
              project.getStartDate(),
              project.getEndDate(),
              project.getLeader(),
              project.getTeam()));
        } else {
          throw new Exception("해당 번호의 프로젝트가 없습니다.");
        }
        break;
      case "project/update":
        fields = request.getData().get(0).split(",");

        project = getProject(Integer.parseInt(fields[0]));
        if (project == null) {
          throw new Exception("해당 번호의 프로젝트가 없습니다.");
        }

        project.setTitle(fields[1]);
        project.setContent(fields[2]);
        project.setStartDate(Date.valueOf(fields[3]));
        project.setEndDate(Date.valueOf(fields[4]));
        project.setLeader(fields[5]);
        project.setTeam(fields[6]);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;
      case "project/delete":
        no = Integer.parseInt(request.getData().get(0));
        project = getProject(no);
        if (project == null) {
          throw new Exception("해당 번호의 프로젝트가 없습니다.");
        }

        list.remove(project);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;
      default:
        throw new Exception("해당 명령을 처리할 수 없습니다.");
    }
  }

  private Project getProject(int projectNo) {
    for (Project p : list) {
      if (p.getNo() == projectNo) {
        return p;
      }
    }
    return null;
  }
}
