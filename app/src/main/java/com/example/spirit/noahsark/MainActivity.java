package com.example.spirit.noahsark;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.spirit.noahsark.fragment.MusicListFragment;
import com.example.spirit.noahsark.service.MusicService;
import com.example.spirit.noahsark.utils.MusicUtils;

/**
 * Created by spirit on 2018/3/18.
 */

public class MainActivity extends FragmentActivity {

//    private FrameLayout container;
    public TextView tv_title;
    public TextView tv_artist;
    public SeekBar sb_seek;
    private android.support.v4.app.FragmentManager fragmentManager;
    private TextView tv_count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, MusicService.class));
        initViews();
    }

    public android.support.v4.app.FragmentManager getManager() {
        return fragmentManager;
    }

    private void initViews() {
//        container = findViewById(R.id.container);
        sb_seek = findViewById(R.id.sb_seek);
        tv_title = findViewById(R.id.tv_title);
        tv_artist = findViewById(R.id.tv_artist);
        tv_count = findViewById(R.id.tv_count);
        tv_count.setText(MusicUtils.getCount(this)+" ");

        fragmentManager = getSupportFragmentManager();
//        MusicService musicService = new MusicService(fragmentManager);
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.container, new MusicListFragment(), "musicFragment");
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        System.out.println(tv_artist);
        super.onStart();
    }

    /**
     * 设置播放页底部的歌曲信息
     */
    public void setBottomText(String title, String artist) {
//        TextView tv_title = this.findViewById(R.id.tv_artist);
//        TextView tv_artist = this.findViewById(R.id.tv_artist);
        tv_artist.setText(artist);
        tv_title.setText(title);

    }
}
