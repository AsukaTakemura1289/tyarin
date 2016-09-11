package com.lifeistech.android.clerkcall;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by asuka on 16/02/28.
 */
public class PlayerActivity extends Activity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    private MediaPlayer mediaPlayer;
    private RecordAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_player);
        toolbar.setTitle("再生");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ListView listview = (ListView) findViewById(R.id.listView);
        File folder = new File(Environment.getExternalStorageDirectory() + "/clerkcall");
        File[] files = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".3gp");
            }
        });
        Log.d("FILES ARE THERE?", files.toString());
        adapter = new RecordAdapter(getApplicationContext());
        adapter.addAll(files);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        // 長押しの時
        listview.setOnItemLongClickListener(this);

        TextView emptyview = (TextView) findViewById(R.id.emptyView);
        listview.setEmptyView(emptyview);
        // TODO
        // 1. Toolbarでタイトルを表示(RecordActivityをみればわかるよ！)
        // 2. なにもファイルがない時に"ファイルがありません"て表示する hint: listview.setEmptyView(~ 表示したいView ~)
        // 3. ファイルを本当に消していいかのダイアログを表示する

    }

    // 画面を移動する時、画面が暗くなる時に呼ばれるメソッド
    @Override
    protected void onPause() {
        super.onPause();
        // 音声が再生中の時
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            // 音声の再生を止める
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // クリックした時の動作
        mediaPlayer = new MediaPlayer();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(adapter.getItem(position));
            mediaPlayer.setDataSource(inputStream.getFD());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        new SweetAlertDialog(PlayerActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("ファイルを消しますか?")
                .setContentText("ファイルは消すと元に戻せません")
                .setConfirmText("はい、消します！")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        File file = adapter.getItem(position);
                        adapter.remove(file);
                        adapter.notifyDataSetChanged();
                        file.delete();
                        // ダイアログをアニメーションさせて閉じる
                        sweetAlertDialog
                                .setTitleText("消しました!")
                                .setContentText("ファイルの削除に成功しました!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
        return true;
    }

    //    public void playbutton(View v) {
//        IsPlaying =! IsPlaying;
//
//        Log.d("playButton : ", String.valueOf(IsPlaying));
//
//
//        if (IsPlaying) { //再生中
//            mp.start();
//
//            try {
//                mp.prepare();
//            } catch (IOException e) {
//                mp.release();
//                mp = null;
//            }
//
//            playbutton.setText("再生中");
//
//            Log.d("playButton = ", "再生中");
//        }
//        else { //停止中
//            playbutton.setText("停止中");
//            mp.stop();
//
//            try {
//                mp.prepare();
//            } catch (IOException e) {
//                mp.release();
//                mp = null;
//            }
//
//
//            Log.d("playButton = ", "停止中");
//
//        }
//    }
//
//
//    private boolean IsPlaying() {
//        return false;
//    }


}


