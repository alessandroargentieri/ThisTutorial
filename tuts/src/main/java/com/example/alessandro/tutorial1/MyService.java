package com.example.alessandro.tutorial1;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


//QUESTO EVENTO CONTINUA A FUNZIONARE SE HAI L'APP APERTA MA MESSA IN ICONA. ALTRIMENTI SI BLOCCA
public class MyService extends IntentService {
    public MyService() {
        super("LogService");
    }

    @Override
    protected void onHandleIntent(Intent i){   //SAREBBE IL BACKGROUND ASINCRONO
        int n=0;
        while(true){
            Toast.makeText(getApplicationContext(),"Evento n."+n++, Toast.LENGTH_LONG).show();
            Log.i("PROVA SERVICE", "Evento n."+n++);
            // Build notification
            // Actions are just fake
            Notification noti = new Notification.Builder(this)
                    .setContentTitle("Evento n."+n++)
                    .setContentText("EVENTO ASINCRONO INTENT SERVICE").setSmallIcon(R.drawable.ninja)
                    .setContentIntent(null)
                    .build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // hide the notification after its selected
            noti.flags |= Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(0, noti);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {

            }
        }
    }

    @Override
    public void onDestroy() {
        Log.i("PROVA SERVICE", "Distruzione Service");
        Toast.makeText(this,"DISTRUZIONE SERVICE", Toast.LENGTH_LONG).show();
    }
    /*
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Visualizzo un Toast su schermo per avvisare l'utente dell'avvenuta
        // inizializzazione del servizio.
        Toast.makeText(this, " Service Started", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }
    */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void startForeground() {
        startForeground(17, null); // Because it can't be zero...
    }
}