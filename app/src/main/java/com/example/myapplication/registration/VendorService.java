package com.example.myapplication.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.DBhelper;
import com.example.myapplication.Homepage;
import com.example.myapplication.R;
import com.example.myapplication.users.User;
import com.example.myapplication.users.Vendor;

import java.util.HashMap;
import java.util.Map;

public class VendorService extends AppCompatActivity {

    Switch appliances_switch;
    EditText appliances_price;
    Switch electrical_switch;
    EditText electrical_price;
    Switch plumbing_switch;
    EditText plumbing_price;
    Switch homeCleaning_switch;
    EditText homeCleaning_price;
    Switch tutoring_switch;
    EditText tutoring_price;
    Switch packaging_switch;
    EditText packaging_price;
    Switch computerRepair_switch;
    EditText computerRepair_price;
    Switch homeRepair_switch;
    EditText homeRepair_price;
    Switch pestControl_switch;
    EditText pestControl_price;

    Button register;
    HashMap<String, Double> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vendor_service);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vendor_service), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        User user = getIntent().getParcelableExtra("user");

        //these are the variables that control the switches       // These are the variables that control the price texts
        appliances_switch = findViewById(R.id.appliances_switch);
        appliances_price = findViewById(R.id.appliances_price);
        electrical_switch = findViewById(R.id.electrical_switch);
        electrical_price = findViewById(R.id.electrical_price);
        plumbing_switch = findViewById(R.id.plumbing_switch);
        plumbing_price = findViewById(R.id.plumbing_price);
        homeCleaning_switch = findViewById(R.id.homeCleaning_switch);
        homeCleaning_price = findViewById(R.id.homeCleaning_price);
        tutoring_switch = findViewById(R.id.tutoring_switch);
        tutoring_price = findViewById(R.id.tutoring_price);
        packaging_switch = findViewById(R.id.packagingAndMoving_switch);
        packaging_price = findViewById(R.id.packaging_price);
        computerRepair_switch = findViewById(R.id.computerRepair_switch);
        computerRepair_price = findViewById(R.id.computerRepair_price);
        homeRepair_switch = findViewById(R.id.homeRepair_switch);
        homeRepair_price = findViewById(R.id.homeRepair_price);
        pestControl_switch = findViewById(R.id.pestControl_switch);
        pestControl_price = findViewById(R.id.pestControl_price);

        services = new HashMap<>();
        register = findViewById(R.id.vendor_register_button);

        makeVisible(appliances_switch, appliances_price);
        makeVisible(electrical_switch,electrical_price);
        makeVisible(plumbing_switch,plumbing_price );
        makeVisible(homeCleaning_switch,homeCleaning_price );
        makeVisible(tutoring_switch,tutoring_price );
        makeVisible(packaging_switch,packaging_price );
        makeVisible(computerRepair_switch,computerRepair_price );
        makeVisible(homeRepair_switch,homeRepair_price );
        makeVisible(pestControl_switch,pestControl_price);

        register.setOnClickListener(v ->
        {
            DBhelper db = new DBhelper(this);

            addService(services, appliances_switch.getText().toString(), appliances_switch, appliances_price);
            addService(services, electrical_switch.getText().toString(), electrical_switch, electrical_price);
            addService(services, plumbing_switch.getText().toString(), plumbing_switch, plumbing_price);
            addService(services, homeCleaning_switch.getText().toString(), homeCleaning_switch, homeCleaning_price);
            addService(services, tutoring_switch.getText().toString(), tutoring_switch, tutoring_price);
            addService(services, packaging_switch.getText().toString(), packaging_switch, packaging_price);
            addService(services, computerRepair_switch.getText().toString(), computerRepair_switch, computerRepair_price);
            addService(services, homeRepair_switch.getText().toString(), homeRepair_switch, homeRepair_price);
            addService(services, pestControl_switch.getText().toString(), pestControl_switch, pestControl_price);
            Vendor newVendor = new Vendor(user.getEmail(), user.getPassword(), user.getphoneNumber(), user.getAddress(), user.getFirstName(), services);

            db.addUser(user);
            db.addVendor(newVendor);
            Intent intent = new Intent(VendorService.this, Homepage.class);
            intent.putExtra("vendor", newVendor);
            startActivity(intent);
        });
    }

    public void addService(Map<String, Double> services, String service, Switch holder, EditText price_text) {
        if (holder.isChecked()) {
            String text = price_text.getText().toString().trim();
            if (!text.isEmpty()) {
                services.put(service, Double.parseDouble(text));
            }
        }
    }

    public void makeVisible(Switch holder, EditText price_text) {
        holder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                price_text.setVisibility(View.VISIBLE);
            } else {
                price_text.setVisibility(View.INVISIBLE);
                price_text.setText("");
            }
        });

    }
}