package com.vendoranalytics.xyz.modal;

public class EmployeeRow {
    private String buName;
    private String level1;
    private String level2;
    private String level3;
    private String level4;
    private String vendor;
    private Integer usCount;
    private Integer indiaCount;
    private Integer caCount;
    private Integer otherCount;

    public EmployeeRow() {
    }

    public String getBuName() {
        return buName;
    }

    public void setBuName(String buName) {
        this.buName = buName;
    }

    public String getLevel1() {
        return level1;
    }

    public void setLevel1(String level1) {
        this.level1 = level1;
    }

    public String getLevel2() {
        return level2;
    }

    public void setLevel2(String level2) {
        this.level2 = level2;
    }

    public String getLevel3() {
        return level3;
    }

    public void setLevel3(String level3) {
        this.level3 = level3;
    }

    public String getLevel4() {
        return level4;
    }

    public void setLevel4(String level4) {
        this.level4 = level4;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Integer getUsCount() {
        return usCount;
    }

    public void setUsCount(Integer usCount) {
        this.usCount = usCount;
    }

    public Integer getIndiaCount() {
        return indiaCount;
    }

    public void setIndiaCount(Integer indiaCount) {
        this.indiaCount = indiaCount;
    }

    public Integer getCaCount() {
        return caCount;
    }

    public void setCaCount(Integer caCount) {
        this.caCount = caCount;
    }

    public Integer getOtherCount() {
        return otherCount;
    }

    public void setOtherCount(Integer otherCount) {
        this.otherCount = otherCount;
    }

    @Override
    public String toString() {
        return "EmployeeRow{" +
                "buName='" + buName + '\'' +
                ", level1='" + level1 + '\'' +
                ", level2='" + level2 + '\'' +
                ", level3='" + level3 + '\'' +
                ", level4='" + level4 + '\'' +
                ", vendor='" + vendor + '\'' +
                ", usCount=" + usCount +
                ", indiaCount=" + indiaCount +
                ", caCount=" + caCount +
                ", otherCount=" + otherCount +
                '}';
    }
}
