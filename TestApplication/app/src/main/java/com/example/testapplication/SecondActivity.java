package com.example.testapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    Button btn_go_back;
    Button btn_go_for;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btn_go_back=findViewById(R.id.btn_go_back);
        btn_go_for=findViewById(R.id.btn_go_for);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Second Activity Here");

        tv=findViewById(R.id.tv);

        Intent intent=getIntent();
        String str=intent.getStringExtra("str");
        Log.d("intent: ", String.valueOf(intent));

        tv.setText("you got this string : "+str);

        btn_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_go_for.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });
    }
}
