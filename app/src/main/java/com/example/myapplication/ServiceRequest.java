package com.example.myapplication;

import com.example.myapplication.users.User;
import com.example.myapplication.users.Vendor;

import java.util.Date;

public class ServiceRequest {
    private int serviceRequestID;
    private String service;
    private String date;
    private Double price;
    private boolean status;
    private Vendor vendor;
    private User customer;
    private String address;
    private boolean accepted;
    public ServiceRequest(String service,String date,Double price,boolean status,Vendor vendor,User customer, String address,boolean accepted)
    {
        this.service = service;
        this.date = date;
        this.price = price;
        this.status = status;
        this.vendor = vendor;
        this.customer = customer;
        this.address = address;
        this.accepted = accepted;
    }

    public void setServiceRequestID(int serviceRequestID){this.serviceRequestID = serviceRequestID;}
    public int getServiceRequestID(){return serviceRequestID;}
    public String getService(){return service;}
    public String getDate(){return date;}
    public Double getPrice(){return price;}
    public boolean getStatus(){return status;}
    public Vendor getVendor(){return vendor;}
    public User getCustomer(){return customer;}
    public String getAddress(){return address;}
    public boolean isAccepted(){return accepted;}

}
