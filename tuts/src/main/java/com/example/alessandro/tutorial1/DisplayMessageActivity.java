package com.example.alessandro.tutorial1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    public TextView messaggioEdit;
    public String parametro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String parametro = "zuckemberg";
        try{
            //teoria: //teoria: http://101apps.co.za/articles/passing-data-between-activities.html
            Bundle b = getIntent().getExtras();
            parametro = b.getString("parametro");
          //  Bundle extras = getIntent().getExtras();
          //  if (extras != null) {
          //      parametro = extras.getString("parametro");
          //      Log.e("parametro",parametro);
          //  }
        }catch(Exception e){}
        setContentView(R.layout.activity_display_message_layout);
        messaggioEdit = (TextView)findViewById(R.id.textViewMessaggio);
        messaggioEdit.setText(parametro);

    }

    //void collegata al metodo onClick del bottone del layout
    public void Back(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
