package com.example.spirit.noahsark.domain;

/**
 * Created by spirit on 2018/3/18.
 */

public class Music {
    public String title;
    public String artist;
    public String duration;
    public String path;

    @Override
    public String toString() {
        return "Music{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
