package com.lms.demo.models.openmodel;

public class Registration {

    private String name;
    private String lastname;
    private String email;
    private String password;
    private String repassword;
    private String username;
    private Long your_id;


    public Registration(Long your_id,String name, String lastname, String email, String password, String repassword, String username) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.repassword = repassword;
        this.username = username;
        this.your_id=your_id;
    }

    public Long getYour_id() {
        return your_id;
    }

    public void setYour_id(Long your_id) {
        this.your_id = your_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
