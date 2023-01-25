package com.diaco.fakepage.Main;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diaco.fakepage.Core.BuyResult;
import com.diaco.fakepage.Core.DataModelResponse;
import com.diaco.fakepage.Core.IView;
import com.diaco.fakepage.Core.Presenter;
import com.diaco.fakepage.MainActivity;
import com.diaco.fakepage.R;
import com.diaco.fakepage.Setting.CustomClasses.CustomFragment;
import com.diaco.fakepage.Setting.IAnimationEnd;
import com.diaco.fakepage.Setting.Setting;
import com.diaco.fakepage.Setting.mAnimation;
import com.diaco.fakepage.Setting.mLocalData;

public class choice_app extends CustomFragment {
    boolean showDialog=false;
    RelativeLayout Dialog;
    @Override
    public int layout() {
        return R.layout.fragment_choice_app;
    }

    @Override
    public void onCreateMyView() {
        RelativeLayout twitter,instagram;
        Window window=MainActivity.getGlobal().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(Color.parseColor("#ffffff"));
            window.setStatusBarColor(Color.parseColor("#ffffff"));
            View view = MainActivity.getGlobal().getWindow().getDecorView();
            view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        Dialog=parent.findViewById(R.id.DialogChoiceApp);
        twitter=parent.findViewById(R.id.twitter);
        instagram=parent.findViewById(R.id.instagram);
        RelativeLayout btn_screenShot=parent.findViewById(R.id.btn_screenshot);
        btn_screenShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(btn_screenShot, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        MainActivity.getGlobal().FinishFragStartFrag(new fragment_screenshottab());
                    }
                });

            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*final Animation myAnim = AnimationUtils.loadAnimation(getContext(),R.anim.press_click);
                twitter.startAnimation(myAnim);*/
                //mAnimation.PressClick(twitter);
                //twitter.startAnimation(buttonClick);
                //mAnimation.townScaleMiddleCustom(twitter,1,1,0.9f,0.9f,0,100);
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(twitter, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        MainActivity.getGlobal().FinishFragStartFrag(new fragment_tweeter());
                    }
                });

            }
        });
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(instagram, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        MainActivity.getGlobal().FinishFragStartFrag(new fragment_instagram());
                    }
                });

            }
        });
        if(Setting.isNetworkConnect() && !mLocalData.getToken(getContext()).equals("")){
            Presenter.get_global().OnCreate(MainActivity.getGlobal(), "https://tools.hooshejavandeh.com/", "");
            Presenter.get_global().GetAction(new IView<BuyResult>() {
                @Override
                public void SendRequest() {

                }

                @Override
                public void OnSucceed(BuyResult object) {
                    if(object.getSuccess().equals("false")){
                        mLocalData.setUserInfo(MainActivity.getGlobal(),false);
                    }
                    //Toast.makeText(MainActivity.getGlobal(), mLocalData.getUserInfo(MainActivity.getGlobal())+"", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnError(String error, int statusCode) {

                }
            }, "users", "getBuy", "fake", BuyResult.class);
        }
    }

    @Override
    public void mBackPressed() {
        super.mBackPressed();
        if(!showDialog){
            showDialog=true;
            Dialog.setVisibility(View.VISIBLE);
            Dialog.removeAllViews();
            Dialog.addView(new Dialog_Exit(MainActivity.getGlobal(),Dialog));
        }
        else{
            Dialog.setVisibility(View.GONE);
            Dialog.removeAllViews();
            showDialog=false;
        }
    }
}
