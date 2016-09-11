package com.lifeistech.android.clerkcall;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void record(View v){
        Intent intent = new Intent(this,RecordActivity.class);
        startActivity(intent);
    }

    public void playbutton(View v){
        Intent intent = new Intent(this,PlayerActivity.class);
        startActivity(intent);
    }

    public void edit(View v){
        Intent intent = new Intent(this,EqualizerActivity.class);
        startActivity(intent);
    }

}
