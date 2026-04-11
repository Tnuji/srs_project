package com.example.myapplication.users;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Vendor extends User implements Parcelable{

    private String business_name;
    private String description;
    private HashMap<String, Double> services;

    public Vendor(String email, String password,String phoneNumber, String address, String business_name, HashMap<String,Double> services)
    {
        super(null, null, email, password, 2,phoneNumber, address);
        this.business_name = business_name;
        this.services = services;
    }

    protected Vendor(Parcel in) {
        super(in); // MUST be first

        business_name = in.readString();
        description = in.readString();

        // HashMap safe approach
        services = (HashMap<String, Double>) in.readSerializable();
    }

    public static final Creator<Vendor> CREATOR = new Creator<Vendor>() {
        @Override
        public Vendor createFromParcel(Parcel in) {
            return new Vendor(in);
        }

        @Override
        public Vendor[] newArray(int size) {
            return new Vendor[size];
        }
    };

    public String getBusiness_name(){return business_name;}
    public String getDescription(){return description;}
    public HashMap<String, Double> getServices(){return services;}
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeString(business_name);
        dest.writeString(description);

        // safest option for HashMap
        dest.writeSerializable(services);
    }
    @Override
    public int describeContents() {return 0;}

}
