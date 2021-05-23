package com.example.groupassigment1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.groupassigment1.R;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void goBtnAction(View view) {

        Intent intent = new Intent(HomeActivity.this, CategoryListActivity.class);
        startActivity(intent);
        finish();
    }
}