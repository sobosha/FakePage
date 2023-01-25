package com.diaco.fakepage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.diaco.fakepage.Setting.mLocalData;


public class ParamsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_params);
        if (getIntent().getData().getQueryParameter("status") != null) {
            if (getIntent().getData().getQueryParameter("status").equals("true")) {
                try {

                    if (getIntent().getData().getQueryParameter("type") != null) {
                        if (getIntent().getData().getQueryParameter("type").equals("fake")) {
                            mLocalData.setUserInfo(MainActivity.getGlobal(),true);
                            MainActivity.getGlobal().showSnackBar("accept","پرداخت با موفقیت انجام شد",1000);

                            /*new RequestManager(getApplicationContext()).setUserProperties();
                            MainActivity.getGlobal().ShowMessageBox(new RelMessageBox(MainActivity.getGlobal()).ExitButtonWithClick("success", "خرید " + getIntent().getData().getQueryParameter("quantity") + "  قلب با موفقیت انجام شد \uD83C\uDF38\uD83D\uDE00", view -> {
                                mAnimation.PressClick(view);
                                MainActivity.getGlobal().HideMessageBox();
                            }).setTextBtnOk("باشه"));
                            //*/
                            /*if (nValue.getGlobal().isBuyPackage())
                                ((FragMission) MainActivity.getGlobal().getCurrentFragment()).onSuccedBuy();
                            else if (nValue.getGlobal().isBuyArticle()) {
                                nValue.getGlobal().setBuyArticle(false);
                                ((FragListArticleWithPDF) MainActivity.getGlobal().getCurrentFragment()).update();
                            } else {
                                *//*MainActivity.getGlobal().HideMessageBox();
                                MainActivity.getGlobal().hideMainDialogs();*//*
                            }*/
                        }
                    } else {
                        MainActivity.getGlobal().showSnackBar("reject","پرداخت ناموفق بود",1000);

                        /*MainActivity.getGlobal().ShowMessageBox(new RelMessageBox(MainActivity.getGlobal()).ExitButtonWithClick("error", " خریدت " + "انجام نشد ☹️\uD83D\uDC94", view -> {
                            mAnimation.PressClick(view);
                            MainActivity.getGlobal().HideMessageBox();
                        }).setTextBtnOk("متوجه شدم"));*/
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                /*MainActivity.getGlobal().ShowMessageBox(new RelMessageBox(MainActivity.getGlobal()).ExitButtonWithClick("error", " خریدت " + "انجام نشد ☹️\uD83D\uDC94", view -> {
                    mAnimation.PressClick(view);
                    MainActivity.getGlobal().HideMessageBox();
                }).setTextBtnOk("متوجه شدم"));*/
                MainActivity.getGlobal().showSnackBar("reject","پرداخت ناموفق بود",1000);

            }

            Intent openMainActivity = new Intent(this, MainActivity.class);
            startActivity(openMainActivity);
        }
    }


}
