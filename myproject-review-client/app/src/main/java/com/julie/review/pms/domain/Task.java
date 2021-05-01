package com.julie.review.pms.domain;

import java.sql.Date;

public class Task {

  private int no;
  private String content;
  private Date deadline;
  private Member leader;
  private int progress;
  private int projectNo;
  private String projectTitle;

  @Override
  public String toString() {
    return "Task [no=" + no + ", content=" + content + ", deadline=" + deadline + ", leader="
        + leader + ", progress=" + progress + ", projectNo=" + projectNo + ", projectTitle="
        + projectTitle + "]";
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getDeadline() {
    return deadline;
  }

  public void setDeadline(Date deadline) {
    this.deadline = deadline;
  }

  public Member getLeader() {
    return leader;
  }

  public void setLeader(Member leader) {
    this.leader = leader;
  }

  public int getProgress() {
    return progress;
  }

  public void setProgress(int progress) {
    this.progress = progress;
  }

  public int getProjectNo() {
    return projectNo;
  }

  public void setProjectNo(int projectNo) {
    this.projectNo = projectNo;
  }

  public String getProjectTitle() {
    return projectTitle;
  }

  public void setProjectTitle(String projectTitle) {
    this.projectTitle = projectTitle;
  }

  public static String getStatusLabel(int status) {
    switch(status) {
      case 2:
        return "진행중";
      case 3:
        return "완료";
      default:
        return "신규";
    }
  }
}
