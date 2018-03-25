package com.example.spirit.noahsark.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Message;
import android.provider.MediaStore;
import android.widget.ListView;

import com.example.spirit.noahsark.domain.Music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by spirit on 2018/3/18.
 */

public class MusicUtils {

    public static List<Music> findAllMusic(Context context) {
        ContentResolver mResolver = context.getContentResolver();
        Cursor cursor = mResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                null,
                null, null);
        List<Music> mMusicList = new ArrayList<>();

        while (cursor.moveToNext()) {
            Music music = new Music();
            music.title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media
                    .TITLE));
            music.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media
                    .ARTIST));
            music.duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media
                    .DURATION));
            music.path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            mMusicList.add(music);
        }
        return mMusicList;
    }


    public static int getCount(Context context){
        ContentResolver mResolver = context.getContentResolver();
        Cursor cursor = mResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                null, null, null);
        int count = cursor.getCount();
        return count;
    }
}
