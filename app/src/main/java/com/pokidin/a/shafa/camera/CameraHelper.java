package com.pokidin.a.shafa.camera;

import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.util.Size;

public class CameraHelper {
    private final static String TAG = "CameraHelper";

    private CameraManager mCameraManager = null;
    private String mCameraID = null;
    private CameraDevice mCameraDevice = null;

    public boolean isOpen() {
        if (mCameraDevice == null) {
            return false;
        } else {
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void openCamera() {
        try {
            mCameraManager.openCamera(mCameraID, mCameraCallback, null);
        } catch (CameraAccessException | SecurityException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void closeCamera() {
        if (mCameraDevice != null){
            mCameraDevice.close();
            mCameraDevice = null;
        }
    }

    private CameraDevice.StateCallback mCameraCallback = new CameraDevice.StateCallback() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            mCameraDevice = camera;
            Log.i(TAG, "Open camera with id: " + mCameraDevice.getId());
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            mCameraDevice.close();
            Log.i(TAG, "Disconnected camera with id: " + mCameraDevice.getId());
            mCameraDevice = null;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            Log.i(TAG, "Error! Camera with id: " + mCameraDevice.getId() + " error: " + error);
        }
    };

    public CameraHelper(@NonNull CameraManager cameraManager, @NonNull String cameraID) {
        mCameraManager = cameraManager;
        mCameraID = cameraID;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void viewFormatSize(int formatSize) {
        CameraCharacteristics characteristics = null;
        try {
            String[] cameraList = mCameraManager.getCameraIdList();
            for (String cameraID : cameraList) {
                Log.i(TAG, "CameraID: " + cameraID);

                characteristics = mCameraManager.getCameraCharacteristics(cameraID);
                StreamConfigurationMap configurationMap =
                        characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                Size[] sizesJPEG = configurationMap.getOutputSizes(ImageFormat.JPEG);
                if (sizesJPEG != null) {
                    for (Size item : sizesJPEG) {
                        Log.i(TAG, "w: " + item.getWidth() + ", h: " + item.getHeight());
                    }
                } else {
                    Log.e(TAG, "Camera with ID: " + cameraID + " don't support JPEG");
                }
            }
        } catch (CameraAccessException e) {
            Log.d(TAG, e.getMessage());
        }
    }
}
