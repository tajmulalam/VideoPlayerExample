package com.educareappsltd.videoplayerexample;

import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private VideoView myVideoPlayer;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen();
        setContentView(R.layout.activity_main);

        myVideoPlayer = (VideoView) findViewById(R.id.myVideoPlayer);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_one);
        myVideoPlayer.setVideoURI(uri);
        myVideoPlayer.requestFocus();
        myVideoPlayer.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(MainActivity.this, "2nd video", Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_two);
                myVideoPlayer.setVideoURI(uri);
                myVideoPlayer.requestFocus();
                myVideoPlayer.start();
            }
        }, 6000);
    }


    private void fullScreen() {

        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }

    }
}
