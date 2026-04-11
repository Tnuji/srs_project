package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.users.User;

public class Homepage extends AppCompatActivity {

    Button appliances; Button electrical;
    Button plumbing; Button homeCleaning;
    Button tutoring; Button packaging_and_moving;
    Button computerRepair; Button homeRepair;
    Button pestControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.user_homepage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        appliances = findViewById(R.id.appliances_button); electrical = findViewById(R.id.electrical_button);
        plumbing = findViewById(R.id.plumbing_button); homeCleaning = findViewById(R.id.homeCleaning_button);
        tutoring = findViewById(R.id.tutoring_button); packaging_and_moving = findViewById(R.id.packaging_button);
        computerRepair = findViewById(R.id.computerRepair_button); homeRepair = findViewById(R.id.homeRepair_button);
        pestControl = findViewById(R.id.pestControl_button);
        User customer = getIntent().getParcelableExtra("user");
        goToVendorList(appliances,customer); goToVendorList(electrical,customer);
        goToVendorList(plumbing,customer); goToVendorList(homeCleaning,customer);
        goToVendorList(tutoring,customer); goToVendorList(packaging_and_moving,customer);
        goToVendorList(computerRepair,customer);


    }

    public void goToVendorList(Button button, User customer)
    {
        button.setOnClickListener(v -> {
            Intent intent = new Intent(Homepage.this, ListVendors.class);
            intent.putExtra("service", button.getText().toString());
            intent.putExtra("customer", customer);
            startActivity(intent);
        });
    }
}