package com.example.edu.jobraiderbeta;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class Config extends Activity {
    EditText ip;
    Button ipButton;
    ProgressBar progressBar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);
        ip=(EditText) findViewById(R.id.ipConf);;

        ipButton=(Button) findViewById(R.id.ipBut);

        progressBar=(ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);

        ipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                //String s1=userName.getText().toString();


            }
        });
    }




    }




