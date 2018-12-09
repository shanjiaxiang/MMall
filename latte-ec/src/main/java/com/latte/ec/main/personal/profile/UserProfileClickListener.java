package com.latte.ec.main.personal.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.late.core.fragments.LatteFragment;
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
