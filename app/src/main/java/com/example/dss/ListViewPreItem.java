package com.example.dss;

public class ListViewPreItem {

    int id;
    String HOSPITAL_NAME;
    String DOSES_DAY;
    String DOSES_TIME;
    String CREATE_DATE;


    public String getHOSPITAL_NAME() {
        return HOSPITAL_NAME;
    }

    public void setHOSPITAL_NAME(String HOSPITAL_NAME) {
        this.HOSPITAL_NAME = HOSPITAL_NAME;
    }

    public String getDOSES_DAY() {
        return DOSES_DAY;
    }

    public void setDOSES_DAY(String DOSES_DAY) {
        this.DOSES_DAY = DOSES_DAY;
    }

    public String getDOSES_TIME() {
        return DOSES_TIME;
    }

    public void setDOSES_TIME(String DOSES_TIME) {
        this.DOSES_TIME = DOSES_TIME;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
