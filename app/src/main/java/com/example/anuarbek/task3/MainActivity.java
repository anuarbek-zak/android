package com.example.anuarbek.task3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button send;
    EditText text;
    MyService myService;
    boolean isbinded=false;
    Intent intent;
    LinearLayout linearLayout;
    ServiceConnection serviceConnection;
    LayoutInflater layoutInflater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout=(LinearLayout)findViewById(R.id.cont);
        layoutInflater=getLayoutInflater();



        text=(EditText)findViewById(R.id.text);
        send=(Button)findViewById(R.id.send);



        intent=new Intent(this,MyService.class);
        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                myService=((MyService.MyBinder)iBinder).getService();
                isbinded=true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                isbinded=false;
            }
        };
        BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                View a=layoutInflater.inflate(R.layout.left,null);
                ((TextView)a.findViewById(R.id.text)).setText(intent.getStringExtra("msg"));
                linearLayout.addView(a);



            }
        };
        IntentFilter intentFilter=new IntentFilter("intent");
        registerReceiver(broadcastReceiver,intentFilter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isbinded&&!text.getText().toString().equals("")){
                    View b=layoutInflater.inflate(R.layout.right,null);
                    ((TextView)b.findViewById(R.id.text)).setText(text.getText().toString());
                    linearLayout.addView(b);

                    myService.answer(text.getText().toString());
                    text.setText("");
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isbinded){
            unbindService(serviceConnection);
            isbinded=false;}
    }
}