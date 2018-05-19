package com.noumanch.selalf.model;

/**
 * Created by macy on 11/23/17.
 */

public class Reminder {

    String id, name,occasion_name,dayid,monthid,yearid,relation_name;

    public Reminder(String id, String name, String occasion_name, String dayid, String monthid, String yearid, String relation_name) {
        this.id = id;
        this.name = name;
        this.occasion_name = occasion_name;
        this.dayid = dayid;
        this.monthid = monthid;
        this.yearid = yearid;
        this.relation_name = relation_name;
    }

    public Reminder() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccasion_name() {
        return occasion_name;
    }

    public void setOccasion_name(String occasion_name) {
        this.occasion_name = occasion_name;
    }

    public String getDayid() {
        return dayid;
    }

    public void setDayid(String dayid) {
        this.dayid = dayid;
    }

    public String getMonthid() {
        return monthid;
    }

    public void setMonthid(String monthid) {
        this.monthid = monthid;
    }

    public String getYearid() {
        return yearid;
    }

    public void setYearid(String yearid) {
        this.yearid = yearid;
    }

    public String getRelation_name() {
        return relation_name;
    }

    public void setRelation_name(String relation_name) {
        this.relation_name = relation_name;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", occasion_name='" + occasion_name + '\'' +
                ", dayid='" + dayid + '\'' +
                ", monthid='" + monthid + '\'' +
                ", yearid='" + yearid + '\'' +
                ", relation_name='" + relation_name + '\'' +
                '}';
    }
}
