package com.vendoranalytics.xyz.business;

public class HeadCount {
    int indiaCount;
    int usCount;
    int caCount;
    int otherCount;

    public HeadCount(int indiaCount, int onSite, int caCount, int otherCount) {
        this.indiaCount = indiaCount;
        this.usCount = onSite;
        this.caCount = caCount;
        this.otherCount = otherCount;
    }

    public int getIndiaCount() {
        return indiaCount;
    }

    public void setIndiaCount(int indiaCount) {
        this.indiaCount = indiaCount;
    }

    public int getUsCount() {
        return usCount;
    }

    public void setUsCount(int usCount) {
        this.usCount = usCount;
    }

    public int getCaCount() {
        return caCount;
    }

    public void setCaCount(int caCount) {
        this.caCount = caCount;
    }

    public int getOtherCount() {
        return otherCount;
    }

    public void setOtherCount(int otherCount) {
        this.otherCount = otherCount;
    }
}
