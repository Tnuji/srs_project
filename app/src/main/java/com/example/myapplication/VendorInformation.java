package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.users.User;
import com.example.myapplication.users.Vendor;

import java.util.HashMap;
import java.util.Map;


public class VendorInformation extends AppCompatActivity {


    Vendor vendor;
    TextView business_name;
    TextView phone_number;
    TextView email;
    TextView address;
    TextView description;
    //TextView average_rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vendor_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.vendor_information), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //this is the vendor that was selected
        try {
            vendor = getIntent().getParcelableExtra("vendor");
            User customer = getIntent().getParcelableExtra("customer");
            String service = getIntent().getStringExtra("service");
            // setting all the fields to the views in the xml
            business_name = findViewById(R.id.vendor_info_name);
            phone_number = findViewById(R.id.vendor_info_phone);
            email = findViewById(R.id.vendor_info_email);
            address = findViewById(R.id.vendor_info_address);
            description = findViewById(R.id.vendor_info_description); //average_rating = findViewById(R.id.vendor_info_name);

            //setting all the card info from the vendor;
            business_name.setText(vendor.getBusiness_name());
            phone_number.setText(vendor.getphoneNumber());
            email.setText(vendor.getEmail());
            address.setText(vendor.getAddress());
            description.setText(vendor.getDescription());


            LinearLayout servicesContainer = findViewById(R.id.servicesContainer);
            HashMap<String, Double> services = vendor.getServices();

            for (Map.Entry<String, Double> entry : services.entrySet()) {
                String serviceName = entry.getKey();
                Double price = entry.getValue();

                Button serviceButton = new Button(this);

                serviceButton.setText(serviceName + "\n$" + price);

                serviceButton.setAllCaps(false);           // keep the text as is
                serviceButton.setTextSize(16);             // text size
                serviceButton.setPadding(16, 16, 16, 16); // padding
                serviceButton.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, // width: fill parent
                        LinearLayout.LayoutParams.WRAP_CONTENT  // height: wrap content
                ));


                serviceButton.setOnClickListener(v -> {
                    //Toast.makeText(this, serviceName + " clicked!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VendorInformation.this, Payment.class);
                    intent.putExtra("customer", customer);
                    intent.putExtra("vendor", vendor);
                    intent.putExtra("service", service);// pass whole object
                    startActivity(intent);
                });

                // Add the button to the container
                servicesContainer.addView(serviceButton);
            }
        } catch (Exception e) {

        }
    }
}