package com.lms.demo.models;

public class ChangeInfo {

    private String oldpassword;
    private String newPassword;
    private String renewPassword;
    private String newemail;


    public ChangeInfo(String oldpassword, String newPassword, String renewPassword, String newemail) {
        this.oldpassword = oldpassword;
        this.newPassword = newPassword;
        this.renewPassword = renewPassword;
        this.newemail = newemail;

    }



    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getRenewPassword() {
        return renewPassword;
    }

    public void setRenewPassword(String renewPassword) {
        this.renewPassword = renewPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewemail() {
        return newemail;
    }

    public void setNewemail(String newemail) {
        this.newemail = newemail;
    }
}
