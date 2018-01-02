package com.marvin.camerademo_master;

import android.Manifest;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.marvin.camerademo_master.Utils.PermissonsUtils;
import com.marvin.camerasurfaceview.CameraSurfaceView;
import com.marvin.camerasurfaceview.PreviewFrameListener;

public class MainActivity extends AppCompatActivity implements PreviewFrameListener {

    private CameraSurfaceView sf_camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissonsUtils.CheckPermissions(this,new String[]{Manifest.permission.CAMERA});
        initView();
    }


    private void initView() {
        sf_camera = findViewById(R.id.sf_camera);
        sf_camera.SetOnPreviewFrameListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        sf_camera.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sf_camera.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sf_camera.onDestroy();
    }

    /***
     * 获取视频流
     * @param bytes
     * @param camera
     * @param width
     * @param height
     */
    @Override
    public void PreviewFrame(byte[] bytes, Camera camera, int width, int height) {

    }


}
