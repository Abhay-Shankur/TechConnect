package com.example.mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mp.databinding.ActivityMemberProfileBinding;
import com.example.mp.module.Member;
import com.google.firebase.auth.FirebaseAuth;

public class MemberProfile extends AppCompatActivity {

    ActivityMemberProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMemberProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.buttonLoginMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= checkField(binding.editTextTextEmailAddress);
                String pass= checkField(binding.editTextTextPassword);
                String name= checkField(binding.editTextTextPersonName);
                String phone= checkField(binding.editTextPhone);

                Member member= new Member(name, email, phone, pass);
                Login login= new Login(MemberProfile.this);
                login.signInMember(member);
                finish();
            }
        });

        binding.textViewSwitchMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MemberProfile.this, StaffProfile.class);
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