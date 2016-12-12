package com.educareappsltd.videoplayerexample;

import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private VideoView myVideoPlayer;
    private MediaController mediaController;
    private Camera mCamera = null;
    private CameraView mCameraView = null;
    private SurfaceView camera_view;
    private SurfaceHolder holder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen();
        setContentView(R.layout.activity_main);
        myVideoPlayer = (VideoView) findViewById(R.id.myVideoPlayer);
        camera_view = (SurfaceView) findViewById(R.id.camera_view);
        makeVideoViewFullScreen();
        try {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            mCamera.lock();
        } catch (RuntimeException re) {
            Log.v("camera: ", "Could not initialize the Camera");
            re.printStackTrace();
        }
        holder = camera_view.getHolder();
        holder.addCallback(this);

        playVideo();

    }

    private void makeVideoViewFullScreen() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) myVideoPlayer.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.setMargins(0, 0, 0, 0);
        myVideoPlayer.setLayoutParams(params);
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

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        previewCamera();


    }

    private void playVideo() {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_one);
        myVideoPlayer.setVideoURI(uri);
        myVideoPlayer.requestFocus();
        myVideoPlayer.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_two);
                myVideoPlayer.setVideoURI(uri);
                myVideoPlayer.requestFocus();
                myVideoPlayer.start();
            }
        }, 6000);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        playVideo();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void previewCamera() {
        Log.v("preview Camera: ", "in surfaceCreated");

        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.v("Errror: ", "Could not start the preview");
            e.printStackTrace();
        }
    }
}
