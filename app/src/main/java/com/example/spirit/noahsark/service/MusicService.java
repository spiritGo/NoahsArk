package com.example.spirit.noahsark.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.SeekBar;

import com.example.spirit.noahsark.MainActivity;
import com.example.spirit.noahsark.domain.Music;
import com.example.spirit.noahsark.fragment.MusicListFragment;
import com.example.spirit.noahsark.utils.MusicUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by spirit on 2018/3/18.
 */

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;
    private List<Music> musicList;
    private MainActivity myActivity;
    private int item;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            updatePlayProgressShow();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final MusicBroadcast musicBroadcast = new MusicBroadcast();
        IntentFilter intentFilter = new IntentFilter("MusicService.MusicBroadcast");
        registerReceiver(musicBroadcast, intentFilter);
        mediaPlayer = new MediaPlayer();
        myActivity = MusicListFragment.getMyActivity();
        new Thread() {
            @Override
            public void run() {
                musicList = MusicUtils.findAllMusic(getApplicationContext());
            }
        }.start();

        myActivity.sb_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                System.out.println("move");
            }
        });
    }

    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void updatePlayProgressShow() {
        int currentPosition = mediaPlayer.getCurrentPosition();
        myActivity.sb_seek.setProgress(currentPosition);
        handler.sendEmptyMessageDelayed(0, 300);
        System.out.println("this is looper");
    }

    @Override
    public void onDestroy() {
        stop();
        super.onDestroy();
    }

    public void play(int item) {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(musicList.get(item).path);
                mediaPlayer.prepare();
                mediaPlayer.start();
                myActivity.sb_seek.setMax(Integer.parseInt(musicList.get(item).duration));
                myActivity.setBottomText(musicList.get(item).title, musicList.get(item).artist);
                updatePlayProgressShow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class MusicBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            item = intent.getIntExtra("item", 0);
            myActivity.setBottomText(musicList.get(item).title, musicList.get(item)
                    .artist);
            System.out.println(musicList.get(item).title + ", " + musicList.get(item)
                    .artist);
            play(item);

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (item < MusicUtils.getCount(getApplicationContext()) - 1) {
                        //顺序播放
                        item++;
                    } else {
                        //item 归零
                        item = 0;
                    }
                    play(item);
                    System.out.println("播放完成");
                }
            });
        }
    }
}
