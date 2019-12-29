package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class Employee {

    @SerializedName("EmployeeID")
    private int employeeID;
    @SerializedName("EmployeeName")
    private String employeeName;
    @SerializedName("Username")
    private String username;
    @SerializedName("Password")
    private String password;
    @SerializedName("DeviceNo")
    private String deviceNo;
    @SerializedName("EmployeeTypeID")
    private int employeeTypeID;
    @SerializedName("ErpID")
    private int erpID;
    @SerializedName("SupervisorID")
    private int supervisorID;
    @SerializedName("CheckInCount")
    private int checkInCount;



    public Employee(int employeeID, String employeeName, String username, String password,String deviceNo,
                    int employeeTypeID, int erpID, int supervisorID,
                    int checkInCount, int warehouseID) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.username = username;
        this.password = password;
        this.deviceNo = deviceNo;
        this.employeeTypeID = employeeTypeID;
        this.erpID = erpID;
        this.supervisorID = supervisorID;
        this.checkInCount = checkInCount;

    }

    public Employee() {
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public int getEmployeeTypeID() {
        return employeeTypeID;
    }

    public void setEmployeeTypeID(int employeeTypeID) {
        this.employeeTypeID = employeeTypeID;
    }

    public int getErpID() {
        return erpID;
    }

    public void setErpID(int erpID) {
        this.erpID = erpID;
    }

    public int getSupervisorID() {
        return supervisorID;
    }

    public void setSupervisorID(int supervisorID) {
        this.supervisorID = supervisorID;
    }

    public int getCheckInCount() {
        return checkInCount;
    }

    public void setCheckInCount(int checkInCount) {
        this.checkInCount = checkInCount;
    }


}
