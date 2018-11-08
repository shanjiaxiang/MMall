package com.latte.ec.sign;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.late.core.app.AccountManager;
import com.latte.ec.database.DatabaseManager;
import com.latte.ec.database.UserProfile;

/**
 * Created by Administrator on 2018\11\8 0008.
 */

public class SignHandler {


    public static void onSignIn(String response, ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
        //DatabaseManager.getInstance().getDao().insert(profile);

        //已经注册并登陆成功了
        AccountManager.setSignState(true);
        signListener.onSignInSuccess();
    }

    public static void onSignUp(String response, ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
        //DatabaseManager.getInstance().getDao().insert(profile);

        //已经注册并登陆成功了
        AccountManager.setSignState(true);
        signListener.onSignUpSuccess();
    }
}
