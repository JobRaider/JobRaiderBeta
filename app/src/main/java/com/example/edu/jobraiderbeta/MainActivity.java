package com.example.edu.jobraiderbeta;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    EditText password,userName;
    Button login,resister, config, logReg;
    ProgressBar progressBar;
    TextView user;
    String ip = "192.168.1.55:8080";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        password=(EditText) findViewById(R.id.pas);
        userName=(EditText) findViewById(R.id.nombre);
        user=(TextView) findViewById(R.id.user);
        login=(Button) findViewById(R.id.register);
        resister=(Button) findViewById(R.id.button2);
        config=(Button) findViewById(R.id.button3);
        logReg=(Button) findViewById(R.id.logReg);


        //progess_msz.setVisibility(View.GONE);
        progressBar=(ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);


        resister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent  intent=new Intent(MainActivity.this, ResisterUser.class);
                startActivityForResult(intent, 0);
            }
        });
        logReg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent  intent=new Intent(MainActivity.this, ResisterLog.class);
                startActivityForResult(intent, 0);
            }
        });
        config.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent  intent=new Intent(MainActivity.this, Config.class);
                startActivityForResult(intent, 0);
            }
        });
        login.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String s1=userName.getText().toString();
                String s2=password.getText().toString();
                new ExecuteTask().execute(s1,s2);


            }
        });


    }

    class ExecuteTask extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {

            String res=PostData(params);

            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            //progess_msz.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), result, 3000).show();
        }

    }

    public String PostData(String[] valuse) {
        String s="";
        try
        {
            HttpClient httpClient=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost("http://" + ip + "/JobRaide-Servlet/Login");

            List<NameValuePair> list=new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("name", valuse[0]));
            list.add(new BasicNameValuePair("pass",valuse[1]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse=	httpClient.execute(httpPost);

            HttpEntity httpEntity=httpResponse.getEntity();
            s= readResponse(httpResponse);

        }
        catch(Exception exception) 	{}
        return s;


    }
    public String readResponse(HttpResponse res) {
        InputStream is=null;
        String return_text="";
        try {
            is=res.getEntity().getContent();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
            String line="";
            StringBuffer sb=new StringBuffer();
            while ((line=bufferedReader.readLine())!=null)
            {
                sb.append(line);
            }
            return_text=sb.toString();
          
        } catch (Exception e)
        {

        }
        user.setText(return_text);
        return return_text;

    }

}
