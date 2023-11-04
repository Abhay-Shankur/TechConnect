package com.example.mp.module;

public class Staff {
    String Name;
    String Email;
    String Dept;
    String Pass;

    public Staff() {
    }

    public Staff(String name, String email, String dept, String pass) {
        Name = name;
        Email = email;
        Dept = dept;
        Pass = pass;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDept() {
        return Dept;
    }

    public void setDept(String dept) {
        Dept = dept;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
