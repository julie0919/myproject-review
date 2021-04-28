package com.julie.review.pms.domain;

import java.sql.Date;
import java.util.List;

public class Project {

  private int no;
  private String title;
  private String content;
  private Date startDate;
  private Date endDate;
  private Member leader;
  private List<Member> team;

  @Override
  public String toString() {
    return "Project [no=" + no + ", title=" + title + ", content=" + content + ", startDate="
        + startDate + ", endDate=" + endDate + ", leader=" + leader + ", team=" + team + "]";
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

  public Member getLeader() {
    return leader;
  }

  public void setLeader(Member leader) {
    this.leader = leader;
  }

  public List<Member> getTeam() {
    return team;
  }

  public void setTeam(List<Member> team) {
    this.team = team;
  }

}
