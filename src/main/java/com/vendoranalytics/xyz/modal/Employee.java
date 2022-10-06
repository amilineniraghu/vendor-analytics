package com.vendoranalytics.xyz.modal;

import java.util.Objects;

public class Employee {
    private String userName;
    private Integer employeeNumber;
    private String firstName;
    private String lastName;
    private String status;
    private String type;
    private String manager;
    private Integer departmentNumber;



    private String fullNameWithTitle;


    private String department;
    private String title;
    private String deskPhone;
    private String building;
    private String site;
    private String vendor;
    private String vendorMask;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Integer getDepartmentNumber() {
        return departmentNumber;
    }

    public void setDepartmentNumber(Integer departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeskPhone() {
        return deskPhone;
    }

    public void setDeskPhone(String deskPhone) {
        this.deskPhone = deskPhone;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVendorMask() {
        return vendorMask;
    }

    public void setVendorMask(String vendorMask) {
        this.vendorMask = vendorMask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return getUserName().equals(employee.getUserName()) && getEmployeeNumber().equals(employee.getEmployeeNumber()) && getFirstName().equals(employee.getFirstName()) && getLastName().equals(employee.getLastName()) && getStatus().equals(employee.getStatus()) && getType().equals(employee.getType()) && getManager().equals(employee.getManager()) && getDepartmentNumber().equals(employee.getDepartmentNumber()) && getDepartment().equals(employee.getDepartment()) && getTitle().equals(employee.getTitle()) && getDeskPhone().equals(employee.getDeskPhone()) && getBuilding().equals(employee.getBuilding()) && getSite().equals(employee.getSite()) && getVendor().equals(employee.getVendor()) && Objects.equals(getVendorMask(), employee.getVendorMask());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName(), getEmployeeNumber(), getFirstName(), getLastName(), getStatus(), getType(), getManager(), getDepartmentNumber(), getDepartment(), getTitle(), getDeskPhone(), getBuilding(), getSite(), getVendor(), getVendorMask());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "userName='" + userName + '\'' +
                ", employeeNumber=" + employeeNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", manager='" + manager + '\'' +
                ", departmentNumber='" + departmentNumber + '\'' +
                ", department='" + department + '\'' +
                ", title='" + title + '\'' +
                ", deskPhone='" + deskPhone + '\'' +
                ", building='" + building + '\'' +
                ", site='" + site + '\'' +
                ", vendor='" + vendor + '\'' +
                ", vendorMask='" + vendorMask + '\'' +
                '}';
    }

    public String getFullNameWithTitle() {
        return firstName + " " + lastName + "(" + title + ")";
    }

}
