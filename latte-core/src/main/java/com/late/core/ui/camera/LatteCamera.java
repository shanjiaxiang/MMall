package com.late.core.ui.camera;

import android.net.Uri;

import com.late.core.fragments.PermissionFragment;
import com.late.core.util.file.FileUtil;

public class LatteCamera {

    public static Uri createCropFile(){
        return Uri.parse(FileUtil.createFile("crop_image",
                FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionFragment fragment){
        new CameraHandler(fragment).beginCameraDialog();
    }
}
