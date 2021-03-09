package com.lms.demo.service;


import com.lms.demo.models.openmodel.Registration;

public interface OpenService {
    boolean registration(Registration registration);

    String getAbout();
}
