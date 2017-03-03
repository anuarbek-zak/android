package com.example.anuarbek.task3;

/**
 * Created by Anuarbek on 03.03.2017.
 */

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {
    boolean canAnswer=false;

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }



    public class MyBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        }
    }
    public void answer( String message){
        String[] phrases = new String[]{"My name is Anuarbek","How are you?","What are you doing?"};

        switch (message) {
            case "Hello":

                Intent result = new Intent("intent");
                result.putExtra("msg", phrases[(int) Math.floor(Math.random() * 3)]);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendBroadcast(result);

                NotificationCompat.Builder notifBuilder =
                        new NotificationCompat.Builder(MyService.this)
                                .setSmallIcon(android.R.drawable.stat_notify_chat)
                                .setContentTitle("Answer")
                                .setContentText(message)
                                .setAutoCancel(true);
                NotificationManager manager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(0, notifBuilder.build());
                canAnswer=true;
                break;
            case "Bye":
                Intent result1 = new Intent("intent");
                result1.putExtra("msg", "Bye");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendBroadcast(result1);

                NotificationCompat.Builder notifBuilder1 =
                        new NotificationCompat.Builder(MyService.this)
                                .setSmallIcon(android.R.drawable.stat_notify_chat)
                                .setContentTitle("Answer")
                                .setContentText(message)
                                .setAutoCancel(true);
                NotificationManager manager1 =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager1.notify(0, notifBuilder1.build());
                canAnswer=false;
                break;
            default:
                if (canAnswer){
                    Intent result3 = new Intent("intent");
                    result3.putExtra("msg", "Random");
                    sendBroadcast(result3);

                    NotificationCompat.Builder notifBuilder3 =
                            new NotificationCompat.Builder(MyService.this)
                                    .setSmallIcon(android.R.drawable.stat_notify_chat)
                                    .setContentTitle("Answer")
                                    .setContentText(message)
                                    .setAutoCancel(true);
                    NotificationManager manager3 =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager3.notify(0, notifBuilder3.build());
                    break;

             }
        }
    }
}
