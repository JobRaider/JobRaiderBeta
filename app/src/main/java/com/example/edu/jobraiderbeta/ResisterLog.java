package com.example.edu.jobraiderbeta;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

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

public class ResisterLog extends Activity {
	EditText userName,ape, hora, est;
	Button register;
	ProgressBar progressBar;
	String ip = "192.168.1.55:8080";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resister_log);
		userName=(EditText) findViewById(R.id.nomLog);;
		ape=(EditText) findViewById(R.id.apeLog);
		est=(EditText) findViewById(R.id.estLog);
		register=(Button) findViewById(R.id.envLog);

		progressBar=(ProgressBar) findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.GONE);

		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				progressBar.setVisibility(View.VISIBLE);

				String s1=userName.getText().toString();
				String s2=ape.getText().toString();
				String s3=est.getText().toString();
				new ExecuteTask().execute(s1,s2,s3);
			}
		});
	}
	    
	class ExecuteTask extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			PostData(params);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
		progressBar.setVisibility(View.GONE);
		}
	}

	public void PostData(String[] valuse) {
		try {
			HttpClient httpClient=new DefaultHttpClient();
			//HttpPost httpPost=new HttpPost("http://" + ip + "/JobRaide-Servlet/httpPostServlet");
			HttpPost httpPost=new HttpPost("http://" + ip + "/JobRaide-Servlet/httpPostServletLog");
			List<NameValuePair> list=new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("name", valuse[0]));
			list.add(new BasicNameValuePair("ape",valuse[1]));
			list.add(new BasicNameValuePair("est",valuse[2]));
			httpPost.setEntity(new UrlEncodedFormEntity(list));
			httpClient.execute(httpPost);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	public String readResponse(HttpResponse res) {
		InputStream is=null;
		String return_text="";
		try {
			is=res.getEntity().getContent();
			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
			String line="";
			StringBuffer sb=new StringBuffer();
			while ((line=bufferedReader.readLine())!=null){
				sb.append(line);
			}
			return_text=sb.toString();
		} catch (Exception e) {}
		return return_text;
	}
}