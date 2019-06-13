package com.example.dss;

import java.sql.Timestamp;

public class User {
    private String id;
    private String email;
    private String password;
    private String nickname;
    private String birth;

    private Timestamp create_date;
    private Timestamp update_date;

    public User( String id, String pwd, String nickname, String email, String birth) {
        this.id = id;
        this.password = pwd;
        this.nickname = nickname;
        this.email = email;
        this.birth = birth;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Timestamp getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    public Timestamp getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Timestamp update_date) {
        this.update_date = update_date;
    }
}
