package com.mysticidiot.project_1;

public class userInfo {
    private String regno;
    private boolean Teacher;
    private int dept_id;


    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }

    public int getDept_id() {
        return dept_id;
    }

    public userInfo() {
    }
    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public boolean isTeacher() {
        return Teacher;
    }

    public void setTeacher(boolean teacher) {
        Teacher = teacher;
    }
}
