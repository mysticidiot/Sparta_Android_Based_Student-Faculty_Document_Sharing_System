package com.mysticidiot.project_1;

public class Sections {
    private int Staff_ID;
    private String Course_ID;
    private String Course_Title;

    public String getCourse_Title() {
        return Course_Title;
    }

    public void setCourse_Title(String course_Title) {
        Course_Title = course_Title;
    }

    public int getStaff_ID() {
        return Staff_ID;
    }

    public void setStaff_ID(int staff_ID) {
        Staff_ID = staff_ID;
    }

    public String getCourse_ID() {
        return Course_ID;
    }

    public void setCourse_ID(String course_ID) {
        Course_ID = course_ID;
    }

    public Sections() {
    }
}
