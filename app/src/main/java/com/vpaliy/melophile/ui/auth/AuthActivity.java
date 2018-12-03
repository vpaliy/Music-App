package com.vpaliy.melophile.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.analytics.FirebaseAnalytics;
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

import android.support.annotation.Nullable;

import butterknife.OnClick;

public class AuthActivity extends AppCompatActivity {

  private static final int REQUEST_CODE = 10;

  private FirebaseAnalytics firebaseAnalytics;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auth);
    ButterKnife.bind(this);
    firebaseAnalytics = FirebaseAnalytics.getInstance(this);
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    String jsonToken = sharedPreferences.getString(Constants.EXTRA_TOKEN, null);
    if (!TextUtils.isEmpty(jsonToken)) {
      Bundle bundle = new Bundle();
      bundle.putString(FirebaseAnalytics.Param.ITEM_ID, jsonToken);
      bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "access token json");
      firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
      Token token = BundleUtils.convertFromJsonString(jsonToken, new TypeToken<Token>() {
      }.getType());
      if (token != null) {
        welcome(token);
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
    if (requestCode == REQUEST_CODE) {
      if (resultCode == RESULT_OK) {
        findViewById(R.id.log_in)
                .animate()
                .scaleX(0)
                .scaleY(0)
                .setDuration(300)
                .start();
        String string = data.getDataString();
        String code = Uri.parse(string).getQueryParameter("code");
        SoundCloudAuth.create(Config.CLIENT_ID, Config.CLIENT_SECRET_ID)
                .addRedirectUri(Config.REDIRECT_URI)
                .tokenWithAuthCode(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::saveToken);
      }
    }
  }

  @OnClick(R.id.log_in)
  public void login() {
    SoundCloudAuth.create(Config.CLIENT_ID, Config.CLIENT_SECRET_ID)
            .loginWithActivity(this, Config.REDIRECT_URI, REQUEST_CODE);
  }

  @OnClick(R.id.invite)
  public void invite() {
    Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
            .setMessage(getString(R.string.invite_message))
            .setDeepLink(Uri.parse("/link"))
            .build();
    startActivityForResult(intent, 1);
  }

  private void saveToken(Token token) {
    if (token != null) {
      Bundle bundle = new Bundle();
      bundle.putString(FirebaseAnalytics.Param.ITEM_ID, token.access);
      bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "access code");
      firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
      SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
      String jsonString = BundleUtils.convertToJsonString(token, new TypeToken<Token>() {
      }.getType());
      sharedPreferences.edit().putString(Constants.EXTRA_TOKEN, jsonString).apply();
      welcome(token);
      return;
    }
    throw new IllegalArgumentException("Token is null!");
  }

  private void welcome(Token token) {
    App.appInstance().appendToken(token);
    Intent intent = new Intent(this, HomeActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
  }
}
