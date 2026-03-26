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


public class DBhelper extends SQLiteOpenHelper //Sqlite must be a class and you must extend it.
{
    public static final int DATABASE_VERSION = 2;
    public DBhelper(Context context) //db helper constructor
    {
        super(context, "srs.db", null, DATABASE_VERSION); //similar to what ive been seeing in databases.
    }

    /* Table's creator, only called once, is an abstract method*/
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        StringBuilder users = new StringBuilder();
        users.append("CREATE TABLE users (");
        users.append("user_id INTEGER PRIMARY KEY AUTOINCREMENT,");
        users.append("role INTEGER,");
        users.append("password TEXT,");users.append("first_name TEXT,");
        users.append("last_name TEXT,");users.append("email TEXT UNIQUE,");
        users.append("address TEXT,"); users.append("phone INTEGER)");//users.append("role INTEGER)");

        db.execSQL(users.toString());
    }

    /*Still unsure about this method, seems to be just about making certain types of changes
      to the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

   /* public long addUser(User user) //Method to add a user to the database :)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("first_name", user.getFirstName());
        values.put("last_name", user.getLastName());
        values.put("phone", user.getphoneNumber());
        values.put("role", user.getRole());

        long result = db.insert("users", null, values);
        db.close();

        return result;
    }
    /*public boolean insertUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);

        long result = db.insert("users", null, values);
        return result != -1;
    }*/

    public boolean userExists(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE email=? AND password=?",
                new String[]{email, password}
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();

        return exists;
    }
}
