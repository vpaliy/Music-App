package com.vpaliy.melophile.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.google.gson.reflect.TypeToken;
import com.vpaliy.melophile.App;
import com.vpaliy.melophile.R;
import com.vpaliy.melophile.di.Config;
import com.vpaliy.melophile.ui.home.HomeActivity;
import com.vpaliy.melophile.ui.utils.BundleUtils;
import com.vpaliy.melophile.ui.utils.Constants;
import com.vpaliy.soundcloud.auth.SoundCloudAuth;
import com.vpaliy.soundcloud.model.Token;
import android.support.v7.preference.PreferenceManager;
import android.text.TextUtils;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import android.support.annotation.Nullable;
import butterknife.OnClick;

public class AuthActivity extends AppCompatActivity {

    private static final int REQUEST_CODE=10;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String jsonToken= sharedPreferences.getString(Constants.EXTRA_TOKEN,null);
        if(!TextUtils.isEmpty(jsonToken)){
            Token token=BundleUtils.convertFromJsonString(jsonToken,new TypeToken<Token>(){}.getType());
            if(token!=null){
                new Handler().postDelayed(()->welcome(token), 400);
                return;
            }
        }
        findViewById(R.id.log_in)
                .animate()
                .scaleX(1)
                .scaleY(1)
                .setStartDelay(300)
                .setDuration(300)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK){
                String string=data.getDataString();
                String code= Uri.parse(string).getQueryParameter("code");
                SoundCloudAuth.create(Config.CLIENT_ID,Config.CLIENT_SECRET_ID)
                        .addRedirectUri(Config.REDIRECT_URI)
                        .tokenWithAuthCode(code)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::saveToken);
            }
        }
    }

    @OnClick(R.id.log_in)
    public void login(){
        SoundCloudAuth.create(Config.CLIENT_ID,Config.CLIENT_SECRET_ID)
                .loginWithActivity(this,Config.REDIRECT_URI,REQUEST_CODE);
    }

    private void saveToken(Token token){
        if(token!=null){
            SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
            String jsonString=BundleUtils.convertToJsonString(token,new TypeToken<Token>(){}.getType());
            sharedPreferences.edit().putString(Constants.EXTRA_TOKEN,jsonString).apply();
            welcome(token);
            return;
        }
        throw new IllegalArgumentException("Token is null!");
    }

    private void welcome(Token token){
        App.appInstance().appendToken(token);
        Intent intent=new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
