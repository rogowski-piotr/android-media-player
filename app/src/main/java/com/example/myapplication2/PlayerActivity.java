package com.example.myapplication2;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends Activity {

    Button returnButton;
    Button pauseButton;
    Spinner spinner;
    MediaPlayer mediaPlayer;

    int recordingPauseTime;
    File rootDir;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity);

        spinner = (Spinner) findViewById(R.id.spinner);

        rootDir = Environment.getExternalStorageDirectory();
        returnButton = (Button) findViewById(R.id.returnButton);
        pauseButton = (Button) findViewById(R.id.pauseRecordingButton);

        File[] rootDirFiles = rootDir.listFiles();
        List<String> fileNames = new ArrayList<>();
        for (int i = 0; i < rootDirFiles.length; i++)
        {
            if (rootDirFiles[i].isFile()){
                fileNames.add(rootDirFiles[i].getName());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fileNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void playSelectedRecording(View view) throws IOException {
        String selectedFile = rootDir + "/" + spinner.getSelectedItem().toString();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(selectedFile);
        mediaPlayer.prepare();
        mediaPlayer.setVolume((float) 0.1, (float) 0.7);
        mediaPlayer.start();
        Toast.makeText(this, "Playing started", Toast.LENGTH_LONG).show();
    }

    public void stopSelectedRecording(View view) throws IOException {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        Toast.makeText(this, "Playing stopped", Toast.LENGTH_LONG).show();
//        recordingPauseTime = 0;
    }

    public void pauseSelectedRecording(View view) throws IOException {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
//            recordingPauseTime = mediaPlayer.getCurrentPosition();
            Log.d("debug", Integer.toString(mediaPlayer.getCurrentPosition()));
            pauseButton.setText("RESUME");
            Toast.makeText(this, "Playing paused", Toast.LENGTH_LONG).show();
        } else {
            Log.d("debug", "ola + " + Integer.toString(mediaPlayer.getCurrentPosition()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d("debug", "correctly seekTo");
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition(), MediaPlayer.SEEK_CLOSEST);
            }
            mediaPlayer.start();
            pauseButton.setText("PAUSE");
            Toast.makeText(this, "Playing resumed", Toast.LENGTH_LONG).show();
        }
    }


    public void switchToMain(View view){
        startActivity(new Intent(this, MainActivity.class));
    }
}
