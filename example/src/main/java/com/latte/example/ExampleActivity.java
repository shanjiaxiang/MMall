package com.latte.example;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.late.core.activities.ProxyActivity;
import com.late.core.app.Latte;
import com.late.core.fragments.LatteFragment;
import com.late.core.ui.launcher.ILauncherListener;
import com.late.core.ui.launcher.OnLauncherFinishTag;
import com.latte.ec.launcher.LauncherFragment;
import com.latte.ec.launcher.LauncherScrollFragment;
import com.latte.ec.main.EcBottomFragment;
import com.latte.ec.sign.ISignListener;
import com.latte.ec.sign.SignInFragment;
import com.latte.ec.sign.SignUpFragment;

import qiu.niorgai.StatusBarCompat;


public class ExampleActivity extends ProxyActivity implements
        ISignListener,
        ILauncherListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Latte.getConfigurator().withWeChatActivity(this);
        StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    public LatteFragment setRootDelegate() {
        return new LauncherFragment();
    }


    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
    }


    //launcher完成后回调
    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag){
            case SIGNED:
                Toast.makeText(this, "启动结束，用户已登录", Toast.LENGTH_SHORT).show();
                getSupportDelegate().startWithPop(new EcBottomFragment());
                break;
            case NOT_SIGNED:
                Toast.makeText(this, "启动结束，用户未登录", Toast.LENGTH_SHORT).show();
                getSupportDelegate().startWithPop(new SignInFragment());
                break;
            default:
                break;
        }
    }
}
