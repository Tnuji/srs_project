// user super class, subclasses include customer & vendor
package com.example.myapplication.users;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User implements Parcelable
{

    protected long userID;
    protected String firstName;
    protected String lastName;
    protected String password;
    protected String email;
    protected String phoneNumber;
    protected long role;
    protected String address;


    public User(String firstName, String lastName, String email, String password, long role, String phoneNumber, String address)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    protected User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        password = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        role = in.readLong();
        address = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public static boolean validateNumber(String phoneNumber)
    {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        return true;
    }
    public static boolean validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return true;
    }
    public static boolean validateEmail(String email) {
        if (email == null || !email.contains("@")) {
            return false;
        }
        return true;
    }
    public static boolean validateRole(long role)
    {
        if(role < 0 || role > 2)
        {
            return false;
        }
        return true;
    }

    public void setUserID(long userID)
    {
        this.userID = userID;
    }

    public long getUserID(){return userID;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public String getphoneNumber(){return phoneNumber;}
    public long getRole(){return role;}
    public String getAddress(){return address;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(phoneNumber);
        dest.writeLong(role);
        dest.writeString(address);
    }
}
