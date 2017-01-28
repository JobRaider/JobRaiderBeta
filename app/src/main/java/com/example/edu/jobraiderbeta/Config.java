package com.example.edu.jobraiderbeta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by edu on 19/01/2017.
 */

public class Config extends Activity {
    EditText ipstring;
    Button ipconfig;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);
        ipstring = (EditText) findViewById(R.id.ipText);
        ipconfig = (Button) findViewById(R.id.ipbutton);


        ipconfig.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String s1 = ipstring.getText().toString();

            }
        });

    }






}

