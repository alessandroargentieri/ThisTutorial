package com.example.alessandro.tutorial1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Set;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "MyBroadcastReceiver";

    public MyBroadcastReceiver() {
        //COSTRUTTORE VUOTO
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i(TAG, "new Intent Received ");
        Log.i(TAG, "Intent Action:" + intent.getAction());

        String messaggio="";

        Set<String> categories = intent.getCategories();
        if (categories != null) {
            for (String category : intent.getCategories()){
                Log.i(TAG, "new Intent Category:" + category);
                messaggio = messaggio + "new Intent Category:" + category;
            }
        }

        if (intent.getData() != null){
            Log.i(TAG, "Intent Data:" + intent.getData().toString());
            messaggio = messaggio + ", Intent Data:" + intent.getData().toString();
        }

        Bundle extras = intent.getExtras();
        if (extras != null) {
            for (String key : extras.keySet()) {
                Log.i(TAG, "Intent Extra key=" + key + ":" + extras.get(key));
                messaggio = messaggio + ", Intent Extra key=" + key + ":" + extras.get(key);
            }
            Intent intent_show = new Intent(context, DisplayMessageActivity.class);
            Bundle b = new Bundle();
            b.putString("parametro", messaggio); //Your id
            intent_show.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent_show.putExtras(b); //Put your id to your next Intent
            context.startActivity(intent_show);


        }

    }
}