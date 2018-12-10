package com.late.core.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.late.core.ui.camera.CameraImageBean;
import com.late.core.ui.camera.LatteCamera;
import com.late.core.ui.camera.RequestCodes;
import com.late.core.util.callback.CallbackManager;
import com.late.core.util.callback.CallbackType;
import com.late.core.util.callback.IGlobalCallback;
import com.yalantis.ucrop.UCrop;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public abstract class PermissionFragment extends BaseFragment {

    //真正调用方法
    public void startCameraWithCheck() {
        PermissionFragmentPermissionsDispatcher.showCameraWithPermissionCheck(this);
        getWritePermission();
    }


    @NeedsPermission(Manifest.permission.CAMERA)
    void showCamera() {
        LatteCamera.start(this);
    }

    public void getWritePermission() {
        PermissionFragmentPermissionsDispatcher.getWriteExStorageWithPermissionCheck(this);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void getWriteExStorage() {
    }

    private void showRationaleDialog(final PermissionRequest request) {
        if (getContext() != null) {
            new AlertDialog.Builder(getContext())
                    .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            request.proceed();
                        }
                    })
                    .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            request.cancel();
                        }
                    })
                    .setCancelable(false)
                    .setMessage("权限管理")
                    .show();
        }
    }


    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(final PermissionRequest request) {
        showRationaleDialog(request);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void showDeniedForCamera() {
        Toast.makeText(getContext(), "不允许拍照", Toast.LENGTH_LONG).show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void showNeverAskForCamera() {
        Toast.makeText(getContext(), "永久拒绝权限", Toast.LENGTH_LONG).show();
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TAKE_PHOTO:
                    final Uri resultUri = CameraImageBean.getInstance().getPath();
                    UCrop.of(resultUri, resultUri)
                            .withMaxResultSize(400, 400)
                            .start(getContext(), this);

                    break;
                case RequestCodes.PICK_PHOTO:
                    if (data != null) {
                        final Uri pickPath = data.getData();
                        //从相册选择后需要有个路径存放剪裁过的图片
                        final String pickCrop = LatteCamera.createCropFile().getPath();
                        UCrop.of(pickPath, Uri.parse(pickCrop))
                                .withMaxResultSize(400, 400)
                                .start(getContext(), this);
                    }

                    break;
                case RequestCodes.CROP_PHOTO:
                    final Uri cropUri = UCrop.getOutput(data);
                    //拿到剪裁后的数据进行处理
                    @SuppressWarnings("unchecked")
                    final IGlobalCallback<Uri> callback = CallbackManager.getInstance()
                            .getCallback(CallbackType.ON_CROP);
                    if (callback != null) {
                        callback.executeCallback(cropUri);
                    }
                    break;
                case RequestCodes.CROP_ERROR:
                    Toast.makeText(getContext(), "剪裁出错", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}