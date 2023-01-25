package com.diaco.fakepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.diaco.fakepage.Core.DeepLinks;
import com.diaco.fakepage.Setting.nValue;


public class NotifyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);



        nValue.getGlobal().setJoinModel(nValue.getGlobal().StartjoinModel);

        if (MainActivity.getGlobal() != null)
        {
                if (nValue.getGlobal().isUserInApp())
                new DeepLinks(nValue.getGlobal().getJoinModel()).onStart();
//            nValue.getGlobal().setJoinModel(null);
        }
        else
        {
            startActivity(new Intent(this,MainActivity.class));
            NotifyActivity.this.finish();
            return;
        }

        Intent openMainActivity= new Intent(this, MainActivity.class);
        startActivity(openMainActivity);
        NotifyActivity.this.finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
