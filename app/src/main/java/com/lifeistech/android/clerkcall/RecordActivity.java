package com.lifeistech.android.clerkcall;

import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RecordActivity extends AppCompatActivity {
    private MediaRecorder recorder;
    private Timer timer;
    private TimerTask timerTask;
    private long startTime;
    private Handler handler = new Handler();

    private boolean isRecording = false;

    ImageButton button;
    EditText editText;

    TextView timeText;
    RelativeLayout playingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        button = (ImageButton) findViewById(R.id.buttonStart);
        editText = (EditText) findViewById(R.id.editText);
        timeText = (TextView) findViewById(R.id.textTime);
        playingLayout = (RelativeLayout) findViewById(R.id.layoutPlaying);
        // 最初は非表示にしておく
        playingLayout.setVisibility(View.INVISIBLE);
        toolbar.setTitle("レコード");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void record(View v) {

        if (!isRecording) {
            //
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            //
            try {
                String fileName = editText.getText() + ".3gp";
                // Environment.getExternalStorageDirectory() => storage/emulated/0
                String folderPath = Environment.getExternalStorageDirectory() + "/clerkcall/";
                {
                    final File folder = new File(folderPath);
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }
                }
                recorder.setOutputFile(folderPath + fileName);
                recorder.prepare();
                recorder.start();

                startTime = System.currentTimeMillis();
                startTimer();
                startAnim();
                isRecording = true;
                // 再生をはじめたら、再生ボタンを非表示にする
                button.setVisibility(View.INVISIBLE);
                // 再生をはじめたら表示する
                playingLayout.setVisibility(View.VISIBLE);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                recorder.stop();
                recorder.reset();
                recorder.release();
                recorder = null;
                stopTimer();
                stopAnim();
                isRecording = false;
                // 再生をはじめたら、再生ボタンを表示にする
                button.setVisibility(View.VISIBLE);
                // 再生をはじめたら表示する
                playingLayout.setVisibility(View.INVISIBLE);
                editText.setText("");
                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("保存しました!")
                        .setContentText("")
                        .show();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    // タイマーをスタートさせる処理
    private void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                final long time = System.currentTimeMillis();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int second = (int) ((time - startTime) / 1000);
                        int minute = second / 60;
                        second = second % 60;
                        timeText.setText(String.format("%02d", minute) + " : " + String.format("%02d", second));
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 500);
    }

    // タイマーと止める処理
    private void stopTimer() {
        // 時計を停止する
        timer.cancel();
        timer = null;
    }

    void startAnim(){
        findViewById(R.id.loadingView).setVisibility(View.VISIBLE);
    }

    void stopAnim(){
        findViewById(R.id.loadingView).setVisibility(View.GONE);
    }

}
