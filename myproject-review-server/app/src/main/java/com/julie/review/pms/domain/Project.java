package com.julie.review.pms.domain;

import java.sql.Date;

public class Project {

  private int no;
  private String title;
  private String content;
  private Date startDate;
  private Date endDate;
  private String leader;
  private String team;

  public Project() {}

  public Project(String csv) {
    String[] fields = csv.split(","); // 번호, 제목, 내용, 시작일, 종료일, 조장, 팀원

    this.setNo(Integer.parseInt(fields[0]));
    this.setTitle(fields[1]);
    this.setContent(fields[2]);
    this.setStartDate(Date.valueOf(fields[3]));
    this.setEndDate(Date.valueOf(fields[4]));
    this.setLeader(fields[5]);
    this.setTeam(fields[6].replace("|",","));
  }

  @Override
  public String toString() {
    return "Project [no=" + no + ", title=" + title + ", content=" + content + ", startDate="
        + startDate + ", endDate=" + endDate + ", leader=" + leader + ", team=" + team + "]";
  }

  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%s,%s\n",
        this.getNo(),
        this.getTitle(),
        this.getContent(),
        this.getStartDate().toString(),
        this.getEndDate().toString(),
        this.getLeader(),
        this.getTeam().replace(",", "|"));
  }

  public static Project valueOfCsv(String csv) {
    String[] fields = csv.split(","); // 번호, 제목, 내용, 시작일, 종료일, 조장, 팀원

    Project p = new Project();
    p.setNo(Integer.parseInt(fields[0]));
    p.setTitle(fields[1]);
    p.setContent(fields[2]);
    p.setStartDate(Date.valueOf(fields[3]));
    p.setEndDate(Date.valueOf(fields[4]));
    p.setLeader(fields[5]);
    p.setTeam(fields[6].replace("|",","));

    return p;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((content == null) ? 0 : content.hashCode());
    result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
    result = prime * result + no;
    result = prime * result + ((leader == null) ? 0 : leader.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
    result = prime * result + ((team == null) ? 0 : team.hashCode());
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
    Project other = (Project) obj;
    if (content == null) {
      if (other.content != null)
        return false;
    } else if (!content.equals(other.content))
      return false;
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
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    if (startDate == null) {
      if (other.startDate != null)
        return false;
    } else if (!startDate.equals(other.startDate))
      return false;
    if (team == null) {
      if (other.team != null)
        return false;
    } else if (!team.equals(other.team))
      return false;
    return true;
  }
  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public Date getStartDate() {
    return startDate;
  }
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  public Date getEndDate() {
    return endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  public String getLeader() {
    return leader;
  }
  public void setLeader(String leader) {
    this.leader = leader;
  }
  public String getTeam() {
    return team;
  }
  public void setTeam(String team) {
    this.team = team;
  }
}
