package com.lifeistech.android.clerkcall;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.SeekBar;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class EqualizerActivity extends AppCompatActivity implements MediaController.MediaPlayerControl {
    private MediaPlayer mMediaPlayer;
    private MediaController mPlayerControl;
    private final static int CHOSE_FILE_CODE = 12345;
    String decodedfilePath;
    Equalizer mEqualizer;
    SeekBar mSeekbar;
    SeekBar mSeekbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equalizer);
        mPlayerControl = (MediaController) findViewById(R.id.mediaController);
        mSeekbar = (SeekBar) findViewById(R.id.seekBar);
        mSeekbar2 = (SeekBar) findViewById(R.id.seekBar2);
//        mSeekbar3 = (SeekBar)findViewById(R.id.seekBar3);
        mMediaPlayer = new MediaPlayer();


        //ファイルの指定
        String filename = "android.resource://" + getPackageName() + "/"
                + R.raw.kimi;



        //エラーが起きてもおちないようにする
            try {
                mMediaPlayer.setDataSource(this, Uri.parse(filename));
                mMediaPlayer.prepareAsync();
                mPlayerControl.setMediaPlayer(this);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

        Log.d(EqualizerActivity.class.getSimpleName(), "player controll");

        // mediaplayerの情報を元にしてEqualizerの生成
        mEqualizer = new Equalizer(0, mMediaPlayer.getAudioSessionId());
        mEqualizer.setEnabled(true);

        Log.d(EqualizerActivity.class.getSimpleName(), "equalizer");

        // SeekBar中の値
        //bands・・・周波数の個数
        short bands = mEqualizer.getNumberOfBands();
        //最小
        short minEQLevel = mEqualizer.getBandLevelRange()[0];
        //最大
        final short maxEQLevel = mEqualizer.getBandLevelRange()[1];

        //SeekBarの最大値を設定
        mSeekbar.setMax(maxEQLevel + Math.abs(minEQLevel));

        Log.e("周波数", String.valueOf(mEqualizer.getCenterFreq((short) 4) / 1000));
//        mEqualizer.setBandLevel((short) 4, (short) 1500);

        //SeekBar
        mSeekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //数値が決まった瞬間
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //0からではなく数字からHzを求める
                    short band = (short) (mSeekbar.getProgress() - maxEQLevel);
                    mEqualizer.setBandLevel((short) 4, (short) band);
                    Log.e("TAG", String.valueOf(band));
                }
                return false;
            }
        });

        Log.d(EqualizerActivity.class.getSimpleName(), "seekbar2");

        mSeekbar2.setOnTouchListener(new View.OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.M)
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    PlaybackParams params = new PlaybackParams();
//                    int progress = mSeekbar2.getProgress() - 5;
//                    if (progress > 0) {
//                        // 早くする
//                        params.setSpeed(2.0f);
//                    } else if(progress < 0) {
//                        //  遅くする
//                        params.setSpeed(0.5f);
//                    } else {
//                        // 普通
//                        params.setSpeed(1.0f);
//                    }
//                    mMediaPlayer.setPlaybackParams(params);
                }
                return true;
            }
        });


        mMediaPlayer.start();


        Log.d(EqualizerActivity.class.getSimpleName(), "load media player");


    }

    public void chose(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, CHOSE_FILE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CHOSE_FILE_CODE && resultCode == RESULT_OK) {
            String filePath = data.getDataString().replace("file://", "");
            decodedfilePath = null;
            try {
                decodedfilePath = URLDecoder.decode(filePath, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d("TAG", decodedfilePath);

        }
    }


    @Override
    public void start() {
        mMediaPlayer.start();
    }

    @Override
    public void pause() {
        mMediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mMediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return mMediaPlayer.getCurrentPosition() / mMediaPlayer.getDuration();
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
