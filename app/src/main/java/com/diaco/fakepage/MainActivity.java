package com.diaco.fakepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.diaco.fakepage.Core.IView;
import com.diaco.fakepage.Core.Presenter;
import com.diaco.fakepage.Core.bzResponce;
import com.diaco.fakepage.Main.Visibility;
import com.diaco.fakepage.Main.choice_app;
import com.diaco.fakepage.Main.fragment_instagram;
import com.diaco.fakepage.Main.fragment_tweeter;
import com.diaco.fakepage.Main.fragment_youtube;
import com.diaco.fakepage.Setting.CustomSnackBar;
import com.diaco.fakepage.Setting.Setting;
import com.diaco.fakepage.Setting.mAnimation;
import com.diaco.fakepage.Setting.mFragment;
import com.diaco.fakepage.Setting.mLocalData;
import com.diaco.fakepage.Setting.nValue;
import com.diaco.fakepage.util.IabHelper;
import com.diaco.fakepage.util.IabResult;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private static MainActivity global;
    public static MainActivity getGlobal() {
        return global;
    }
    public boolean showPermission=false;
    public String typetemp="";
    public MainActivity() {
        global = this;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);
        /*mLocalData.SetToken(this,"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6IjIiLCJleHBpcmUiOjE2MjcyODYxNzR9.wMT-8sNK1_B-8vP8MPeCQDhnzqvh0gcLAf3h1VpyasoV3FqAN7EhQ60nB6dK-9pFPJY5HqwIlgKknhbn3a5PHA");
        Presenter.get_global().OnCreate(getApplicationContext(),"https://tools.hooshejavandeh.com/","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6IjIiLCJleHBpcmUiOjE2MjcyODYxNzR9.wMT-8sNK1_B-8vP8MPeCQDhnzqvh0gcLAf3h1VpyasoV3FqAN7EhQ60nB6dK-9pFPJY5HqwIlgKknhbn3a5PHA");
        Presenter.get_global().GetAction(new IView<bzResponce>() {
            @Override
            public void SendRequest() {

            }

            @Override
            public void OnSucceed(bzResponce object) {
                Toast.makeText(MainActivity.this, "salam", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnError(String error, int statusCode) {
                Toast.makeText(MainActivity.this, "er", Toast.LENGTH_SHORT).show();
            }
        },"app","toolsID","",bzResponce.class);*/
        MainActivity.getGlobal().FinishFragStartFrag(new choice_app());
    }


    public mFragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(mFragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    private mFragment currentFragment;

    public void FinishFragStartFrag(mFragment newFragment) {
        Presenter.get_global().cancelReq();
        currentFragment = newFragment;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_show, R.anim.fade_hide);
        ft.replace(R.id.relMaster, newFragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }


    @Override
    public void onBackPressed() {
        currentFragment.mBackPressed();
    }


    public boolean isMainDialogShow = false;

    public void MainDiallog(String type) {
        ((RelativeLayout) findViewById(R.id.relMainDialogs)).removeAllViews();
        isMainDialogShow = true;
        findViewById(R.id.relMainDialogs).setVisibility(VISIBLE);
        findViewById(R.id.imgBlackMain).setVisibility(VISIBLE);
        switch (type) {
        }

        mAnimation.myTransToLeft(findViewById(R.id.relMainDialogs), 0, 500, 2, 0);
        findViewById(R.id.imgBlackMain).setOnClickListener(view -> {
            hideMainDialogs();
        });

    }

    public void hideMainDialogs() {
        isMainDialogShow = false;
        findViewById(R.id.relMainDialogs).setVisibility(View.GONE);
        findViewById(R.id.imgBlackMain).setVisibility(View.GONE);
        mAnimation.myTransToLeft(findViewById(R.id.relMainDialogs), 0, 500, 0, 2).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.relMainDialogs).clearAnimation();
                ((RelativeLayout) findViewById(R.id.relMainDialogs)).removeAllViews();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        findViewById(R.id.imgBlackMain).setOnClickListener(view -> hideMainDialogs());

    }


    boolean canShowSnack = true, showSnack;
    Handler handler;
    Runnable runnable;

    public void showSnackBar(String type, String desc, int duration) {
        if (canShowSnack) {
            showSnack = true;
            canShowSnack = false;
            findViewById(R.id.relSnackBar).setVisibility(VISIBLE);
            if (type.equals("accept") || type.equals("pending") || type.equals("reject") || type.equals("warning") || type.equals("normal")) {
                mAnimation.myTrans_ToTopSnack(findViewById(R.id.relSnackBar), 0, 500);
                ((RelativeLayout) findViewById(R.id.relSnackBar)).addView(new CustomSnackBar(this, type, desc));
                if (handler == null)
                    handler = new Handler(Looper.getMainLooper());
                runnable = () -> hideSnackBar(false);
                handler.postDelayed(runnable, duration);
            } else {
                this.type = desc;
                mAnimation.myTrans_ToBottom(findViewById(R.id.relSnackBar), 0, 1500);
                ((RelativeLayout) findViewById(R.id.relSnackBar)).addView(new CustomSnackBar(this, type, desc));
            }
        }

    }

    String type = "";

    public void hideSnackBar(boolean isTop) {
        showSnack = false;
        if (isTop) {
            mAnimation.myTrans_ToBottomBAck(findViewById(R.id.relSnackBar), 0, 1000).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ((RelativeLayout) findViewById(R.id.relSnackBar)).removeAllViews();
                    findViewById(R.id.relSnackBar).setVisibility(View.GONE);
                    findViewById(R.id.relSnackBar).clearAnimation();
                    if (handler != null)
                        handler.removeCallbacks(runnable);
                    canShowSnack = true;
                    if (type.length() > 0) {
                        MainDiallog(type);
                        type = "";
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        } else {
            mAnimation.myTrans_ToBottom(findViewById(R.id.relSnackBar), 0, 500, 0.15f).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ((RelativeLayout) findViewById(R.id.relSnackBar)).removeAllViews();
                    findViewById(R.id.relSnackBar).setVisibility(View.GONE);
                    findViewById(R.id.relSnackBar).clearAnimation();
                    if (handler != null)
                        handler.removeCallbacks(runnable);
                    canShowSnack = true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    showPermission=false;
                    Setting.takeScreenshotWithWatermark(getApplicationContext(),this,"instagram");
                    visibility.Allow();
                    //Toast.makeText(getApplicationContext(), "ذخیره شد", Toast.LENGTH_SHORT).show();
                    MainActivity.getGlobal().showSnackBar("accept","در صفحه اصلی قسمت اسکرین شات ها ذخیره شد",1500);
                }  else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    showPermission=false;
                    visibility.Deny();
                    showSnackBar("warning"  , "برای ادامه کار به دسترسی نیاز داریم" , 1000);
                }
                return;
            case 2:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    fragment_instagram.getInstance(getApplicationContext()).openGallery(Integer.parseInt(typetemp));
                }  else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    showSnackBar("warning"  , "برای ادامه کار به دسترسی نیاز داریم" , 1000);
                }
                return;

            case 10:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.

                }  else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    if(fragment_tweeter.getInstance(getApplicationContext()).balloon!=null){
                        fragment_tweeter.getInstance(getApplicationContext()).balloon.dismiss();
                    }
                    showSnackBar("warning"  , "برای ادامه کار به دسترسی نیاز داریم" , 1000);
                }
                return;
            case 11:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    showPermission=false;
                    Setting.takeScreenshotWithWatermark(getApplicationContext(), this,"tweeter");
                    visibility.Allow();
                }  else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    showPermission=false;
                    visibility.Deny();
                    showSnackBar("warning"  , "برای ادامه کار به دسترسی نیاز داریم" , 1000);
                }
                return;
        }
    }
    Visibility visibility;
    public void setVisibility(Visibility visibility){
        this.visibility=visibility;
    }

    boolean mIsMarketStore = false;

    public boolean ismIsMarketStore() {
        return mIsMarketStore;
    }

    IabHelper mHelper;

    public IabHelper getmHelper() {
        return mHelper;
    }

    public void CheckMarketStore() {

        if (nValue.marketModel.equals("myket")) {
            mHelper = new IabHelper(this, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkCRFmXDzm36v2sWMA80Vku/TcsbEUEF7NPZQ/KTN7uAF8D4loUqaxT8WFW9eQnf9wRRXfbg6o1qj04euvRzKmbJrS4ANRzdvTwzvao6T8tCvCiS/y0/qR77uPOzGPJcz6if4fgDlzCRcT2FmgnrGKNmD00NsTZ4CAInVTY5BoZQIDAQAB");
        } else if (nValue.marketModel.equals("bazar")) {
            mHelper = new IabHelper(this, "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwC08X0roGTf+9yC/zaPsP7JRaV2vnDpD9TQ0/6nc2frsT1x8G0G+jeC9m2Heu4BMNvtr0xvf4JJpLyXCcWBA994ViYXx2cfmzj/8/pYXX0T9IExHipyIcjA2zV/vRgivOxx+lAzLZKcveGf+ptNA24bmazgSz1uYsPhs9vgm8T25cMxgvT9OO+Ltb4ZEMmJQoaaOM75NsBuIRN361jeHdQjfRKgI3c9KN0nOJkLj9sCAwEAAQ==");
        }
        try {

            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) {
                    mIsMarketStore = result.isSuccess();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}