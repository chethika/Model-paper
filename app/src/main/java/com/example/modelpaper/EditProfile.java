package com.example.modelpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.modelpaper.Database.DBHelper;

import java.util.List;

public class EditProfile extends AppCompatActivity {
    EditText userName,DOfBirth,password;
    Button edit,delete,search;
    RadioButton male,female;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        userName=findViewById(R.id.etname);
        DOfBirth=findViewById(R.id.etbirth);
        password=findViewById(R.id.etPass);
        edit=findViewById(R.id.btnedit);
        delete=findViewById(R.id.btnDelete);
        search =findViewById(R.id.btnSearch);
        male=findViewById(R.id.rbMale);
        female=findViewById(R.id.rbFemale);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DBHelper dbHelper = new DBHelper(getApplicationContext());
                    List<String> user = dbHelper.readAllInfo(userName.getText().toString(),getApplicationContext());

                    //Toast.makeText(EditProfile.this, user.toString(), Toast.LENGTH_SHORT).show();

                if(user.isEmpty()){
                    Toast.makeText(EditProfile.this, "no user", Toast.LENGTH_SHORT).show();
                    userName.setText(null);
                }else{
                    Toast.makeText(EditProfile.this, "user found: User" + user.get(0), Toast.LENGTH_SHORT).show();
                    userName.setText(user.get(0));
                    DOfBirth.setText(user.get(1));
                    password.setText(user.get(2));
                    if(user.get(3).equals("Male")){
                        male.setChecked(true);
                    }else{
                        female.setChecked(true);
                    }
                }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(male.isChecked()){
                    gender ="Male";
                }else{
                    gender ="Female";
                }
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                boolean status = dbHelper.updateInfo(userName.getText().toString(),DOfBirth.getText().toString(),password.getText().toString(),gender);
                if(status){
                    Toast.makeText(EditProfile.this, "updated successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(EditProfile.this, "user not update", Toast.LENGTH_SHORT).show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                dbHelper.deleteInfo(userName.getText().toString());
                Toast.makeText(EditProfile.this, "user deleted", Toast.LENGTH_SHORT).show();

                userName.setText(null);
                DOfBirth.setText(null);
                password.setText(null);
                male.setChecked(false);
                female.setChecked(false);
            }
        });
    }
}