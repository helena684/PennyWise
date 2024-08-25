package com.example.pennywise;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";

    private EditText mEmail;
    private EditText mPassword;
    private Button btnSignIn;
    private TextView mForgotPassword;
    private TextView mSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signIn();
    }

    private void signIn() {
        mEmail = findViewById(R.id.Email_login);
        mPassword = findViewById(R.id.Password_login);
        btnSignIn = findViewById(R.id.buttonSignin);
        mForgotPassword = findViewById(R.id.forgotPassword);
        mSignUp = findViewById(R.id.signUp);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email Required!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password Required!");
                    return;
                }


            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });


    }


}
