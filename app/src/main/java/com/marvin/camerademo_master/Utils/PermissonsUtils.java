package com.marvin.camerademo_master.Utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by hmw on 2017/12/18.
 */

public class PermissonsUtils {

    public static void CheckPermissions(Activity activity,String[] requestPermission){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (isCheck(activity,requestPermission)){
                ActivityCompat.requestPermissions(activity,requestPermission,0);
            }
        }
    }

    private static boolean isCheck(Activity activity,String[] permissions) {
        for (String permission :permissions){
            if (ContextCompat.checkSelfPermission(activity, permission)!= PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }
}
