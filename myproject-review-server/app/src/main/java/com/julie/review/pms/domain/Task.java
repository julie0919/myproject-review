package com.julie.review.pms.domain;

import java.sql.Date;

public class Task {

  private int no;
  private String content;
  private Date endDate;
  private int progress;
  private String leader;

  public Task() {}

  public Task(String csv) {
    String[] fields = csv.split(","); // 번호, 내용, 마감일, 조장, 진행상태

    this.setNo(Integer.parseInt(fields[0]));
    this.setContent(fields[1]);
    this.setEndDate(Date.valueOf(fields[2]));
    this.setLeader(fields[3]);
    this.setProgress(Integer.parseInt(fields[4]));
  }

  @Override
  public String toString() {
    return "Task [no=" + no + ", content=" + content + ", endDate=" + endDate + ", progress="
        + progress + ", leader=" + leader + "]";
  }

  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%d\n",
        this.getNo(),
        this.getContent(),
        this.getEndDate().toString(),
        this.getLeader(),
        this.getProgress());
  }

  public static Task valueOfCsv(String csv) {
    String[] fields = csv.split(","); // 번호, 내용, 마감일, 조장, 진행상태

    Task t = new Task();
    t.setNo(Integer.parseInt(fields[0]));
    t.setContent(fields[1]);
    t.setEndDate(Date.valueOf(fields[2]));
    t.setLeader(fields[3]);
    t.setProgress(Integer.parseInt(fields[4]));

    return t;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
    result = prime * result + no;
    result = prime * result + ((leader == null) ? 0 : leader.hashCode());
    result = prime * result + ((content == null) ? 0 : content.hashCode());
    result = prime * result + progress;
    return result;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Task other = (Task) obj;
    if (endDate == null) {
      if (other.endDate != null)
        return false;
    } else if (!endDate.equals(other.endDate))
      return false;
    if (no != other.no)
      return false;
    if (leader == null) {
      if (other.leader != null)
        return false;
    } else if (!leader.equals(other.leader))
      return false;
    if (content == null) {
      if (other.content != null)
        return false;
    } else if (!content.equals(other.content))
      return false;
    if (progress != other.progress)
      return false;
    return true;
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
  public Date getEndDate() {
    return endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  public int getProgress() {
    return progress;
  }
  public void setProgress(int progress) {
    this.progress = progress;
  }
  public String getLeader() {
    return leader;
  }
  public void setLeader(String leader) {
    this.leader = leader;
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
