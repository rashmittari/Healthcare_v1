package com.example.healthcare_v1;

public class Detuser {

    String address, fees, name,profession,qualifications,timings,status;
    String Pname,Age,Date,Gender,Time,user_id;


    public Detuser(){}

    public Detuser(String address, String fees, String name, String profession, String qualifications, String timings, String status, String Pname,String Age,
                   String Gender,String Date,String Time,String user_id) {
        this.address = address;
        this.fees = fees;
        this.name = name;
        this.profession = profession;
        this.qualifications = qualifications;
        this.timings = timings;
        this.status = status;

        this.Pname = Pname;
        this.Age= Age;
        this.Gender = Gender;
        this.Date = Date;
        this.Time = Time;
        this.user_id = user_id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //


    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
