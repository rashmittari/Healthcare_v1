package com.example.healthcare_v1;

import java.util.List;

public class User {

    String name,profession,qualifications,timings,address,fees;
    List<String> available;

    public User(){}

   // public User(String name) {
   //     this.name = name;
   // }

    public User(String name, String profession, String qualifications, String timings, String address, String fees, List<String> available) {
        this.name = name;
        this.profession = profession;
        this.qualifications = qualifications;
        this.timings = timings;
        this.address = address;
        this.fees = fees;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

//


    public List<String> getAvailable() {
        return available;
    }

    public void setAvailable(List<String> available) {
        this.available = available;
    }

   // public List<String> getTags() {
    //    return tags;
   // }

   // public void setTags(List<String> tags) {
   //     this.tags = tags;
    //}
}
