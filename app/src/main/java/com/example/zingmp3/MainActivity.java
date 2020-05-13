package com.example.zingmp3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
        this.currentTimeSong = findViewById(R.id.currenttime);
        this.barSong = findViewById(R.id.seekBar);

        this.mediaPlayer = MediaPlayer.create(MainActivity.this, listSong.get(indexSong).getFile());
        this.mediaPlayer.start();
        setTotalTime();
        this.playSong = true;
        this.nameSong.setText(listSong.get(indexSong).getNameSong());
        setStateButtonPlay();
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

        setCurrentTimeSong();

        barSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if(b){
                    barSong.setProgress(progress);
                    if(mediaPlayer != null){
                        mediaPlayer.seekTo(progress*1000);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
        barSong.setMax(timeSong/1000);
    }

    public void setCurrentTimeSong() {
        handler = new Handler();
        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                int currentTime = 0;
                if (mediaPlayer != null) {
                    currentTime = mediaPlayer.getCurrentPosition() / 1000;
                    barSong.setProgress(currentTime);
                }
                handler.postDelayed(this, 1000);
                String time = String.format("%d:%d",
                        TimeUnit.SECONDS.toMinutes(currentTime),
                        TimeUnit.SECONDS.toSeconds(currentTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(currentTime)));
                currentTimeSong.setText(time);
            }
        });

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
    private TextView currentTimeSong;
    private boolean playSong;
    private Handler handler;
}
