package com.example.alessandro.tutorial1;

//import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    String parametro;
    String modalita = "Activity";
    EditText messaggio;
    Button modalita_button;
    Button cambia_lan;
    Button send;
    Context con;
    ImageView mImageView;
    ImageView ninjaSprite;
    TextView statusDownload;
    //View paintView;

    private Locale myLocale;  //per cambiare lingua
    public String lingua = "it";
    static final int PICK_CONTACT_REQUEST = 1;  // The request code for PickContact
    private ShareActionProvider mShareActionProvider;   //per il menù
   // static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_REQUEST = 1888;
    private boolean isTouch = false;  //Per gestire l'evento touch
    private MyBroadcastReceiver mReceiver;

    //Elementi del custom dialog
    TextView mess;
    Button Ok;
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(DownloadService.FILEPATH);
                int resultCode = bundle.getInt(DownloadService.RESULT);
                if (resultCode == RESULT_OK) {
                    Toast.makeText(MainActivity.this,
                            "Download complete. Download URI: " + string,
                            Toast.LENGTH_LONG).show();
                    statusDownload.setText("Download done");
                } else {
                    Toast.makeText(MainActivity.this, "Download failed",
                            Toast.LENGTH_LONG).show();
                    statusDownload.setText("Download failed");
                }
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messaggio = (EditText) findViewById(R.id.edit_message);
        modalita_button = (Button) findViewById(R.id.button_mod);
        cambia_lan = (Button) findViewById(R.id.button_cambia_lan);
        send = (Button) findViewById(R.id.button_invia);
        mImageView = (ImageView) findViewById(R.id.imgView);
        ninjaSprite = (ImageView) findViewById(R.id.ninja_boy);
        statusDownload = (TextView)findViewById(R.id.status);
        //paintView = (View) findViewById(R.id.paintview);

        /*
        //GESTIRE LE RICHIESTE DI ALTRE APP CHE CONDIVIDONO CON LA NOSTRA TESTI O IMMAGINI COME DEFINITO NEL MANIFEST NELL'INTENT FILTER
        // Get the intent that started this activity
        Intent intent = getIntent();
        Uri data = intent.getData();

        // Figure out what to do based on the intent type
        if (intent.getType().indexOf("image/") != -1) {
            // Handle intents with image data ...
        } else if (intent.getType().equals("text/plain")) {
            // Handle intents with text ...
        }   */

       findViewById(R.id.turuzzuccuru).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int X = (int) event.getX();
                int Y = (int) event.getY();
                int eventaction = event.getAction();

                switch (eventaction) {
                    case MotionEvent.ACTION_DOWN:
                        String colore = "#" + X + "" + Y;
                        try {
                            findViewById(R.id.turuzzuccuru).setBackgroundColor(Color.parseColor(colore));
                            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ninjaSprite.getWidth(), ninjaSprite.getHeight());
                            layoutParams.setMargins(X, Y, 0, 0);
                            ninjaSprite.setLayoutParams(layoutParams);
                        }catch(Exception e){Log.d("COLORE", "COLORE INESISTENTE");}
                        Toast.makeText(MainActivity.this, "X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
                        Log.d("MOTION DOWN", "MOTION DOWN " + X + ", " + Y);
                        isTouch = true;
                        break;

                    case MotionEvent.ACTION_MOVE:
                       // Toast.makeText(MainActivity.this, "MOVE "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
                        break;

                    case MotionEvent.ACTION_UP:
                       // Toast.makeText(MainActivity.this, "ACTION_UP "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
                        Log.d("MOTION UP", "MOTION UP " + X + ", " + Y);

                        break;
                }
                return true;
            }
        });


    }  //END ON CREATE








    //onClick method del bottone di scelta della View associata a questa Activity
    public void ScegliModalita(View v) {
        CharSequence[] opzioni = {"Activity", "Dialog", "Custom Dialog"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scegli l'opzione");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(true);
        builder.setItems(opzioni, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                if (which == 0) {
                    modalita = "Activity";
                    modalita_button.setText("Activity");
                    Toast.makeText(getApplicationContext(), "selezionato Activity", Toast.LENGTH_LONG).show();
                } else if (which == 1) {
                    modalita = "Dialog";
                    modalita_button.setText("Dialog");
                    Toast.makeText(getApplicationContext(), "Selezionato Dialog", Toast.LENGTH_LONG).show();
                } else if (which == 2) {
                    modalita = "Custom Dialog";
                    modalita_button.setText("Custom Dialog");
                    Toast.makeText(getApplicationContext(), "Selezionato Custom Dialog", Toast.LENGTH_LONG).show();
                }
                dialog.cancel();
            }
        });
        builder.show();
    }

    //onClick method del bottone di invio della View associata a questa Activity
    public void SendMessage(View v) {
        switch (modalita) {
            case "Activity":
                Intent intent = new Intent(this, DisplayMessageActivity.class);
                EditText editText = (EditText) findViewById(R.id.edit_message);
                String message = editText.getText().toString();
                //     intent.putExtra(parametro, message);
                //     startActivity(intent);
                //     finish();
                //teoria: http://101apps.co.za/articles/passing-data-between-activities.html
                Bundle b = new Bundle();
                b.putString("parametro", message); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
                //finish();
                break;
            case "Dialog":
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Dialog");
                builder.setMessage(messaggio.getText());
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Chiudi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case "Custom Dialog":
                final Dialog d = new Dialog(MainActivity.this);
                d.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                d.setContentView(R.layout.custom_dialog);
                d.setCancelable(true);
                d.setTitle("Custom Dialog");
                Display display = ((WindowManager) getSystemService(getBaseContext().WINDOW_SERVICE)).getDefaultDisplay();
                int width = display.getWidth();
                int height = display.getHeight();
                d.getWindow().setLayout(width, (int) height / 2);
                d.show();
                //d.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.down_up_button);
                mess = (TextView) d.findViewById(R.id.textMessage);
                mess.setText(messaggio.getText());
                Ok = (Button) d.findViewById(R.id.buttonOK);
                Ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                break;

        }

    }


    //*********************FUNZIONI PER CAMBIARE LA LINGUA
    public void CambiaLingua(View v) {
        Configuration c = new Configuration(getResources().getConfiguration());
        switch (lingua) {
            case "it":
                lingua = "en";
                c.locale = Locale.US;
                break;
            case "en":
                lingua = "it";
                c.locale = Locale.ITALIAN;
                break;
            default:
                break;
        }

        getResources().updateConfiguration(c, getResources().getDisplayMetrics());
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }



    //*******************************************************************************************************
    //METODI ON CLICK DEI BOTTONI DELL'INTERFACCIA
    public void SaveInFile(View w) {
        SaveTxtFile("/Tutorial1","/sdcard/Tutorial1/FileDiTesto.txt", messaggio.getText().toString(),false);
        Toast.makeText(this,"File salvato",Toast.LENGTH_LONG).show();
    }

    public void ImportFromFile(View v) {
        messaggio.setText(readFileAsString("/sdcard/Tutorial1/FileDiTesto.txt"));
        Toast.makeText(this,"File importato",Toast.LENGTH_LONG).show();
    }

    public void DeleteFile(View v) {
        deleteFiles("/sdcard/Tutorial1/FileDiTesto.txt");
        Toast.makeText(this,"File eliminato",Toast.LENGTH_LONG).show();
    }

    // *************************************************************************************
    //FUNZIONE PER ELIMINARE DEI FILE SPECIFICANDO IL PERCORSO:

    public static void deleteFiles(String path) {
        File file = new File(path);
        if (file.exists()) {
            String deleteCmd = "rm -r " + path;
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(deleteCmd);
            } catch (IOException e) {
            }
        }
    }

    //*************************************************************************************

//SALVATAGGIO NEL FILE
    //Directory = "/Tutorial1", Path = "/sdcard/Tutorial1/FileDiTesto.txt", Content = <Contenuto file>, append = false
public void SaveTxtFile(String Directory, String Path, String Content, Boolean append){
    File root_text = Environment.getExternalStorageDirectory();
    try{
        File folder = new File(Environment.getExternalStorageDirectory() + Directory);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        BufferedWriter fwv = new BufferedWriter(new FileWriter(new File(Path), append));
        if (root_text.canWrite()) {
            fwv.write(Content);
            fwv.close();
        }
    }catch(Exception e){
        Log.e("ERRORE SALVATAGGIO FILE", "ERRORE: " + e.toString());
    }

}

////////////////////////////////////////////////////////////////////////////////
//FUNZIONE CHE RESTITUISCE IL CONTENUTO DI UN FILE TESUALE COME UNA STRINGA

    public static String readFileAsString(String filePath) {
        String result = "";
        File file = new File(filePath);
        if (file.exists() ) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                char current;
                while (fis.available() > 0) {
                    current = (char) fis.read();
                    result = result + String.valueOf(current);
                }
            }catch (Exception e) {
                Log.d("ERRORE", e.toString());
            }finally {
                if (fis != null)
                    try {
                        fis.close();
                    } catch (IOException ignored) {}
            }
        }
        return result;
    }

    public void Download(View v){
        Intent intent = new Intent(this, DownloadService.class);
        // add infos for the service which file to download and where to store
        intent.putExtra(DownloadService.FILENAME, "index.html");
        intent.putExtra(DownloadService.URL, "http://www.vogella.com/index.html");
        startService(intent);
        statusDownload.setText("Service started");
    }
    ////////////////////////////////////////////////////////////////

    public void PickContact(View v){
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);

                // Do something with the phone number...
                messaggio.setText(number);
            }
        }else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                mImageView.setImageBitmap(photo);
                new ImageSaver(this).
                    setFileName("myImage.png").
                    setDirectoryName("images").
                    save(photo);                                    //SALVA E LOAD IMMAGINE IN CARTELLA SEGRETA
                    createDirectoryAndSaveFile(photo, "IMGH.png"); //CREA L'IMMAGINE IN UNA CARTELLA

                Uri imageUri = Uri.parse("/sdcard/Tutorial1/Images/IMGH.png");

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("image/png");
                startActivity(Intent.createChooser(shareIntent, "CONDIVIDIMI!"));

            }

    }

    public void SendText(View w){               //CONDIVISIONE DI DATI AD ALTRE APP CHE DEVONO POSSEDERE L'INTENT FILTER PER GESTIRLE
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, messaggio.getText());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
        //riga opzionale
        startActivity(Intent.createChooser(sendIntent, "MANDA MANDA MANDA!!!!"));
    }

    public void SendImg(View w){

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        //VEDERE L'AZIONE SVOLTA IN ON_ACTIVITY_RESULT
    }

    public void LoadImg(View v){
        Bitmap bitmap = new ImageSaver(this).
                setFileName("myImage.png").
                setDirectoryName("images").
                load();
        mImageView.setImageBitmap(bitmap);
    }


    public void Notifica(View v){
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, NotificationReceiverActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject").setSmallIcon(R.drawable.ninja)
                .setContentIntent(pIntent)
                .addAction(R.drawable.ninja, "Call", pIntent)
                .addAction(R.drawable.ninja, "More", pIntent)
                .addAction(R.drawable.ninja, "And more", pIntent).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);
    }

    public void startService(View v) {
        startService(new Intent(this,MyService.class));
    }

    public void stopService(View v)  {
        stopService(new Intent(this,MyService.class));
    }

    public void SendBroadcast(View w){
        /*si collega al bottone che avvia un service in Background che dopo 10 secondi invia un BroadcastIntent
        * che sarà recepito da un BroadCastReceiver istanziato qui e registrato solo qui e non nel manifest
        * http://www.anddev.it/index.php?topic=10114.0*/
        Intent serviceIntent = new Intent(w.getContext(),ReceiverTestService.class);
        startService(serviceIntent);

    }

    public void Calendar(View v){
        Intent intent = new Intent(this, CalendarViewSampleActivity.class);
        startActivity(intent);
    }

    //////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(DownloadService.NOTIFICATION));
        mReceiver=new MyBroadcastReceiver();
        registerReceiver(mReceiver, new IntentFilter(Constants.INTENT_ACTION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        unregisterReceiver(mReceiver);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mon_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.menu_a:
                Toast.makeText(this,"Scelta a",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, PaintActivity.class);
                startActivity(intent);
                //finish();
                break;

            case R.id.menu_b:
                Toast.makeText(this,"Scelta b",Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(this, ScaleImageActivity.class);
                startActivity(intent2);
                break;

            case R.id.menu_c:
                Toast.makeText(this,"Scelta c",Toast.LENGTH_LONG).show();
                Intent intent3 = new Intent(this, AnimationActivity.class);
                startActivity(intent3);
                break;

            default:
                break;
        }
        return true;
    }

    public void FragmentChange(View v){
        Intent intent = new Intent(this, FragmentChangeActivity.class);
        startActivity(intent);
    }

    public void RusticSlideMenu(View v){

            Intent intent = new Intent(this, NavigationDrawerActivity.class);
            startActivity(intent);

    }
    /////////////
    public class ImageSaver {

        private String directoryName = "images";
        private String fileName = "image.png";
        private Context context;

        public ImageSaver(Context context) {
            this.context = context;
        }

        public ImageSaver setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public ImageSaver setDirectoryName(String directoryName) {
            this.directoryName = directoryName;
            return this;
        }

        public void save(Bitmap bitmapImage) {
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(createFile());
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @NonNull
        private File createFile() {
            File directory = context.getDir(directoryName, Context.MODE_PRIVATE);
            return new File(directory, fileName);
        }

        public Bitmap load() {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(createFile());
                return BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
/////////////////////////////
private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

    File direct = new File(Environment.getExternalStorageDirectory() + "/Tutorial1/Images");

    if (!direct.exists()) {
        File wallpaperDirectory = new File("/sdcard/Tutorial1/Images/");
        wallpaperDirectory.mkdirs();
    }

    File file = new File(new File("/sdcard/Tutorial1/Images/"), fileName);
    if (file.exists()) {
        file.delete();
    }
    try {
        FileOutputStream out = new FileOutputStream(file);
        imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
        out.flush();
        out.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
 ////////////////////
  //PER LE IMMAGINI
 public static int calculateInSampleSize(
         BitmapFactory.Options options, int reqWidth, int reqHeight) {
     // Raw height and width of image
     final int height = options.outHeight;
     final int width = options.outWidth;
     int inSampleSize = 1;

     if (height > reqHeight || width > reqWidth) {

         final int halfHeight = height / 2;
         final int halfWidth = width / 2;

         // Calculate the largest inSampleSize value that is a power of 2 and keeps both
         // height and width larger than the requested height and width.
         while ((halfHeight / inSampleSize) > reqHeight
                 && (halfWidth / inSampleSize) > reqWidth) {
             inSampleSize *= 2;
         }
     }

     return inSampleSize;
 }


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    //////////////////////
    //QUESTA VOID è IL METODO ONCLICK DEL PULSANTE OMONIMO SULL'INTERFACCIA.
    //IL CARICAMENTO AVVIENE ATTRAVERSO IL THREAD PRINCIPALE (METODO NON ESATTAMENTE INDICATO)
    public void ShowBmp(View v){
        mImageView.setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.drawable.palma, 200, 200));
    }

    private class AsyncPicasso extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        public AsyncPicasso (Context context){   //COSTRUTTORE A CUI VA PASSATO IL CONTESTO
            mContext = context;
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            // Picasso.with(this).load("http://cdn5.upsocl.com/wp-content/uploads/imverde/2014/12/6066910548_f67d225bba_z.jpg").into(mImageView);
            runOnUiThread(new Runnable() {
                public void run(){
                    //LINK: http://square.github.io/picasso/
                    //SE VOLESSIMO USARE PICASSO PER SCARICARE L'IMMAGINE DALLE RISORSE IN LOCALE:
                   // Picasso.with(mContext).load(R.drawable.palma).into(mImageView);
                   //QUESTA OPERAZIONE DI LOAD RICHIEDE I PERMESSI NEL MANIFEST SEGUENTI:
                   // <uses-permission android:name="android.permission.INTERNET" />
                   // <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
                   Picasso.with(mContext).load("http://cultura.biografieonline.it/wp-content/uploads/2014/05/differenze-png-e-jpg.png").into(mImageView);
                    //PER GIOCO LA RENDIAMO CLICCABILE...
                    mImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), "Mi hai cliccato!!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });

            return null;
        }

    }

    public void ImgFromURL(View v){
        new AsyncPicasso(this).execute();
    }

}
