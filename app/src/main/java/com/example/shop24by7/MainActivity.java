package com.example.shop24by7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth =FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SystemClock.sleep(3000);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentuser = firebaseAuth.getCurrentUser();

        if(currentuser==null){
            Intent loginIntent = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(loginIntent);
            finish();
        }else{
            Intent mainIntent = new Intent(MainActivity.this,MainActivity2.class);
            startActivity(mainIntent);
            finish();
        }
    }
}