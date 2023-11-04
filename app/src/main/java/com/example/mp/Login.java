package com.example.mp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mp.module.Member;
import com.example.mp.module.Staff;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Login {
    private Context context;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    public Login(Context context) {
        this.context = context;
        auth= FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
    }

    public void signInMember(Member member) {
        auth.signInWithEmailAndPassword(member.getEmail(), member.getPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Log In Successfull Member", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, MainActivity.class));
                } else {
//                    Toast.makeText(context, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    if (task.getException().getMessage().toString().contains("There is no user record corresponding to this identifier")) {
                        createMember(member);
                    }
                }
            }
        });
    }

    private void createMember(Member member) {
        auth.createUserWithEmailAndPassword(member.getEmail(), member.getPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "New Member Created", Toast.LENGTH_SHORT).show();
                    database.getReference().child("Member").child(task.getResult().getUser().getUid()).setValue(member);
                } else {
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signInStaff(Staff staff) {
        auth.signInWithEmailAndPassword(staff.getEmail(), staff.getPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Log In Successfull Staff", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, MainActivity.class));
//                    return true;
                } else {
//                    Toast.makeText(context, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    if (task.getException().getMessage().toString().contains("There is no user record corresponding to this identifier")) {
                        createStaff(staff);
                    }
                }
            }
        });
    }

    private void createStaff(Staff staff) {
        auth.createUserWithEmailAndPassword(staff.getEmail(), staff.getPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "New Staff Created", Toast.LENGTH_SHORT).show();
                    database.getReference().child("Staff").child(task.getResult().getUser().getUid()).setValue(staff);
                } else {
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
