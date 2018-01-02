package com.marvin.camerasurfaceview;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;

import java.util.List;

/**
 * Created by hmw on 2017/12/20.
 */

public class CameraUtil {

    private Context mcontext;

    public CameraUtil(Context mcontext) {
        this.mcontext = mcontext;
    }

    /**
     * 配置相机参数
     * @param mCamera
     * @param width
     * @param height
     * @param cameraWidth
     * @param cameraHeight
     */
    public void configureCamera(Camera mCamera, int width, int height, int cameraWidth, int cameraHeight){
        Camera.Parameters parameters = mCamera.getParameters();
        setOptimalPreviewSize(parameters, width, height,cameraWidth,cameraHeight);
        setAutoFocus(parameters);
        mCamera.setParameters(parameters);
    }

    /**
     * 设置相机支持的预览画面的宽高
     *  @param cameraParameters
     * @param width
     * @param height
     * @param cameraWidth
     * @param cameraHeight
     */
    private void setOptimalPreviewSize(Camera.Parameters cameraParameters, int width, int height, int cameraWidth, int cameraHeight) {
        List<Camera.Size> previewSizes = cameraParameters.getSupportedPreviewSizes();
        int previewWidth = 0;
        int previewHeight = 0;

        for (int i = 0, size = previewSizes.size(); i < size; i++) {
            if (previewSizes.get(i).width == cameraWidth && previewSizes.get(i).height == cameraHeight) {
                previewWidth = cameraWidth;
                previewHeight = cameraHeight;
                break;
            }
        }
        //若没有则选择自适应
        if (previewWidth == 0 && previewHeight == 0) {
            //  获取最合适的预览尺寸
            Camera.Size size = getCloselyPreSize(width, height, previewSizes);
            previewWidth = size.width;
            previewHeight = size.height;
        }
        /**
         * 计算尺寸全帧位图规模较小的位图检测面临的比例比完整的位图位图具有很高的性能。
         * 较小的图像大小->检测速度更快, 但距离检测面临短, 所以计算大小跟随你的目的
         */
        cameraParameters.setPreviewSize(previewWidth, previewHeight);
    }




    /**
     * 通过对比得到与宽高比最接近的尺寸（如果有相同尺寸，优先选择）
     *
     * @param surfaceWidth  需要被进行对比的原宽
     * @param surfaceHeight 需要被进行对比的原高
     * @param preSizeList   需要对比的预览尺寸列表
     * @return 得到与原宽高比例最接近的尺寸
     */
    protected Camera.Size getCloselyPreSize(int surfaceWidth, int surfaceHeight,
                                                             List<Camera.Size> preSizeList) {
        int ReqTmpWidth;
        int ReqTmpHeight;
        // 当屏幕为垂直的时候需要把宽高值进行调换，保证宽大于高
        if (isScreenOriatationPortrait(mcontext)) {
            ReqTmpWidth = surfaceHeight;
            ReqTmpHeight = surfaceWidth;
        } else {
            ReqTmpWidth = surfaceWidth;
            ReqTmpHeight = surfaceHeight;
        }
        //先查找preview中是否存在与surfaceview相同宽高的尺寸
        for (Camera.Size size : preSizeList) {
            if ((size.width == ReqTmpWidth) && (size.height == ReqTmpHeight)) {
                return size;
            }
        }

        // 得到与传入的宽高比最接近的size
        float reqRatio = ((float) ReqTmpWidth) / ReqTmpHeight;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Camera.Size retSize = null;
        for (Camera.Size size : preSizeList) {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
        }

        return retSize;
    }


    /**
     * 返回当前屏幕是否为竖屏。
     *
     * @param context
     * @return 当且仅当当前屏幕为竖屏时返回true, 否则返回false。
     */
    public boolean isScreenOriatationPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }



    /**
     * 设置相机自动调焦
     *
     * @param cameraParameters
     */
    private void setAutoFocus(Camera.Parameters cameraParameters) {
        List<String> focusModes = cameraParameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            cameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
    }
}
