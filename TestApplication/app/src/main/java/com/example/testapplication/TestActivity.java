package com.example.testapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {
    Button btn_go_back;
    Button btn_go_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        btn_go_back=findViewById(R.id.btn_go_back);
        btn_go_main=findViewById(R.id.btn_go_main);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Third Activity Here");
        actionBar.setDisplayHomeAsUpEnabled(true);

        btn_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TestActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_go_main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TestActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
