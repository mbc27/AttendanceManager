package com.rabbiter.am.entity;

import java.util.Date;

public class Chart {

    /**
     * 员工学历分布
     */
    private String item;

    private int count;

    private Double percent;

    /**
     * 员工年龄分布
     */
    private String type;

    private int value;

    /**
     * 近半年每月新入职员工情况
     */
    private String time;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
