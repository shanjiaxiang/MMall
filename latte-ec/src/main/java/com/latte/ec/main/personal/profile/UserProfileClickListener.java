package com.latte.ec.main.personal.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.late.core.fragments.LatteFragment;
import com.late.core.net.RestClient;
import com.late.core.net.callback.IError;
import com.late.core.net.callback.ISuccess;
import com.late.core.util.callback.CallbackManager;
import com.late.core.util.callback.CallbackType;
import com.late.core.util.callback.IGlobalCallback;
import com.late.core.util.log.LatteLogger;
import com.latte.ec.R;
import com.latte.ec.main.personal.list.ListBean;
import com.latte.ui.date.DateDialogUtil;

/**
 * Created by Administrator on 2018\11\22 0022.
 */

public class UserProfileClickListener extends SimpleClickListener {

    private final LatteFragment FRAGMENT;
    private String[] mGenders = new String[]{"男","女","保密"};

    public UserProfileClickListener(LatteFragment fragment) {
        FRAGMENT = fragment;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        final int id = bean.getmId();
        switch (id){
            case 1:
                //照相机或选择图片
                CallbackManager.getInstance().addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void executeCallback(Uri args) {
                        LatteLogger.d("ON_CROP", args);
                        final ImageView avatar = (ImageView) view.findViewById(R.id.img_arrow_avatar);
                        Glide.with(FRAGMENT)
                                .load(args)
                                .into(avatar);

                        //上传照片
                        RestClient.Builder()
                                .url(UploadConfig.UPLOAD_IMG)
                                .loader(FRAGMENT.getContext())
                                .file(args.getPath())
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        LatteLogger.d("ON_CROP_UPLOAD", response);
                                        final String path = JSON.parseObject(response).getJSONObject("result")
                                                .getString("path");
                                        //通知服务器更新信息
                                        RestClient.Builder()
                                                .url("user_profile.php")
                                                .params("avatar", path)
                                                .loader(FRAGMENT.getContext())
                                                .success(new ISuccess() {
                                                    @Override
                                                    public void onSuccess(String response) {
                                                        //获取更新后的用户信息，然后更新本地数据库
                                                        //没有本地数据的APP，每次打开APP都请求API，获取信息
                                                    }
                                                })
                                                .build()
                                                .post();
                                    }
                                })
                                .error(new IError() {
                                    @Override
                                    public void onError(int code, String msg) {
                                        LatteLogger.d("ON_CROP_UPLOAD", msg);
//                                        Toast.makeText(FRAGMENT.getContext(), "服务器地址错误", Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .build()
                                .upload();
                    }
                });
                FRAGMENT.startCameraWithCheck();
                break;
            case 2:
                //
                final LatteFragment nameFragment = bean.getmFragment();
                FRAGMENT.getSupportDelegate().start(nameFragment);
                break;
            case 3:
                getGenderDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(mGenders[which]);
                        dialogInterface.cancel();
                    }
                });
                break;
            case 4:
                final DateDialogUtil dateDialogUtil = new DateDialogUtil();
                dateDialogUtil.setmDateListener(new DateDialogUtil.IDateListener() {
                    @Override
                    public void onDateChange(String date) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(date);
                    }
                });
                dateDialogUtil.showDialog(FRAGMENT.getContext());
                break;
        }
    }

    private void getGenderDialog(DialogInterface.OnClickListener listener){
        final AlertDialog.Builder builder = new AlertDialog.Builder(FRAGMENT.getContext());
        builder.setSingleChoiceItems(mGenders,0, listener);
        builder.show();
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
