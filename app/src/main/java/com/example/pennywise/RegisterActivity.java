package com.example.pennywise;

import android.app.ProgressDialog;
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



public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private EditText mUsername;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button btnRegister;
    private TextView mSignIn;
    private ProgressDialog mDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        mDialog = new ProgressDialog(this);



        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        register();
    }

    private void register() {
        mUsername = findViewById(R.id.username_reg);
        mEmail = findViewById(R.id.email_reg);
        mPassword = findViewById(R.id.password_reg);
        mConfirmPassword = findViewById(R.id.confirm_password_reg);
        btnRegister = findViewById(R.id.reg_button);
        mSignIn = findViewById(R.id.signIn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String confirmPassword = mConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    mUsername.setError("Username Required!");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email Required!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password Required!");
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword)) {
                    mConfirmPassword.setError("Please Confirm Password!");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    mConfirmPassword.setError("Passwords do not match!");
                    return;
                }

                // Show the progress dialog
                mDialog.setMessage("Processing");
                mDialog.show();


            }
        });

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        });
    }


}
