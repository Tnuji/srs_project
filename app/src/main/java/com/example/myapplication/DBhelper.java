/* This is the java class that helps to communicate with the database
   Still a bit nerve racking but i'm getting the hang of it
 */
package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.myapplication.users.User;
import com.example.myapplication.users.Vendor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DBhelper extends SQLiteOpenHelper //Sqlite must be a class and you must extend it.
{
    public static final int DATABASE_VERSION = 12;

    public DBhelper(Context context) //db helper constructor
    {
        super(context, "srs.db", null, DATABASE_VERSION); //similar to what ive been seeing in databases.
    }

    // Table's creator, only called once, is an abstract method
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder users = new StringBuilder();
        users.append("CREATE TABLE users (");
        users.append("user_id INTEGER PRIMARY KEY AUTOINCREMENT,");
        users.append("role INTEGER,");
        users.append("password TEXT,");
        users.append("first_name TEXT,");
        users.append("last_name TEXT,");
        users.append("email TEXT UNIQUE,");
        users.append("address TEXT,");
        users.append("phone INTEGER)");

        db.execSQL(users.toString());


        StringBuilder vendors = new StringBuilder();
        vendors.append("CREATE TABLE vendors (");
        vendors.append("vendor_row_id INTEGER PRIMARY KEY AUTOINCREMENT,");
        vendors.append("user_id INTEGER,");
        vendors.append("business_name TEXT,");
        vendors.append("description TEXT,");
        vendors.append("service TEXT,");
        vendors.append("service_price REAL,");
        vendors.append("UNIQUE(user_id, service),");
        vendors.append("FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE)");
        db.execSQL(vendors.toString());

        StringBuilder serviceRequests = new StringBuilder();
        serviceRequests.append("CREATE TABLE service_requests (");
        serviceRequests.append("service_request_id INTEGER PRIMARY KEY AUTOINCREMENT,");
        serviceRequests.append("service TEXT,");
        serviceRequests.append("date TEXT,");
        serviceRequests.append("price REAL,");
        serviceRequests.append("status INTEGER,");
        serviceRequests.append("vendor_id INTEGER,");
        serviceRequests.append("customer_id INTEGER,");
        serviceRequests.append("accepted INTEGER,");
        serviceRequests.append("address TEXT,");
        serviceRequests.append("FOREIGN KEY(vendor_id) REFERENCES vendors(user_id) ON DELETE CASCADE,");
        serviceRequests.append("FOREIGN KEY(customer_id) REFERENCES users(user_id) ON DELETE CASCADE)");

        db.execSQL(serviceRequests.toString());

        StringBuilder ratings = new StringBuilder();
        ratings.append("CREATE TABLE ratings (");
        ratings.append("rating_id INTEGER PRIMARY KEY AUTOINCREMENT,");
        ratings.append("stars REAL NOT NULL,");
        ratings.append("comment TEXT,");
        ratings.append("rating_date TEXT,");
        ratings.append("customer_id INTEGER,");
        ratings.append("vendor_id INTEGER,");
        ratings.append("FOREIGN KEY(customer_id) REFERENCES users(user_id) ON DELETE CASCADE,");
        ratings.append("FOREIGN KEY(vendor_id) REFERENCES users(user_id) ON DELETE CASCADE)");

        db.execSQL(ratings.toString());

        StringBuilder forgotPassword = new StringBuilder();
        forgotPassword.append("CREATE TABLE forgot_password (");
        forgotPassword.append("user_id INTEGER PRIMARY KEY,");
        forgotPassword.append("color TEXT,");
        forgotPassword.append("city TEXT,");
        forgotPassword.append("FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE)");

        db.execSQL(forgotPassword.toString());

        db.execSQL("INSERT INTO users (role, password, first_name, last_name, email, address, phone) VALUES " +
                "(1, 'pass123', 'John', 'Doe', 'john@email.com', '123 Main St', 1111111111), " +
                "(1, 'pass123', 'Jane', 'Smith', 'jane@email.com', '456 Oak St', 2222222222), " +
                "(2, 'pass123', 'Mike', 'Johnson', 'mike@email.com', '789 Pine St', 3333333333), " +
                "(2, 'pass123', 'Sarah', 'Brown', 'sarah@email.com', '101 Elm St', 4444444444), " +
                "(1, 'pass123', 'Alex', 'Lee', 'alex@email.com', '202 Cedar St', 5555555555), " +
                "(2, 'pass123', 'Priya', 'Patel', 'priya@email.com', '303 Maple St', 6666666666)");

// =======================
// VENDORS TABLE
// =======================
        db.execSQL("INSERT INTO vendors (user_id, business_name, description, service, service_price) VALUES " +
                "(3, 'Mike Fix-It Services', 'Reliable home repair specialist', 'Appliances', 85.0), " +
                "(3, 'Mike Fix-It Services', 'Reliable home repair specialist', 'Electrical', 65.0), " +
                "(3, 'Mike Fix-It Services', 'Reliable home repair specialist', 'Plumbing', 75.0), " +

                "(4, 'Sarah Clean & Safe', 'Trusted cleaning and pest control services', 'Home Cleaning', 55.0), " +
                "(4, 'Sarah Clean & Safe', 'Trusted cleaning and pest control services', 'Pest Control', 90.0), " +

                "(6, 'Priya Tech Tutors', 'Tech help and academic tutoring', 'Computer Repair', 80.0), " +
                "(6, 'Priya Tech Tutors', 'Tech help and academic tutoring', 'Tutoring', 40.0)");

// =======================
// SERVICE REQUESTS
// DATE FORMAT: YYYY-MM-DD HH:mm (24-hour / military time)
// =======================
        db.execSQL("INSERT INTO service_requests (service, date, price, status, vendor_id, customer_id, accepted, address) VALUES " +

                "( 'Appliances', '2026-04-10 09:30', 85.0, 0, 3, 1, 0, '123 Main St'), " +
                "( 'Electrical', '2026-04-11 14:15', 65.0, 1, 3, 2, 1, '456 Oak St'), " +
                "( 'Plumbing', '2026-04-12 18:45', 75.0, 1, 3, 5, 1, '202 Cedar St'), " +

                "( 'Home Cleaning', '2026-04-13 10:00', 55.0, 0, 4, 1, 0, '123 Main St'), " +
                "( 'Pest Control', '2026-04-14 16:30', 90.0, 1, 4, 2, 1, '456 Oak St'), " +

                "( 'Computer Repair', '2026-04-15 13:00', 80.0, 0, 6, 5, 0, '202 Cedar St'), " +
                "( 'Tutoring', '2026-04-16 17:20', 40.0, 1, 6, 1, 1, '123 Main St'), " +

                "( 'Appliances', '2026-04-17 11:10', 85.0, 0, 3, 2, 0, '456 Oak St'), " +
                "( 'Home Cleaning', '2026-04-18 15:50', 55.0, 1, 4, 5, 1, '202 Cedar St')");

// =======================
// RATINGS TABLE
// =======================
        db.execSQL("INSERT INTO ratings (stars, comment, rating_date, customer_id, vendor_id) VALUES " +

                "(5.0, 'Mike fixed my stove perfectly!', '2026-04-10 10:45', 1, 3), " +
                "(4.5, 'Electrical issue solved quickly', '2026-04-11 15:30', 2, 3), " +
                "(4.0, 'Good plumbing work', '2026-04-12 19:10', 5, 3), " +

                "(5.0, 'House is spotless, great cleaning!', '2026-04-13 12:00', 1, 4), " +
                "(4.2, 'Pest issue handled well', '2026-04-14 17:45', 2, 4), " +

                "(5.0, 'Fixed my laptop in one day!', '2026-04-15 14:25', 5, 6), " +
                "(4.8, 'Tutoring really helped my exam prep', '2026-04-16 18:10', 1, 6), " +
                "(4.5, 'Very knowledgeable and patient', '2026-04-17 16:05', 2, 6)");

// =======================
// FORGOT PASSWORD TABLE
// (Alex Lee intentionally left out)
// =======================
        db.execSQL("INSERT INTO forgot_password (user_id, color, city) VALUES " +
                "(1, 'Blue', 'Dallas'), " +
                "(2, 'Green', 'Austin'), " +
                "(3, 'Red', 'Houston'), " +
                "(4, 'Yellow', 'Fort Worth'), " +
                "(6, 'Purple', 'Plano')");
    }

    /*Still unsure about this method, seems to be just about making certain types of changes
      to the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS vendors");
        db.execSQL("DROP TABLE IF EXISTS service_requests");
        db.execSQL("DROP TABLE IF EXISTS ratings");
        db.execSQL("DROP TABLE IF EXISTS forgot_password");
        onCreate(db);
    }

    //function that adds users to the database :)
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("role", user.getRole());
        values.put("password", user.getPassword().trim());
        values.put("first_name", user.getFirstName().trim());
        values.put("last_name", user.getLastName());
        values.put("email", user.getEmail().toLowerCase().trim());
        values.put("address", user.getAddress());
        values.put("phone", user.getphoneNumber().trim());

        return db.insert("users", null, values);
    }

    //function that adds vendor info :)
    public void addVendor(Vendor vendor) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            HashMap<String, Double> services = vendor.getServices();

            for (String service : services.keySet()) {

                ContentValues values = new ContentValues();
                values.put("user_id", vendor.getUserID());
                values.put("business_name", vendor.getBusiness_name().trim());
                values.put("description", vendor.getDescription());
                values.put("service", service.trim());
                values.put("service_price", services.get(service));

                db.insert("vendors", null, values);
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /*public boolean userExists(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE email=? AND password=?",
                new String[]{email, password}
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();

        return exists;
    }*/
    public User getUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE email = ? AND password = ?",
                new String[]{email.toLowerCase().trim(), password.trim()}
        );

        if (cursor.moveToFirst()) {

            long userId = cursor.getLong(cursor.getColumnIndexOrThrow("user_id"));
            long role = cursor.getLong(cursor.getColumnIndexOrThrow("role"));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow("first_name"));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow("last_name"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            String phone_number = cursor.getString(cursor.getColumnIndexOrThrow("phone"));

            User user = new User(
                    firstName,
                    lastName,
                    email,
                    password,
                    role,
                    phone_number,
                    address
            );
            //I also set the userid here :p
            user.setUserID(userId);
            cursor.close();
            return user;
        }

        cursor.close();
        return null;
    }

    public String getEmailByUserId(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT email FROM users WHERE user_id = ?",
                new String[]{String.valueOf(userId)}
        );

        String email = null;

        if (cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
        }

        cursor.close();
        return email;
    }

    public String getPasswordByUserId(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT password FROM users WHERE user_id = ?",
                new String[]{String.valueOf(userId)}
        );

        String password = null;

        if (cursor.moveToFirst()) {
            password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
        }

        cursor.close();
        return password;
    }

    public String getPhoneByUserId(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT phone FROM users WHERE user_id = ?",
                new String[]{String.valueOf(userId)}
        );

        String phoneNumber = null;

        if (cursor.moveToFirst()) {
            phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
        }

        cursor.close();
        return phoneNumber;
    }

    public String getAddressByUserId(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT address FROM users WHERE user_id = ?",
                new String[]{String.valueOf(userId)}
        );

        String address = null;

        if (cursor.moveToFirst()) {
            address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
        }

        cursor.close();
        return address;
    }

    //vendor service stuff
    public List<Vendor> getVendorsByService(String service) {
        List<Vendor> vendors = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM vendors WHERE service = ?",
                new String[]{service}
        );

        if (cursor.moveToFirst()) {
            do {
                Long userId = cursor.getLong(cursor.getColumnIndexOrThrow("user_id"));
                String email = getEmailByUserId(userId);
                String businessName = cursor.getString(cursor.getColumnIndexOrThrow("business_name"));
                String phoneNumber = getPhoneByUserId(userId);
                String address = getAddressByUserId(userId);
                //still gotta add the description lol
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String serviceName = cursor.getString(cursor.getColumnIndexOrThrow("service"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("service_price"));

                // Since your Vendor uses a HashMap
                HashMap<String, Double> services = new HashMap<>();
                services.put(serviceName, price);

                // Create Vendor object
                Vendor vendor = new Vendor(email, "null", phoneNumber, address, businessName, services);

                vendor.setUserID(userId);

                vendors.add(vendor);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return vendors;
    }

    public HashMap<String, Double> getVendorServices(int userId) {

        HashMap<String, Double> services = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT service, service_price FROM vendors WHERE user_id = ?",
                new String[]{String.valueOf(userId)}
        );

        while (cursor.moveToNext()) {

            String service = cursor.getString(
                    cursor.getColumnIndexOrThrow("service")
            );

            double price = cursor.getDouble(
                    cursor.getColumnIndexOrThrow("service_price")
            );

            services.put(service, price);
        }

        cursor.close();
        return services;
    }
    public Vendor getVendorFromUser(User user) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM vendors WHERE user_id = ?",
                new String[]{String.valueOf(user.getUserID())}
        );

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        // We'll build ONE vendor object
        String businessName = cursor.getString(cursor.getColumnIndexOrThrow("business_name"));
        String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        String email = user.getEmail();
        String password = user.getPassword();
        String address = user.getAddress();
        String phoneNumber = user.getphoneNumber();

        // Use map to collect services (prevents duplicates if needed)
        HashMap<String, Double> services = new HashMap<>();

        do {
            String service = cursor.getString(cursor.getColumnIndexOrThrow("service"));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("service_price"));

            services.put(service, price);

        } while (cursor.moveToNext());

        cursor.close();

        //yes I set the user id lmao for the vendor
        Vendor vendor = new Vendor(email,password,phoneNumber,address,businessName,services);
        vendor.setUserID(user.getUserID());

        return vendor;
    }

    public long addServiceRequest(ServiceRequest request) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("service", request.getService());
        values.put("date", request.getDate());
        values.put("price", request.getPrice());
        values.put("status", request.getStatus() ? 1 : 0);
        values.put("vendor_id", request.getVendor().getUserID());
        values.put("customer_id", request.getCustomer().getUserID());
        values.put("address", request.getAddress());
        values.put("accepted", request.isAccepted() ? 1 : 0);

        // Insert into DB
        long id = db.insert("service_requests", null, values);
        db.close();
        return id; // returns inserted row ID (or -1 if failed)
    }
    // all the service requests stuff
    public List<ServiceRequest> getRequestsByUserId(int userId) {

        List<ServiceRequest> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM service_requests WHERE vendor_id = ? OR customer_id = ?",
                new String[]{String.valueOf(userId), String.valueOf(userId)}
        );

        while (cursor.moveToNext()) {

            String service = cursor.getString(cursor.getColumnIndexOrThrow("service"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
            boolean status = cursor.getInt(cursor.getColumnIndexOrThrow("status")) == 1;

            int vendorId = cursor.getInt(cursor.getColumnIndexOrThrow("vendor_id"));
            int customerId = cursor.getInt(cursor.getColumnIndexOrThrow("customer_id"));

            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            boolean accepted = cursor.getInt(cursor.getColumnIndexOrThrow("accepted")) == 1;

            Vendor vendor = getVendorFromUser(getUser(getEmailByUserId(vendorId), getPasswordByUserId(vendorId)));
            User customer = getUser(getEmailByUserId(customerId), getPasswordByUserId(customerId));

            ServiceRequest request = new ServiceRequest(
                    service,
                    date,
                    price,
                    status,
                    vendor,
                    customer,
                    address,
                    accepted
            );

            request.setServiceRequestID(cursor.getInt(cursor.getColumnIndexOrThrow("service_request_id")));
            list.add(request);
        }

        cursor.close();
        return list;
    }

    public void acceptRequest(int requestId) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("accepted", 1);
        db.update(
                "service_requests",
                values,
                "service_request_id = ?",
                new String[]{String.valueOf(requestId)}
        );
    }
    public void declineRequest(int requestId) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                "service_requests",
                "service_request_id = ?",
                new String[]{String.valueOf(requestId)}
        );
    }

    //All the ratings stuff
    public List<Rating> getRatingsForVendor(int vendorId) {

        List<Rating> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM ratings WHERE vendor_id = ?",
                new String[]{String.valueOf(vendorId)}
        );

        while (cursor.moveToNext()) {

            int ratingId = cursor.getInt(cursor.getColumnIndexOrThrow("rating_id"));
            double stars = cursor.getDouble(cursor.getColumnIndexOrThrow("stars"));
            String comment = cursor.getString(cursor.getColumnIndexOrThrow("comment"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("rating_date"));

            int customerId = cursor.getInt(cursor.getColumnIndexOrThrow("customer_id"));

            User customer = getUser(
                    getEmailByUserId(customerId),
                    getPasswordByUserId(customerId)
            );

            Rating rating = new Rating(
                    stars,
                    comment,
                    date,
                    customer,
                    null // vendor optional here
            );

            rating.setRatingID(ratingId);
            list.add(rating);
        }

        cursor.close();
        return list;
    }

    public long addRating(Rating rating) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("stars", rating.getStars());
        values.put("comment", rating.getComment());
        values.put("rating_date", rating.getDate());
        values.put("vendor_id", rating.getVendor().getUserID());
        values.put("customer_id", rating.getCustomer().getUserID());

        long id = db.insert("ratings", null, values);

        db.close();
        return id;
    }

    public String getAverageRatingFormatted(int vendorId) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT AVG(stars) FROM ratings WHERE vendor_id = ?",
                new String[]{String.valueOf(vendorId)}
        );

        double average = 0.0;

        if (cursor.moveToFirst() && !cursor.isNull(0)) {
            average = cursor.getDouble(0);
        }

        cursor.close();
        db.close();

        return String.format("%.1f", average);
    }

    // forget password stuff

    public boolean addForgotPasswordInfo(int userId, String color, String city)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("color", color.trim().toLowerCase());
        values.put("city", city.trim().toLowerCase());

        long result = db.insert("forgot_password", null, values);

        return result != -1;
    }

    public HashMap<String, String> getForgotPasswordInfo(int userId)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT color, city FROM forgot_password WHERE user_id = ?",
                new String[]{String.valueOf(userId)}
        );

        HashMap<String, String> info = new HashMap<>();

        if (cursor.moveToFirst())
        {
            info.put("color", cursor.getString(0));
            info.put("city", cursor.getString(1));
        }

        cursor.close();
        return info;
    }

    //honestly should have had this function earlier lmao
    public int getUserIdByEmail(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT user_id FROM users WHERE email = ?",
                new String[]{email.trim().toLowerCase()}
        );

        int userId = -1;

        if (cursor.moveToFirst())
        {
            userId = cursor.getInt(0);
        }

        cursor.close();
        return userId;
    }

    //changing the password
    public boolean updatePassword(int userId, String newPassword)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("password", newPassword);

        int rows = db.update(
                "users",
                values,
                "user_id = ?",
                new String[]{String.valueOf(userId)}
        );

        return rows > 0;
    }
}
