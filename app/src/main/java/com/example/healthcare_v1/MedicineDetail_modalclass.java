package com.example.healthcare_v1;

import java.io.Serializable;

public class MedicineDetail_modalclass implements Serializable {

    public String cricketerName;
    public String teamName;

    public MedicineDetail_modalclass() {

    }

    public MedicineDetail_modalclass(String cricketerName, String teamName) {
        this.cricketerName = cricketerName;
        this.teamName = teamName;
    }

    public String getCricketerName() {
        return cricketerName;
    }

    public void setCricketerName(String cricketerName) {
        this.cricketerName = cricketerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
