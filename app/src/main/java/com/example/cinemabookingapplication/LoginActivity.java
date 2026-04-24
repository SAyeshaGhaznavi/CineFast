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
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.content.SharedPreferences;
import android.widget.CheckBox;
public class LoginActivity extends AppCompatActivity {

    TextInputEditText etName, etPassword;
    Button btnLogin;
    TextView tvForgottenPassword, tvSignUp;
    ImageView ivBack;
    CheckBox rememberMe;
    SharedPreferences preferences;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        boolean isRemembered = preferences.getBoolean("isLoggedIn", false);

        if(user != null && isRemembered)
        {
            startActivity(new Intent(LoginActivity.this, Home1Activity.class));
            finish();
        }

        tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            finish();
        });

        ivBack.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        });

        btnLogin.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if(TextUtils.isEmpty(name))
            {
                etName.setError("Enter name");
                return;
            }
            if(TextUtils.isEmpty(password))
            {
                etPassword.setError("Enter password");
                return;
            }
            if(password.length() < 8)
            {
                etPassword.setError("Enter minimum 8 digits password");
                return;
            }

            auth.signInWithEmailAndPassword(name,password)
                    .addOnSuccessListener(authResult -> {

                        if(rememberMe.isChecked())
                        {
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.apply();
                        }

                        startActivity(new Intent(LoginActivity.this, Home1Activity.class));
                        finish();
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    @SuppressLint("MissingInflatedId")
    private void init()
    {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgottenPassword = findViewById(R.id.tvForgottenPassword);
        tvSignUp = findViewById(R.id.tvSignUp);
        ivBack = findViewById(R.id.btnBack);
        rememberMe = findViewById(R.id.rememberMe);
        preferences = getSharedPreferences("cinefast_session_pref_v3", MODE_PRIVATE);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }
}