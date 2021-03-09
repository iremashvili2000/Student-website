package com.lms.demo.models.foradmin;

public class AddStudent {

    private String gmail;
    private String subject;

    public AddStudent(String gmail, String subject) {
        this.gmail = gmail;
        this.subject = subject;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
