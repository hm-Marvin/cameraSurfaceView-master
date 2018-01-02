package com.marvin.camerasurfaceview;

import android.content.Context;
import android.content.res.TypedArray;
import android.hardware.Camera;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by hmw on 2017/12/20.
 */

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private Context mcontext;
    private HandlerThread cameraThread;
    private Handler handler;
    private Camera camera;

    private CameraUtil cameraUtil;
    private PreviewFrameListener previewFrameListener;

    private int CAMERA_WIDTH = 640;
    private int CAMERA_HEIGHT = 320;
    private int CAMERA_ID = 1;

    private int cameraWidth;
    private int cameraHeight;
    private int cameraID;


    public CameraSurfaceView(Context context) {
        this(context,null);

    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mcontext = context;
        initData(context,attrs);
        getHolder().addCallback(this);
    }

    private void initData(Context context,AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CameraSurfaceView);
        cameraWidth = typedArray.getInt(R.styleable.CameraSurfaceView_cameraWidth,CAMERA_WIDTH);
        cameraHeight = typedArray.getInt(R.styleable.CameraSurfaceView_cameraHeight,CAMERA_HEIGHT);
        cameraID = typedArray.getInt(R.styleable.CameraSurfaceView_cameraID,CAMERA_ID);
        typedArray.recycle();
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //创建新的线程负责打开相机
        cameraThread = new HandlerThread("camera");
        cameraThread.start();
        handler = new Handler(cameraThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                if (camera==null){
                    camera = Camera.open(cameraID);
                }
                Camera.getCameraInfo(cameraID,cameraInfo);
                try {
                    camera.setPreviewDisplay(getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, final int width, final int height) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                cameraUtil = new CameraUtil(mcontext);
                camera.stopPreview();
                cameraUtil.configureCamera(camera,width,height,cameraWidth,cameraHeight);
                if (cameraUtil.isScreenOriatationPortrait(mcontext)){
                    camera.setDisplayOrientation(90);
                }else {
                    camera.setDisplayOrientation(0);
                }
                if (camera!=null){
                    camera.startPreview();
                    camera.setPreviewCallback(CameraSurfaceView.this);
                }
            }
        });
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (camera!=null){
                    camera.release();
                }
            }
        });
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        previewFrameListener.PreviewFrame(bytes,camera,camera.getParameters().getPreviewSize().width,camera.getParameters().getPreviewSize().height);
    }

    /**
     * 设置预览数据回调
     * @param previewFrameListener
     */
    public void SetOnPreviewFrameListener(PreviewFrameListener previewFrameListener){
        this.previewFrameListener = previewFrameListener;
    }

    public void onStart(){
        if(camera!=null){
            camera.startPreview();
        }
    }

    public void onPause(){
        if (camera!=null){
            camera.stopPreview();
        }

    }
    public void onDestroy(){
        if (camera!=null){
            camera.release();
        }
    }

}
