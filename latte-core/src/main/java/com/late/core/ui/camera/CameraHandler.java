package com.late.core.ui.camera;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.FileUtils;
import com.late.core.R;
import com.late.core.fragments.PermissionFragment;
import com.late.core.util.file.FileUtil;

import java.io.File;

public class CameraHandler implements View.OnClickListener{
    private final AlertDialog DIALOG;
    private final PermissionFragment DELEGATE;

    public CameraHandler(PermissionFragment fragment) {
        this.DELEGATE = fragment;
        this.DIALOG = new AlertDialog.Builder(fragment.getContext()).create();
    }

    final void beginCameraDialog(){
        DIALOG.show();
        final Window window = DIALOG.getWindow();
        if (window != null){
            window.setContentView(R.layout.dialog_camera_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            params.dimAmount = 0.5f;
            window.setAttributes(params);

            window.findViewById(R.id.photodialog_btn_take).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_native).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_cancel).setOnClickListener(this);
        }
    }

    private String getFileName(){
        return FileUtil.getFileNameByTime("IMG", "jpg");
    }

    private void takePhoto(){
        final String currentPhotoName = getFileName();
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final File tempFile = new File(FileUtil.CAMERA_PHOTO_DIR, currentPhotoName);

        //兼容7.0及以上的写法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            final ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getPath());
            final Uri uri = DELEGATE.getContext().getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            //需要将Uri路径转化为实际路径
            final File realFile = FileUtils.getFileByPath(
                    FileUtil.getRealFilePath(DELEGATE.getContext(), uri));
            final Uri realUri = Uri.fromFile(realFile);
            CameraImageBean.getInstance().setPath(realUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }else {
            final Uri fileUri = Uri.fromFile(tempFile);
            CameraImageBean.getInstance().setPath(fileUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        }
        DELEGATE.startActivityForResult(intent, RequestCodes.TAKE_PHOTO);
    }

    private void pickPhoto(){
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        DELEGATE.startActivityForResult(Intent.createChooser(intent, "选择获取图片的方式"),
                RequestCodes.PICK_PHOTO);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.photodialog_btn_take){
            takePhoto();
        }else if (id == R.id.photodialog_btn_native){
            pickPhoto();
        }else if (id == R.id.photodialog_btn_cancel){
            DIALOG.cancel();
        }
    }
}






