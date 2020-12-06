package com.example.modelpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.modelpaper.Database.DBHelper;

public class Home extends AppCompatActivity {
    Button btnRegister,btnLogin;
    EditText etUserName,etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnRegister =findViewById(R.id.btnRegister);
        btnLogin=findViewById(R.id.btnLogin);
        etUserName=findViewById(R.id.editTextTextPersonName);
        etPassword=findViewById(R.id.editTextTextPersonName2);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ProfileManagement.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                if(dbHelper.loginUser(etUserName.getText().toString(),etPassword.getText().toString()))
                {
                    Toast.makeText(Home.this, "login success", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), EditProfile.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(Home.this, "invalid user", Toast.LENGTH_SHORT).show();
                    etUserName.setText(null);
                    etPassword.setText(null);
                }
            }
        });
    }
}