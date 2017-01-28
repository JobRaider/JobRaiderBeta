package com.example.edu.jobraiderbeta;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class RegisterLog extends Activity {
	EditText userName,ape, hora, est;
	Button register;
	ProgressBar progressBar;
	String ip = "192.168.137.1:8080";


	public void InsertLog( String s1, String s2){
		new ExecuteTask().execute(s1,s2);

	}
	    
	class ExecuteTask extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			PostData(params);
			return null;
		}


	}

	public void PostData(String[] valuse) {
		try {
			HttpClient httpClient=new DefaultHttpClient();
			//HttpPost httpPost=new HttpPost("http://" + ip + "/JobRaide-Servlet/httpPostServlet");
			HttpPost httpPost=new HttpPost("http://" + ip + "/JobRaide-Servlet/httpPostServletLog");
			List<NameValuePair> list=new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("name", valuse[0]));

			list.add(new BasicNameValuePair("est",valuse[1]));
			httpPost.setEntity(new UrlEncodedFormEntity(list));
			httpClient.execute(httpPost);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}


}