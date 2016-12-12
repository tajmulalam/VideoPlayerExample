package com.educareappsltd.videoplayerexample;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

public class CustomVideoPlayerActivity extends Activity {
    private VideoView myVideoPlayer;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_video_player);
        myVideoPlayer = (VideoView) findViewById(R.id.myVideoPlayer);
        playVideo();
    }

    private Uri uri;

    private void playVideo() {
        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_one);
        myVideoPlayer.setVideoURI(uri);
        myVideoPlayer.requestFocus();
        myVideoPlayer.start();
        myVideoPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_two);
                myVideoPlayer.setVideoURI(uri);
                myVideoPlayer.requestFocus();
                myVideoPlayer.start();
                myVideoPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_one);
                        myVideoPlayer.setVideoURI(uri);
                        myVideoPlayer.requestFocus();
                        myVideoPlayer.start();
                    }
                });
            }
        });


    }
}
