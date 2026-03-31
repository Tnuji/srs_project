package com.example.myapplication.users;

import java.util.ArrayList;
import java.util.List;

public class Vendor extends User{

    private String business_name;
    private String description;
    private List<String> services;

    public Vendor(String email, String password,String phoneNumber, String address, String business_name, String description, List<String> services)
    {
        super(null, null, email, password, 2,phoneNumber, address);
        this.business_name = business_name;
        this.description = description;
        this.services = new ArrayList<>();
    }

    public String getBusiness_name(){return business_name;}
    public String getDescription(){return description;}
    public List<String> getServices(){return services;}
}
