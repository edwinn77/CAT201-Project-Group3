package com.example.busticketbookingmanagementsystem;

import java.util.Date;

public class busData {
    private Integer busId;
    private String departLocation;
    private String arriveLocation;
    private String status;
    private Double price;


    public busData(Integer busId, String departLocation, String arriveLocation, String status, Double price){
        this.busId = busId;
        this.departLocation = departLocation;
        this.arriveLocation = arriveLocation;
        this.status = status;
        this.price = price;
    }

    public Integer getBusId(){
        return busId;
    }
//    public String getLocation() {
//        return depart_location;
//    }
    public String getDepartLocation(){return departLocation;}
    public String getArriveLocation(){return arriveLocation; }
    public String getStatus(){
        return status;
    }
    public Double getPrice() {
        return price;
    }
}
