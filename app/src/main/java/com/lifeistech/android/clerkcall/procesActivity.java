//package com.lifeistech.android.clerkcall;
//
//import android.media.MediaPlayer;
//import android.media.audiofx.Equalizer;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//public class procesActivity extends AppCompatActivity {
//    private MediaPlayer mediaPlayer;
//    private Equalizer mEqualizer;
//    private Equalizer mEqulizer;
//    private SeekBar mSeekBars;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_proces);
//
//        mediaPlayer = MediaPlayer.create(this, R.raw.music);
//        equalizer = new Equalizer(0, mediaPlayer.getAudioSessionId());
//        equalizer.setEnabled(true);
//
//        short bands = equalizer.getNumberOfBands();
//        Log.d("EqualizerSample", "NumberOfBands: " + bands);
//
//        short minEQLevel = equalizer.getBandLevelRange()[0];
//        short maxEQLevel = equalizer.getBandLevelRange()[1];
//        Log.d("EqualizerSample", "minEQLevel: " + String.valueOf(minEQLevel));
//        Log.d("EqualizerSample", "maxEQLevel: " + String.valueOf(maxEQLevel));
//
//        for (short i = 0; i < bands; i++) {
//            // 中心周波数の表示
//            Log.d("EqualizerSample", i + String.valueOf(equalizer.getCenterFreq(i) / 1000) + "Hz");
//
//            // レベルを真ん中に設定
//            equalizer.setBandLevel(i, (short) ((minEQLevel + maxEQLevel) / 2));
//}