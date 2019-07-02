package com.example.almasud.fundamental.checkbox_radio_button_spinner;

import java.io.Serializable;
import java.util.ArrayList;

public class Employee implements Serializable {
    private String name, email, gender, country, platform;
    private ArrayList<String> languageSkills;


    public ArrayList<String> getLanguageSkills() {
        return languageSkills;
    }

    public void setLanguageSkills(ArrayList<String> languageSkills) {
        this.languageSkills = languageSkills;
    }

    public Employee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

}
