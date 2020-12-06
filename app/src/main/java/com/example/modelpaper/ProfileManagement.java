package com.example.modelpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.modelpaper.Database.DBHelper;

public class ProfileManagement extends AppCompatActivity {
    EditText userName,DOfBirth,password;
    Button  add,updateProfile;
    RadioButton male,female;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);
        userName=findViewById(R.id.etname);
        DOfBirth=findViewById(R.id.etbirth);
        password=findViewById(R.id.etPass);
        add=findViewById(R.id.btnAdd);
        updateProfile=findViewById(R.id.btnedit);
        male =findViewById(R.id.rbMale);
        female=findViewById(R.id.rbFemale);

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),EditProfile.class);
                startActivity(i);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(male.isChecked()){
                    gender="Male";
                }else{
                    gender="Female";
                }
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                long newId =dbHelper.addInfo(userName.getText().toString(),DOfBirth.getText().toString(),password.getText().toString(),gender);
                Toast.makeText(ProfileManagement.this, "user added. user ID:"+ newId, Toast.LENGTH_SHORT).show();

                Intent i= new Intent(getApplicationContext(),EditProfile.class);
                startActivity(i);
                userName.setText(null);
                DOfBirth.setText(null);
                password.setText(null);
                male.setChecked(true);
                female.setChecked(false);

            }
        });
    }
}