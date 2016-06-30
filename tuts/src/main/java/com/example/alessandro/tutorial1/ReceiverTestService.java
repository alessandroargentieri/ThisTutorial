package com.example.alessandro.tutorial1;

import android.app.IntentService;
import android.content.Intent;

/*Questo intent service viene avviato dal Bottone BroadCastIntent e ha lo scopo di avviare un BroadCastIntent per
* l'evento con le caratteristiche stabilite nella classe Constants.
* Questo evento viene recepito e ascoltato dalla Classe MyBroadCastReceiver che è istanziata e registrata in MainActivity*/

public class ReceiverTestService extends IntentService {

    public ReceiverTestService() {
        super("ReceiverTestService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try{
            Thread.sleep(10000);
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        /*Per capire vai a vedere la classe Constants*/
        //Only for test, send a broadcast intent
        Intent mIntent= new Intent();
        mIntent.setAction(Constants.INTENT_ACTION);
        mIntent.putExtra(Constants.INTENT_EXTRA, "Additional info");
        sendBroadcast(mIntent);
    }
}