package com.julie.review.pms.domain;

public class Member {

  private int no;
  private String name;
  private String mail;
  private String pw;
  private String tel;

  public Member() {}

  public Member(String csv) {
    String[] fields = csv.split(","); // 번호, 이름, 이메일, 연락처, 비밀번호

    this.setNo(Integer.parseInt(fields[0]));
    this.setName(fields[1]);
    this.setMail(fields[2]);
    this.setTel(fields[3]);
    this.setPw(fields[4]);
  }

  @Override
  public String toString() {
    return "Member [no=" + no + ", name=" + name + ", mail=" + mail + ", pw=" + pw + ", tel=" + tel
        + "]";
  }

  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%d",
        this.getNo(),
        this.getName(),
        this.getMail(),
        this.getTel(),
        this.getPw()); 
  }

  //다음과 같이 인스턴스를 생성해주는 메서드를 
  // "factory method"라 부른다.
  // 팩토리 메서드 패턴
  // - 인스턴스 생성 과정이 복잡할 때 
  //   인스턴스 생성을 대신 해주는 메서드를 만들어
  //   그 메서드를 통해 객체를 생성하는 프로그래밍 방식이다.

  public static Member valueOfCsv(String csv) {
    String[] fields = csv.split(","); // 번호, 이름, 이메일, 연락처, 비밀번호

    Member m = new Member();
    m.setNo(Integer.parseInt(fields[0]));
    m.setName(fields[1]);
    m.setMail(fields[2]);
    m.setTel(fields[3]);
    m.setPw(fields[4]);

    return m;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + no;
    result = prime * result + ((mail == null) ? 0 : mail.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((pw == null) ? 0 : pw.hashCode());
    result = prime * result + ((tel == null) ? 0 : tel.hashCode());
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
    Member other = (Member) obj;
    if (no != other.no)
      return false;
    if (mail == null) {
      if (other.mail != null)
        return false;
    } else if (!mail.equals(other.mail))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (pw == null) {
      if (other.pw != null)
        return false;
    } else if (!pw.equals(other.pw))
      return false;
    if (tel == null) {
      if (other.tel != null)
        return false;
    } else if (!tel.equals(other.tel))
      return false;
    return true;
  }
  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getMail() {
    return mail;
  }
  public void setMail(String mail) {
    this.mail = mail;
  }
  public String getPw() {
    return pw;
  }
  public void setPw(String pw) {
    this.pw = pw;
  }
  public String getTel() {
    return tel;
  }
  public void setTel(String tel) {
    this.tel = tel;
  }
}
