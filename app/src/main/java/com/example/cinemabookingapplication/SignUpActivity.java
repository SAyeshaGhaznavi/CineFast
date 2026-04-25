package com.example.cinemabookingapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText etName, etPassword, etCPassword;
    Button btnLogin;
    TextView tvForgottenPassword, tvSignUp;
    ImageView btnBack;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });

        btnLogin.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String cPassword = etCPassword.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                etName.setError("Enter name");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Enter password");
                return;
            }
            if (password.length() < 8) {
                etPassword.setError("Enter minimum 8 digits password");
                return;
            }
            if (!password.equals(cPassword)) {
                etPassword.setError("Password does not match");
                etCPassword.setError("Password does not match");
                return;
            }

            auth.createUserWithEmailAndPassword(name, password).
                    addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(SignUpActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, Home1Activity.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    @SuppressLint("MissingInflatedId")
    private void init()
    {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etCPassword = findViewById(R.id.etConfirmPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnBack = findViewById(R.id.btnBack);
        auth = FirebaseAuth.getInstance();
    }
}