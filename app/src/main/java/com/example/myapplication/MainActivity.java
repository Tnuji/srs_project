package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.registration.VendorOrCustomer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.customer_registration), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DBhelper db = new DBhelper(this);
        /*if (!db.userExists("testuser@gmail.com", "1234")) {
            db.insertUser("testuser@gmail.com", "1234");
        }*/


        EditText emailEditText = findViewById(R.id.login_email);
        EditText passwordEditText = findViewById(R.id.login_password);
        Button loginButton = findViewById(R.id.login_button);
        TextView registrationText = findViewById(R.id.new_user_text_button);

        loginButton.setOnClickListener(v ->
        {

            String username = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (db.userExists(username,password)) {
                // ✅ Login success → go to next activity

                Intent intent = new Intent(MainActivity.this, UserHomePage.class);
                intent.putExtra("username", username); // pass username
                startActivity(intent);

            } else {
                // ❌ Login failed
                Toast.makeText(MainActivity.this, "Invalid login", Toast.LENGTH_SHORT).show();
            }

        });

        registrationText.setOnClickListener(v ->
        {
            Intent intent = new Intent(MainActivity.this, VendorOrCustomer.class);
            startActivity(intent);
        });

    }
    void disable(View v){
        v.setEnabled(false);
    }
}