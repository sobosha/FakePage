package com.diaco.fakepage.Main;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diaco.fakepage.MainActivity;
import com.diaco.fakepage.R;
import com.diaco.fakepage.Setting.CustomClasses.CustomRel;

public class Dialog_Description extends CustomRel {
    TextView Exit,ScreenShot;
    fragment_tweeter tweeter;
    fragment_instagram instagram;
    RelativeLayout Back;
    public Dialog_Description(Context context,fragment_tweeter fr_tweeter,fragment_instagram fr_insta) {
        super(context, R.layout.dialog_exit_screen);
        tweeter=fr_tweeter;
        instagram=fr_insta;
        Back=findViewById(R.id.rel_ExitDialog);
        Back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getGlobal().getCurrentFragment().mBackPressed();
            }
        });
        Description();
    }

    private void Description() {
        Exit=findViewById(R.id.btn_Exit);
        ScreenShot=findViewById(R.id.btn_screenshot_withwatermark);
        Exit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getGlobal().FinishFragStartFrag(new choice_app());
            }
        });
        ScreenShot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(instagram!=null){
                    fragment_instagram.getInstance(MainActivity.getGlobal()).takeScreen.performClick();
                }
                if(tweeter!=null){
                    fragment_tweeter.getInstance(MainActivity.getGlobal()).btn_screenShot.performClick();
                }
            }
        });
    }
}
