package com.example.zingmp3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addSongToList();


        this.buttonPlay = findViewById(R.id.playButton);
        this.nextSongButton = findViewById(R.id.nextSong);
        this.previousSongButton = findViewById(R.id.previousSong);
        this.nameSong = findViewById(R.id.nameSong);
        this.totalTime = findViewById(R.id.totaltime);
        this.currentTime = findViewById(R.id.currenttime);
        this.barSong = findViewById(R.id.seekBar);

        this.mediaPlayer = MediaPlayer.create(MainActivity.this, listSong.get(indexSong).getFile());
        this.mediaPlayer.start();
        setTotalTime();
        this.playSong = true;
        this.nameSong.setText(listSong.get(indexSong).getNameSong());
        this.buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playSong) {
                    buttonPlay.setImageResource(R.drawable.pausebutton);
                    playSong = false;
                    mediaPlayer.pause();
                } else {
                    buttonPlay.setImageResource(R.drawable.playbutton);
                    playSong = true;
                    mediaPlayer.start();
                    barSong.setProgress(0);
                }
            }
        });

        this.previousSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indexSong > 0 && indexSong < listSong.size() - 1) {
                    indexSong--;
                } else {
                    indexSong = 0;
                }

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer = MediaPlayer.create(MainActivity.this, listSong.get(indexSong).getFile());
                mediaPlayer.start();
                nameSong.setText(listSong.get(indexSong).getNameSong());
                playSong = true;
                setStateButtonPlay();
                setTotalTime();
                barSong.setProgress(0);
            }
        });

        this.nextSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indexSong >= 0 && indexSong < listSong.size() - 1) {
                    indexSong++;
                } else {
                    indexSong = 0;
                }

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer = MediaPlayer.create(MainActivity.this, listSong.get(indexSong).getFile());
                mediaPlayer.start();
                nameSong.setText(listSong.get(indexSong).getNameSong());
                playSong = true;
                setStateButtonPlay();
                setTotalTime();
                barSong.setProgress(0);
            }
        });

    }

    private void setStateButtonPlay() {
        if (playSong) {
            buttonPlay.setImageResource(R.drawable.playbutton);
        } else {
            buttonPlay.setImageResource(R.drawable.pausebutton);
        }
    }

    private void addSongToList() {
        Song song1 = new Song("Thich Thi Den", R.raw.thichthiden);
        Song song2 = new Song("Tinh Sau Thien Thu", R.raw.tinhsauthienthu);
        Song song3 = new Song("Truc Sinh", R.raw.trucxinh);
        listSong.add(song1);
        listSong.add(song3);
        listSong.add(song2);
    }

    public void setTotalTime() {
        int timeSong = mediaPlayer.getDuration();
        String time = String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(timeSong),
                TimeUnit.MILLISECONDS.toSeconds(timeSong) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeSong)));
        totalTime.setText(time);
        barSong.setMax(timeSong);
    }

    public void setCurrentTimeSong(){

    }

    private List<Song> listSong = new ArrayList<>();
    private int indexSong = 0;
    private ImageView buttonPlay;
    private ImageView nextSongButton;
    private ImageView previousSongButton;
    private MediaPlayer mediaPlayer;
    private SeekBar barSong;
    private TextView nameSong;
    private TextView totalTime;
    private TextView currentTime;
    private boolean playSong;
}
