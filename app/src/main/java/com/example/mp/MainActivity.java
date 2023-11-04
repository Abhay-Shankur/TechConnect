package com.example.mp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mp.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    RecyclerView rv;
    FloatingActionButton fab;
    EditText iss, per, dept;
//    Button ibtn;
    ArrayList<Issue> issuse = new ArrayList<>();
    IssueAdapter adapter;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sd = new SimpleDateFormat("dd/MM/YY");
    String date = sd.format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        getSupportActionBar().hide();


        rv = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(view -> {
            if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_issue);
                iss = dialog.findViewById(R.id.issue);
                per = dialog.findViewById(R.id.person);
                dept = dialog.findViewById(R.id.dept);
                dialog.findViewById(R.id.button).setOnClickListener(view1 -> {
                    String issue_name = iss.getText().toString();
                    String raised_by = per.getText().toString();
                    String raised_at = dept.getText().toString();
                    if (!issue_name.isEmpty() && !raised_by.isEmpty()) {
//                        issuse.add(new Issue(raised_at, issue_name, raised_by, date, R.drawable.img_2));
                        String id= String.valueOf((int) (Math.random()*1000000));
                        Issue i= new Issue(id, raised_at, issue_name, raised_by, date, R.drawable.img_2);
                        FirebaseDatabase.getInstance().getReference().child("Issues").child(id).setValue(i);
                        onStart();
                    } else {
                        Toast.makeText(this, "Enter Complete Issue", Toast.LENGTH_SHORT).show();
                    }

//                adapter.notifyItemChanged(issuse.size() - 1);
//                rv.scrollToPosition(issuse.size() - 1);

                    dialog.dismiss();
                });
                dialog.show();
            } else {
                Toast.makeText(this, "Please Login First !", Toast.LENGTH_SHORT).show();
            }

        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IssueAdapter(this, issuse);
        rv.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (FirebaseAuth.getInstance().getCurrentUser()==null) {
            getMenuInflater().inflate(R.menu.option_menu2, menu);
        } else {
            getMenuInflater().inflate(R.menu.option_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
//            case R.id.profile:
//                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                onRestart();
                return true;
            case R.id.item_login:
                Intent i = new Intent(this,StaffProfile.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        issuse.clear();
        fetch();
    }

    private void fetch() {
        FirebaseDatabase.getInstance().getReference().child("Issues").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data: snapshot.getChildren()) {
                        Issue i=data.getValue(Issue.class);
                        issuse.add(i);

                        adapter.notifyItemChanged(issuse.size() - 1);
                        rv.scrollToPosition(issuse.size() - 1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        adapter.notifyItemChanged(issuse.size() - 1);
//        rv.scrollToPosition(issuse.size() - 1);
    }
}