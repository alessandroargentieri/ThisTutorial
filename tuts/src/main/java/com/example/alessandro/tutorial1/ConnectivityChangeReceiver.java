package com.example.alessandro.tutorial1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/*questa classe è un broadcast receiver registrato nel manifest che richiama DisplayMessageActivity quando il sistema invia un
* BroadcastIntent relativo alla modifica della connettività:
* http://www.anddev.it/index.php?topic=10114.0*/

public class ConnectivityChangeReceiver extends BroadcastReceiver {

    private final static String TAG="ConnectivityChangeReceiver";

    public ConnectivityChangeReceiver(){
        Log.i("BroadcastReceiver","INIT Receiver");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("BroadcastReceiver","OnReceiver");

        Log.v(TAG, "action: " + intent.getAction());
        Log.v(TAG, "component: " + intent.getComponent());
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String messaggio = "";
            for (String key : extras.keySet()) {
                Log.v(TAG, "key [" + key + "]: " + extras.get(key));
                messaggio = messaggio + "key [" + key + "]: " + extras.get(key);
            }
            Intent intent_show = new Intent(context, DisplayMessageActivity.class);
            Bundle b = new Bundle();
            b.putString("parametro", messaggio); //Your id
            intent_show.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent_show.putExtras(b); //Put your id to your next Intent
            context.startActivity(intent_show);
        } else {
            Log.v(TAG, "no extras");
        }
    }

}