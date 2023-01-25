package com.diaco.fakepage.Main;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

import com.diaco.fakepage.MainActivity;
import com.diaco.fakepage.R;
import com.diaco.fakepage.Setting.CustomClasses.CustomFragment;
import com.diaco.fakepage.Setting.IAnimationEnd;
import com.diaco.fakepage.Setting.Setting;
import com.diaco.fakepage.Setting.mAnimation;
import com.google.android.material.tabs.TabLayout;

public class fragment_screenshottab extends CustomFragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    public int layout() {
        return R.layout.fragment_screenshottablayout;
    }

    static fragment_screenshottab instance;

    @Override
    public void onCreateMyView() {
        RelativeLayout back=parent.findViewById(R.id.relBackScreenshot);
        instance=this;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(back, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        MainActivity.getGlobal().FinishFragStartFrag(new choice_app());
                    }
                });

            }
        });
        tabLayout=parent.findViewById(R.id.tablayout);
        viewPager=parent.findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        fragment_screenshot insta=new fragment_screenshot();
        viewPagerAdapter.addFragment(new fragment_screenshot(),"توییتر");
        insta.setType("instagram");
        viewPagerAdapter.addFragment(insta,"اینستاگرام");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void mBackPressed() {
        super.mBackPressed();
        MainActivity.getGlobal().FinishFragStartFrag(new choice_app());
    }

    public static fragment_screenshottab getInstance(Context context){
        return instance;
    }
}
