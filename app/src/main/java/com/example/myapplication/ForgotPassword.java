package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class ForgotPassword extends AppCompatActivity {

    private TextInputEditText emailEditText;
    private Spinner questionSpinner;
    private TextInputEditText answerEditText;
    private Button verifyAnswerButton;

    private TextInputLayout newPasswordLayout;
    private TextInputEditText newPasswordEditText;
    private Button resetPasswordButton;

    private DBhelper dbHelper;
    private int currentUserId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.forgot_password), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHelper = new DBhelper(this);

        emailEditText = findViewById(R.id.email_edit_text);
        questionSpinner = findViewById(R.id.question_spinner);
        answerEditText = findViewById(R.id.answer_edit_text);
        verifyAnswerButton = findViewById(R.id.verify_answer_button);

        newPasswordLayout = findViewById(R.id.new_password_layout);
        newPasswordEditText = findViewById(R.id.new_password_edit_text);
        resetPasswordButton = findViewById(R.id.reset_password_button);

        String[] questions = {
                "Favorite Color",
                "City You Were Born In"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                questions
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionSpinner.setAdapter(adapter);

        verifyAnswerButton.setOnClickListener(v -> verifyAnswer());

        resetPasswordButton.setOnClickListener(v -> {
            resetPassword();
            Intent intent = new Intent(this, MainActivity.class);
            Toast.makeText(this, "Successfully changed your password!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
    }

    private void verifyAnswer() {
        String email = emailEditText.getText().toString().trim().toLowerCase();
        String enteredAnswer = answerEditText.getText().toString().trim().toLowerCase();

        if (email.isEmpty() || enteredAnswer.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        currentUserId = dbHelper.getUserIdByEmail(email);

        if (currentUserId == -1) {
            Toast.makeText(this, "No account found with that email", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> recoveryInfo = dbHelper.getForgotPasswordInfo(currentUserId);

        if (recoveryInfo.isEmpty()) {
            Toast.makeText(this, "No recovery information found", Toast.LENGTH_SHORT).show();
            return;
        }

        String selectedQuestion = questionSpinner.getSelectedItem().toString();

        boolean correct = false;

        if (selectedQuestion.equals("Favorite Color")) {
            String storedColor = recoveryInfo.get("color");
            correct = enteredAnswer.equals(storedColor);
        }
        else if (selectedQuestion.equals("City You Were Born In")) {
            String storedCity = recoveryInfo.get("city");
            correct = enteredAnswer.equals(storedCity);
        }

        if (correct) {
            newPasswordLayout.setVisibility(View.VISIBLE);
            resetPasswordButton.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Answer verified!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Incorrect answer!", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetPassword() {
        String newPassword = newPasswordEditText.getText().toString().trim();

        if (newPassword.isEmpty()) {
            Toast.makeText(this, "Enter a new password", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean updated = dbHelper.updatePassword(currentUserId, newPassword);

        if (updated) {
            Toast.makeText(this, "Password reset successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Toast.makeText(this, "Failed to reset password", Toast.LENGTH_SHORT).show();
        }
    }
}