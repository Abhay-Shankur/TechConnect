package com.example.mp.module;

public class Member {
    String Name;
    String Email;
    String Phone;
    String Pass;

    public Member() {
    }

    public Member(String name, String email, String phone, String pass) {
        Name = name;
        Email = email;
        Phone = phone;
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

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
