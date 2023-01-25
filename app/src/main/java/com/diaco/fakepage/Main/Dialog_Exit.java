package com.diaco.fakepage.Main;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diaco.fakepage.MainActivity;
import com.diaco.fakepage.R;
import com.diaco.fakepage.Setting.CustomClasses.CustomRel;

public class Dialog_Exit extends CustomRel {
    TextView Exit,Cancle;
    public Dialog_Exit(Context context, RelativeLayout Dialog) {
        super(context, R.layout.dialog_exit);
        Exit=findViewById(R.id.btn_Exit);
        Cancle=findViewById(R.id.btn_cancle);
        Exit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getGlobal().finish();
            }
        });
        Cancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getGlobal().getCurrentFragment().mBackPressed();
            }
        });
    }
}
