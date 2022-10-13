package com.example.myapplication2;

import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button switchToPlayerButton;
    Button switchToRecorderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchToPlayerButton = (Button) findViewById(R.id.toPlayerButton);
        switchToRecorderButton = (Button) findViewById(R.id.toRecorderButton);
    }

    public void switchToPlayer(View view) {
        startActivity(new Intent(this, PlayerActivity.class));
    }

    public void switchToRecorder(View view) {
        startActivity(new Intent(this, RecorderActivity.class));
    }
}