package com.pokidin.a.shafa;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;

import com.pokidin.a.shafa.camera.CameraHelper;

public class CameraActivity2 extends AppCompatActivity{

    final static String TAG = "CameraActivity2";

    CameraManager mCameraManager = null;
    CameraHelper[] myCameras = null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String[] cameraList = mCameraManager.getCameraIdList();
            myCameras = new CameraHelper[cameraList.length];
            for (String cameraID : cameraList){
                Log.i(TAG, "CameraID: " + cameraID);
                int id = Integer.parseInt(cameraID);

                myCameras[id] = new CameraHelper(mCameraManager, cameraID);
                myCameras[id].viewFormatSize(ImageFormat.JPEG);
            }
        } catch (CameraAccessException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
    }
}

