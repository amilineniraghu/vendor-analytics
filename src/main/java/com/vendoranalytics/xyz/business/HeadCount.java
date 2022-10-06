package com.vendoranalytics.xyz.business;

public class HeadCount {
    int offShore;
    int onSite;

    public HeadCount(int offShore, int onSite) {
        this.offShore = offShore;
        this.onSite = onSite;
    }

    public int getOffShore() {
        return offShore;
    }

    public void setOffShore(int offShore) {
        this.offShore = offShore;
    }

    public int getOnSite() {
        return onSite;
    }

    public void setOnSite(int onSite) {
        this.onSite = onSite;
    }
}
