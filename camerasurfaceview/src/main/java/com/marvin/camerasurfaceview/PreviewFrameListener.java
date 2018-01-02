package com.marvin.camerasurfaceview;

import android.hardware.Camera;

/**
 * Created by hmw on 2017/12/21.
 */

public interface PreviewFrameListener {
    void PreviewFrame(byte[] bytes, Camera camera, int width, int height);
}
