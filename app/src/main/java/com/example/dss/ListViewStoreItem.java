package com.example.dss;

public class ListViewStoreItem {

    private String dutyName;
    private String dutyAddr;
    private String dutyTel1;
    double wgs84Lat;
    double wgs84Lon;

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public String getDutyAddr() {
        return dutyAddr;
    }

    public void setDutyAddr(String dutyAddr) {
        this.dutyAddr = dutyAddr;
    }

    public String getDutyTel1() {
        return dutyTel1;
    }

    public void setDutyTel1(String dutyTel1) {
        this.dutyTel1 = dutyTel1;
    }

    public double getWgs84Lat() {
        return wgs84Lat;
    }

    public void setWgs84Lat(double wgs84Lat) {
        this.wgs84Lat = wgs84Lat;
    }

    public double getWgs84Lon() {
        return wgs84Lon;
    }

    public void setWgs84Lon(double wgs84Lon) {
        this.wgs84Lon = wgs84Lon;
    }
}
