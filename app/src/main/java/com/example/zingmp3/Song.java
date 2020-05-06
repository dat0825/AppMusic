package com.example.zingmp3;

public class Song {
    public Song(String nameSong, int file) {
        this.nameSong = nameSong;
        this.file = file;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }

    private String nameSong;
    private int file;
}
