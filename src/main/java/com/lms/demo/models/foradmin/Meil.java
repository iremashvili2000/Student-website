package com.lms.demo.models.foradmin;

public class Meil {
    private String meil;
    private String adminPassword;

    public Meil(String meil, String adminPassword) {
        this.meil = meil;
        this.adminPassword = adminPassword;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getMeil() {
        return meil;
    }

    public void setMeil(String meil) {
        this.meil = meil;
    }
}
