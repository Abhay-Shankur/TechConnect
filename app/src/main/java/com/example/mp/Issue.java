package com.example.mp;

public class Issue {
    String id,issue,person,dept,date,status;
    int img;

    public Issue() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Issue(String id, String dept, String issue, String person, String date, int img) {
        this.id = id;
        this.issue = issue;
        this.person = person;
        this.dept = dept;
        this.date = date;
        this.img = img;
        this.status = "Not Solved";
    }

    public Issue(String status, int img) {
        this.status = status;
        this.img = img;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
