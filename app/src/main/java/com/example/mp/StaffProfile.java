package com.example.mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mp.databinding.ActivityStaffProfileBinding;
import com.example.mp.module.Staff;

public class StaffProfile extends AppCompatActivity {

    ActivityStaffProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityStaffProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.buttonLoginStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name= checkField(binding.mName);
                String dept= checkField(binding.mDept);
                String email= checkField(binding.mEmail);
                String pass= checkField(binding.mPass);
                Staff staff= new Staff(name, email, dept, pass);
                Login login= new Login(StaffProfile.this);
                login.signInStaff(staff);
                finish();
            }
        });

        binding.textViewSwitchStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(StaffProfile.this, MemberProfile.class);
                startActivity(i);
            }
        });
    }

    private String checkField(EditText editText) {
        if (!editText.getText().toString().isEmpty()) {
            return editText.getText().toString();
        } else {
            editText.setError("Empty Field");
            return null;
        }
    }
}