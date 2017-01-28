package com.example.edu.jobraiderbeta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class WifiScan extends AppCompatActivity {
    TextView wifiStatus, jobStatus;
    WifiManager mainWifi;
    WifiReceiver receiverWifi;
    List<ScanResult> wifiList;
    String wifiName = "JobRaider";
    StringBuilder sb = new StringBuilder();
    String employeeName  = "";
    RegisterLog register= new RegisterLog();
    String work = "trabajando";
    Button stop;
    int ausente = 0;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_con);
        Bundle bundle = getIntent().getExtras();
        employeeName = (bundle.getString("name"));
        wifiStatus = (TextView) findViewById(R.id.wifiStatus);
        jobStatus = (TextView) findViewById(R.id.jobStatus);
        stop = (Button) findViewById(R.id.Stop);
        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        receiverWifi = new WifiReceiver();
        mainWifi.startScan();
        wifiStatus.setText("Buscando redes...\n");

        whenResumed();
        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                register.InsertLog(employeeName, "Fin");


                Intent  intent=new Intent(WifiScan.this,  MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                moveTaskToBack(true);
                return true;
        }return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0x12345) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
        }
    }

    protected void whenResumed() {//Esto solía ser un onResume();
        registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        Timer();
    }

    private void Timer(){
        //Thread este es el timer que ejecutará periódicamente la prueba de conexión
        //Cada 5 segundos hay un tick y el timer dura 10 segundos
        CountDownTimer contador = new CountDownTimer(10000, 5000) {
            //Esta función ejecuta un comando cada tick del timer
            public void onTick(long millisUntilFinished) {
                //Toast.makeText(getApplicationContext(),"Thread Ejecutado", Toast.LENGTH_LONG).show();
            }
            //Esta función ejecuta un comando al terminar el timer, en nuestro caso vuelve a lanzar la prueba de conexión
            public void onFinish() {

            }
        }.start();
    }

    class WifiReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
            sb = new StringBuilder();
            wifiList = mainWifi.getScanResults();
           for(int i = 0; i < wifiList.size(); i++){
               sb.append("Puntos de acceso Wi-Fi:\n");
                sb.append(new Integer(i+1).toString() + ". ");
                sb.append((wifiList.get(i).SSID).toString()+ "  -  Potencia: " + (wifiList.get(i).level));
                sb.append("\n");
                if( (wifiList.get(i).SSID).toString().equals(wifiName)) {
                    Toast.makeText(getApplicationContext(), "La red " + (wifiList.get(i).SSID) + " está en tu rango", Toast.LENGTH_LONG).show();
                    jobStatus.setText("Bienvenido al trabajo " + employeeName + ", pasa un buen día.");

                   register.InsertLog(employeeName, "trabajando");
                    break;


                }else {
                    register.InsertLog(employeeName, "ausente");
                    ausente++;
                    jobStatus.setText("No estas en el Rango de la red de la empresa");
                    Toast.makeText(getApplicationContext(), "La red " + (wifiList.get(i).SSID) + " no está en tu rango", Toast.LENGTH_LONG).show();
                       if(ausente==2){
                        register.InsertLog(employeeName, "Fin");

                           Intent  intent2=new Intent(WifiScan.this,  MainActivity.class);
                           startActivityForResult(intent2, 0);
                                     }
                    break;



                }
            }wifiStatus.setText(sb);
        }
    }
}
