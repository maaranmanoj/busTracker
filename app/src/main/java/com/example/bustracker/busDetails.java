package com.example.bustracker;

public class busDetails {

    private String busNo, busRoute, busPoint1, busPoint2, currLocation;

    public busDetails(){
    }

    public busDetails(String busNo, String busRoute, String busPoint1,String busPoint2,String currLocation){
        this.busNo=busNo;
        this.busRoute=busRoute;
        this.busPoint1=busPoint1;
        this.busPoint2=busPoint2;
        this.currLocation=currLocation;
    }

    //getter methods
    public String getBusNo(){
        return busNo;
    }

    public String getBusRoute(){
        return busRoute;
    }

    public String getBusPoint1(){
        return busPoint1;
    }

    public String getBusPoint2(){
        return busPoint2;
    }

    public String getCurrLocation(){return currLocation;}

    //setter methods
    public void setBusNo(String busNo){
        this.busNo=busNo;
    }

    public void setBusRoute(String busRoute){
        this.busRoute=busRoute;
    }

    public void setBusPoint1(String busPoint1){
        this.busPoint1=busPoint1;
    }

    public void setBusPoint2(String busPoint2){
        this.busPoint2=busPoint2;
    }

    public void setCurrLocation(String currLocation){this.currLocation=currLocation;}
}
