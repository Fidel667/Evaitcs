package org.example.entity;

public class Sub {

    private long subId;
    private String subName;
    private String subType;
    private double cost;

    public Sub() {
    }

    public long getSubId() {
        return subId;
    }

    public void setSubId(long subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Sub(String subName, String subType, double cost) {
        this.subId = 0;
        this.subName = subName;
        this.subType = subType;
        this.cost = cost;
    }
}