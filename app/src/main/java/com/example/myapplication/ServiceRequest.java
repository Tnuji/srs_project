package com.example.myapplication;

import com.example.myapplication.users.User;
import com.example.myapplication.users.Vendor;

import java.util.Date;

public class ServiceRequest {
    private int serviceRequestID;
    private String service;
    private Date date;
    private Double price;
    private boolean status;
    private Vendor vendor;
    private User customer;

    public ServiceRequest(int serviceRequestID,String service,Date date,Double price,boolean status,Vendor vendor,User customer)
    {
        this.serviceRequestID = serviceRequestID;
        this.service = service;
        this.date = date;
        this.price = price;
        this.status = status;
        this.vendor = vendor;
        this.customer = customer;
    }

    public int getServiceRequestID(){return serviceRequestID;}
    public String getService(){return service;}
    public Date getDate(){return date;}
    public Double getPrice(){return price;}
    public boolean getStatus(){return status;}
    public Vendor getVendor(){return vendor;}
    public User getCustomer(){return customer;}

}
