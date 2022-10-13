package com.example.myapplication2;

import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.IOException;

public class RecorderActivity extends Activity {

    File rootDir;
    File tempFile;
    MediaRecorder mediaRecorder;
    Button startButton;
    Button pauseButton;
    Button stopButton;
    Button returnButton;
    boolean recording = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recorder_activity);

        returnButton = (Button) findViewById(R.id.returnButton);
        startButton = (Button) findViewById(R.id.startRecordingButton);
        pauseButton = (Button) findViewById(R.id.pauseRecordingButton);
        stopButton = (Button) findViewById(R.id.stopRecordingButton);

        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                        Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                startActivityForResult(intent, 999);
            }
        }
    }

    public void startRecording(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 0);
        } else {
            try {
                startButton.setEnabled(false);
                pauseButton.setEnabled(true);
                stopButton.setEnabled(true);
                rootDir = Environment.getExternalStorageDirectory();
                tempFile = File.createTempFile("test_", ".mp3", rootDir);
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioChannels(1);
                mediaRecorder.setAudioSamplingRate(8000);
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setOutputFile(tempFile.getAbsolutePath());
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mediaRecorder.prepare();
                mediaRecorder.start();
                recording = true;
                Toast.makeText(this, "Started recording", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopRecording(View view){
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        mediaRecorder.stop();
        mediaRecorder.release();
        pauseButton.setText("PAUSE");
        recording = false;
        Toast.makeText(this, "Stopped recording", Toast.LENGTH_LONG).show();

    }

    public void pauseRecording(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
            stopButton.setEnabled(true);

            if (recording) {
                mediaRecorder.pause();
                recording = false;
                pauseButton.setText("RESUME");
                Toast.makeText(this, "Paused recording", Toast.LENGTH_LONG).show();
            } else {
                mediaRecorder.resume();
                recording = true;
                pauseButton.setText("PAUSE");
                Toast.makeText(this, "Resumed recording", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void switchToMain(View view){
        startActivity(new Intent(this, MainActivity.class));
    }
}
