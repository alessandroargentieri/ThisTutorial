package com.example.alessandro.tutorial1;

import android.app.Activity;
import android.os.Bundle;

public class PaintActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SingleTouchEventView(this, null));
    }
}