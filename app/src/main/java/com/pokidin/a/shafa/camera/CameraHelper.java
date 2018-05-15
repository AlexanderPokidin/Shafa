package com.pokidin.a.shafa.camera;

import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.util.Size;

public class CameraHelper {
    final static String TAG = "CameraHelper";

    private CameraManager mCameraManager = null;
    private String mCameraID = null;

    public CameraHelper(@NonNull CameraManager cameraManager, @NonNull String cameraID) {
        mCameraManager = cameraManager;
        mCameraID = cameraID;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void viewFormatSize(int formatSize) {
        CameraCharacteristics characteristics = null;
        try {
            String[] cameraList = mCameraManager.getCameraIdList();
            for (String cameraID : cameraList){
                Log.i(TAG, "CameraID: " + cameraID);

                characteristics = mCameraManager.getCameraCharacteristics(cameraID);
                StreamConfigurationMap configurationMap =
                        characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                Size[] sizesJPEG = configurationMap.getOutputSizes(ImageFormat.JPEG);
                if (sizesJPEG != null){
                    for (Size item : sizesJPEG){
                        Log.i(TAG, "w: " + item.getWidth() + ", h: " + item.getHeight());
                    }
                } else {
                    Log.e(TAG, "Camera with ID: " + cameraID + " don't support JPEG");
                }
            }
        } catch (CameraAccessException e) {
            Log.d(TAG, e.getMessage());
//            e.printStackTrace();
        }
    }
}
