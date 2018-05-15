package com.pokidin.a.shafa;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.pokidin.a.shafa.camera.CameraHelper;

public class CameraActivity2 extends AppCompatActivity{

    private final String TAG = "CameraActivity2";
    private final int CAMERA1 = 0;
    private final int CAMERA2 = 1;

    CameraManager mCameraManager = null;
    CameraHelper[] myCameras = null;

    private CameraHelper[] mCameras = null;
    private Button mButtonOpenCamera1 = null;
    private Button mButtonOpenCamera2 = null;

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

        mButtonOpenCamera1 = findViewById(R.id.btnOpenCamera1);
        mButtonOpenCamera2 = findViewById(R.id.btnOpenCamera2);

        mButtonOpenCamera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myCameras[CAMERA1].isOpen()){
                    myCameras[CAMERA2].closeCamera();
                }
                if (myCameras[CAMERA2] != null){
                    if (!myCameras[CAMERA2].isOpen()){
                        myCameras[CAMERA2].openCamera();
                    }
                }
            }
        });
    }
}

