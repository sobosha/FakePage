package com.diaco.fakepage.Main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.diaco.fakepage.ImageCompresor.IImageCompressTaskListener;
import com.diaco.fakepage.ImageCompresor.ImageCompressTask;
import com.diaco.fakepage.MainActivity;
import com.diaco.fakepage.R;
import com.diaco.fakepage.Setting.CustomClasses.CustomAdapter;
import com.diaco.fakepage.Setting.CustomClasses.CustomFragment;
import com.diaco.fakepage.Setting.IAnimationEnd;
import com.diaco.fakepage.Setting.ImageFilePath;
import com.diaco.fakepage.Setting.Setting;
import com.diaco.fakepage.Setting.mAnimation;
import com.diaco.fakepage.Setting.mLocalData;
import com.google.android.material.snackbar.Snackbar;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.skydoves.balloon.overlay.BalloonOverlayAnimation;
import com.skydoves.balloon.overlay.BalloonOverlayRect;
import com.skydoves.balloon.overlay.BalloonOverlayShape;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.hdodenhof.circleimageview.CircleImageView;

public class fragment_tweeter extends CustomFragment {

    CustomAdapter customAdapter,customAdapterBorn;
    ImageView profilePhoto,headerPhoto,one,two,three,four,add_imagetweets,back,minus_tweet;
    List<ListviewTweet> listTweet;
    List<relItemBorn> listBorn;
    ImageView menu, plus_tweet, tick, screenShot,gallery,camera,header,close,Done;
    RelativeLayout Dialog, mainRelative,
            rel_addtweet,rel_minustweet,editProfile,btn_screenShot;
    public RelativeLayout Dialog_CameraGallery,Dialog_camera;
    CircleImageView imageProfile;
    TextView add_tweet, Username, followers, following, nameTweet,tweeterid, idTweet, hourTweet
            ,bornTweeter,joinTweeter,textview_minus,Bio;
    boolean isDarkmode;
    SwitchCompat darkMode;
    String currentPhotoPath;
    Uri imageUri;
    ImageCompressTask imageCompressTask;
    String realPath;
    Bitmap bitmapSend;
    String command,commandTweet="";
    public Balloon balloon,ballooncameragallery;
    RecyclerView recBorn;
    boolean showDialog=false;
    public boolean showDialogCameraGallery=false;
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(1);
    @Override
    public int layout() {
        return R.layout.fragment_twiter;
    }

    @Override
    public void onCreateMyView() {
        instance=this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = MainActivity.getGlobal().getWindow();
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
            View view = MainActivity.getGlobal().getWindow().getDecorView();
            view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        recBorn=parent.findViewById(R.id.Recycler_Born);
        back=parent.findViewById(R.id.back);
        tweeterid=parent.findViewById(R.id.twiter_id);
        header=parent.findViewById(R.id.twiter_image_header);
        bornTweeter=parent.findViewById(R.id.tweeter_Born);
        joinTweeter=parent.findViewById(R.id.tweeter_Join);
        editProfile=parent.findViewById(R.id.btn_setting);
        menu = parent.findViewById(R.id.tweeter_menu);
        btn_screenShot=parent.findViewById(R.id.btn_screenshot);
        Dialog = parent.findViewById(R.id.tweeter_dialog);
        Bio=parent.findViewById(R.id.twiter_Bio);
        mainRelative = parent.findViewById(R.id.tweeter_main_relative);
        tick = parent.findViewById(R.id.tweeter_tick);
        Username = parent.findViewById(R.id.twiter_name);
        followers = parent.findViewById(R.id.number_followers);
        following = parent.findViewById(R.id.number_following);
        imageProfile = parent.findViewById(R.id.twiter_circle_image);
        nameTweet = parent.findViewById(R.id.name_tweets);
        idTweet = parent.findViewById(R.id.id_tweets);
        hourTweet = parent.findViewById(R.id.hour_tweets);
        listTweet = new ArrayList<>();
        listBorn=new ArrayList<>();
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
        for (int i = 0; i < listTweet.size(); i++){
            listTweet.get(i).setBodyColor("#707070");
            listTweet.get(i).setProfile(imageProfile.getDrawable());
            listTweet.get(i).setId(tweeterid.getText().toString());
            listTweet.get(i).setName(Username.getText().toString());
        }
        customAdapter = new CustomAdapter.RecyclerBuilder<ListviewTweet, RelitemPost>(getContext(), parent.findViewById(R.id.recycler_tweets), listTweet)
                .setView(() -> new RelitemPost(getContext(), 2))
                .setBind((position, list, rel, selectItem, customAdapter) -> rel.onStartTweet(list.get(position)))
                .orientation(RecyclerView.VERTICAL)
                .build();

        /*menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog.addView(new instagram_bottomsheet(getContext(), R.layout.instagram_bottom_sheet));
                showDialog=true;
                add_tweet = parent.findViewById(R.id.textview_add_post);
                add_tweet.setText("اضافه کردن توییت");
                Dialog.setVisibility(View.VISIBLE);
                Dialogtransparent = parent.findViewById(R.id.bottomsheet_Main_Relative);
                Dialogtransparent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog.removeAllViews();
                    }
                });
                bottomSheetSetting();
            }
        });*/

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Setting.OnAnimationEnd(mAnimation.PressClick(editProfile), () -> {
                    ProfileEdit();
                    v.clearAnimation();
                });*/

                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(editProfile, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        ProfileEdit();
                        v.clearAnimation();
                    }
                });

            }
        });

        btn_screenShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(btn_screenShot, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        if(!mLocalData.getUserInfo(MainActivity.getGlobal())) {
                            Dialog.removeAllViews();
                            showDialog=true;
                            Dialog.setVisibility(View.VISIBLE);
                            Dialog_pardakht pardakht=new Dialog_pardakht(getContext(),null,fragment_tweeter.this);
                            Dialog.addView(pardakht);
                            pardakht.pardakht(Dialog,btn_screenShot,editProfile);
                        }
                        else{
                            MainActivity.getGlobal().setVisibility(new Visibility() {
                                @Override
                                public void Allow() {
                                    btn_screenShot.setVisibility(View.VISIBLE);
                                    editProfile.setVisibility(View.VISIBLE);
                                    parent.findViewById(R.id.textview_screenshot).setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void Deny() {
                                    btn_screenShot.setVisibility(View.VISIBLE);
                                    editProfile.setVisibility(View.VISIBLE);
                                    parent.findViewById(R.id.textview_screenshot).setVisibility(View.VISIBLE);
                                }
                            });
                            editProfile.clearAnimation();
                            btn_screenShot.clearAnimation();
                            Dialog.removeAllViews();
                            btn_screenShot.setVisibility(View.GONE);
                            editProfile.setVisibility(View.GONE);
                            parent.findViewById(R.id.textview_screenshot).setVisibility(View.GONE);
                            if(btn_screenShot.getVisibility()!=View.VISIBLE && editProfile.getVisibility()!=View.VISIBLE) {
                                if (Build.VERSION.SDK_INT >= 23) {
                                    if (Setting.checkPermissionWrite()) {
                                        Setting.takeScreenshotWithoutWatermark(getContext(), getActivity(), "tweeter");
                                        btn_screenShot.setVisibility(View.VISIBLE);
                                        editProfile.setVisibility(View.VISIBLE);
                                        parent.findViewById(R.id.textview_screenshot).setVisibility(View.VISIBLE);
                                    }
                                    else {
                        /*if(Build.VERSION.SDK_INT >= 30){
                        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                        intent.addCategory("android.intent.category.DEFAULT");
                        intent.setData(Uri.parse(String.format("package:%s",getContext().getPackageName())));
                        startActivityForResult(intent, 2296);
                        }
                        else*/
                                        MainActivity.getGlobal().showPermission=true;
                                        ActivityCompat.requestPermissions(MainActivity.getGlobal(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
                                    }
                                } else {
                                    Setting.takeScreenshotWithoutWatermark(getContext(), getActivity(), "tweeter");
                                    btn_screenShot.setVisibility(View.VISIBLE);
                                    editProfile.setVisibility(View.VISIBLE);
                                    parent.findViewById(R.id.textview_screenshot).setVisibility(View.VISIBLE);
                                }
                            }
                        }





                    }
                });
                }
        });

    }


    private static fragment_tweeter instance;
    public static fragment_tweeter getInstance(Context context){
        if(instance==null){
            instance=new fragment_tweeter();
            return instance;
        }
        else return instance;
    }


    private void ProfileEdit() {
        Dialog.removeAllViews();
        Dialog.addView(new editDialog(getContext(),R.layout.dialog_tweeter_editprofile));
        showDialog=true;
        Point point=new Point();
        point.set(1000000,1000000);
        rel_addtweet=parent.findViewById(R.id.btn_addtweet);
        rel_minustweet=parent.findViewById(R.id.btn_minustweet);
        darkMode = parent.findViewById(R.id.Darkmode_switch);
        SwitchCompat blue_tick = parent.findViewById(R.id.blueTickSwitch);
        EditText Id,UsernameEdit,Born,Join,followersEdit,followingEdit,Bio_edittext,Website,Location;
        Dialog_CameraGallery=parent.findViewById(R.id.tweeter_dialog_camera_gallery);
        headerPhoto=parent.findViewById(R.id.tweeter_editHeader_imageview);
        profilePhoto=parent.findViewById(R.id.tweeter_imageview_dialog_editprofile);
        RelativeLayout editprofilePhoto,editheaderPhoto;
        editprofilePhoto=parent.findViewById(R.id.tweeter_rel_changepPofilePhoto);
        editheaderPhoto=parent.findViewById(R.id.rel_editHeaderImageview);
        Id=parent.findViewById(R.id.tweeter_edittext_id);
        UsernameEdit=parent.findViewById(R.id.tweeter_edittext_username);
        Born=parent.findViewById(R.id.tweeter_edittext_Born);
        Join=parent.findViewById(R.id.tweeter_edittext_Join);
        Website=parent.findViewById(R.id.tweeter_edittext_Website);
        Location=parent.findViewById(R.id.tweeter_edittext_Location);
        followersEdit=parent.findViewById(R.id.tweeter_edittext_followers);
        followingEdit=parent.findViewById(R.id.tweeter_edittext_following);
        Bio_edittext=parent.findViewById(R.id.tweeter_edittext_Bio);
        Bio_edittext.setText(Bio.getText());
        Id.setText(tweeterid.getText());
        UsernameEdit.setText(Username.getText());
        followersEdit.setText(followers.getText());
        followingEdit.setText(following.getText());
        header.setScaleType(ImageView.ScaleType.CENTER);
        headerPhoto.setImageDrawable(header.getDrawable());
        profilePhoto.setImageDrawable(imageProfile.getDrawable());
        Dialog.setVisibility(View.VISIBLE);
        close=parent.findViewById(R.id.tweeter_close_edit);
        Done=parent.findViewById(R.id.tweeter_blue_check_editProfile);
        for(int i=0;i<listBorn.size();i++){
            if(listBorn.get(i).image==R.drawable.ic_link_icon){
                Website.setText(listBorn.get(i).getContent());
            }
            else if(listBorn.get(i).image==R.drawable.ic_location)
                Location.setText(listBorn.get(i).getContent());
            else if(listBorn.get(i).image==R.drawable.ic_born)
                Born.setText(listBorn.get(i).getContent());
            else if(listBorn.get(i).image==R.drawable.ic_calendar_icon)
                Join.setText(listBorn.get(i).getContent());
        }
        if (isDarkmode) darkMode.setChecked(true);
        if (tweeter_tick) blue_tick.setChecked(true);
        blue_tick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(blue_tick.isChecked()){
                    tweeter_tick=true;
                    tick.setVisibility(View.VISIBLE);
                }
                else{
                    tweeter_tick=false;
                    tick.setVisibility(View.GONE);
                }
                Done.performClick();
                Done.setPressed(true);
            }
        });
        darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mainRelative.setBackgroundColor(Color.BLACK);
                    tick.setImageResource(R.drawable.tweeter_white_tick);
                    Username.setTextColor(Color.WHITE);
                    followers.setTextColor(Color.WHITE);
                    following.setTextColor(Color.WHITE);
                    imageProfile.setBorderColor(Color.BLACK);
//                    for(int i=0;i<listBorn.size();i++){
//                        if(listBorn.get(i).image!=R.drawable.ic_link_icon){
//                            listBorn.get(i).setColorContent("#ffffff");
//                        }
//                    }
                    //customAdapterBorn.notifyDataSetChanged();

                    for (int i = 0; i < listTweet.size(); i++) {
                        listTweet.get(i).setBodyColor("#FFFFFF");
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = MainActivity.getGlobal().getWindow();
                        window.setStatusBarColor(Color.parseColor("#000000"));
                        View view = MainActivity.getGlobal().getWindow().getDecorView();
                        view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }

                    customAdapter.notifyDataSetChanged();
                    isDarkmode = true;

                } else {
                    mainRelative.setBackgroundColor(Color.WHITE);
                    tick.setImageResource(R.drawable.twiter_tickblue);
                    Username.setTextColor(Color.BLACK);
                    followers.setTextColor(Color.BLACK);
                    following.setTextColor(Color.BLACK);
                    imageProfile.setBorderColor(Color.WHITE);
                    for (int i = 0; i < listTweet.size(); i++) {
                        listTweet.get(i).setBodyColor("#707070");
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = MainActivity.getGlobal().getWindow();
                        window.setStatusBarColor(Color.parseColor("#FFFFFF"));
                        View view = MainActivity.getGlobal().getWindow().getDecorView();
                        view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }
//                    for(int i=0;i<listBorn.size();i++){
//                        if(listBorn.get(i).image!=R.drawable.ic_link_icon){
//                            listBorn.get(i).setColorContent("#000000");
//                            customAdapterBorn.notifyDataSetChanged();
//                        }
//                    }

                    //customAdapter.notifyDataSetChanged();
                    isDarkmode = false;

                }
                Done.performClick();
                Done.setPressed(true);
            }
        });


        rel_minustweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(rel_minustweet, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        if(!listTweet.isEmpty()){
                            listTweet.get(listTweet.size()-1).getCustomview_multiImage().number_image=0;
                            listTweet.remove(listTweet.get(listTweet.size()-1));
                            customAdapter.notifyDataSetChanged();
                            Done.performClick();
                            Done.setPressed(true);
                        }
                    }
                });

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(close, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        hideSoftKeyboard(MainActivity.getGlobal());
                        Dialog.removeAllViews();
                        Dialog.setVisibility(View.GONE);
                        header.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        showDialog=false;
                    }
                });


            }
        });
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(Done, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        hideSoftKeyboard(MainActivity.getGlobal());
                        listBorn.clear();
                        tweeterid.setText(Id.getText().toString());
                        Username.setText(UsernameEdit.getText().toString());
                        bornTweeter.setText(Born.getText().toString());
                        joinTweeter.setText(Join.getText().toString());
                        followers.setText(followersEdit.getText().toString());
                        following.setText(followingEdit.getText().toString());
                        Bio.setText(Bio_edittext.getText().toString());
                        header.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        header.setImageDrawable(headerPhoto.getDrawable());
                        imageProfile.setImageDrawable(profilePhoto.getDrawable());
                        if(!Location.getText().toString().equals("")){
                            relItemBorn temp=new relItemBorn();
                            temp.image=R.drawable.ic_location;
                            temp.content=Location.getText().toString();
                            if(!isDarkmode)
                            temp.ColorContent="#687684";
                            else temp.ColorContent="#ffffff";
                            listBorn.add(temp);
                        }
                        if(!Website.getText().toString().equals("")){
                            relItemBorn temp=new relItemBorn();
                            temp.image=R.drawable.ic_link_icon;
                            temp.content=Website.getText().toString();
                            temp.ColorContent="#4c9eeb";
                            listBorn.add(temp);
                        }
                        if(!Born.getText().toString().equals("")){
                            relItemBorn temp=new relItemBorn();
                            temp.image=R.drawable.ic_born;
                            temp.content=Born.getText().toString();
                            if(!isDarkmode)
                                temp.ColorContent="#687684";
                            else temp.ColorContent="#ffffff";
                            listBorn.add(temp);
                        }
                        if(!Join.getText().toString().equals("")){
                            relItemBorn temp=new relItemBorn();
                            temp.image=R.drawable.ic_calendar_icon;
                            temp.content=Join.getText().toString();
                            if(!isDarkmode)
                                temp.ColorContent="#687684";
                            else temp.ColorContent="#ffffff";
                            listBorn.add(temp);
                        }
                        for(int i=0;i<listTweet.size();i++){
                            listTweet.get(i).setProfile(imageProfile.getDrawable());
                            listTweet.get(i).setId(tweeterid.getText().toString());
                            listTweet.get(i).setName(Username.getText().toString());
                        }
                        int temp;
                        if(listBorn.size()>=3)
                            temp=3;
                        else temp=2;
                        customAdapterBorn = new CustomAdapter.RecyclerBuilder<relItemBorn, BornAdapt>(getContext(), recBorn, listBorn)
                                .setView(() -> new BornAdapt(getContext()))
                                .setBind((position, list, rel, selectItem, customAdapter) -> rel.onBorn(list.get(position)))
                                .orientation(RecyclerView.HORIZONTAL)
                                .grid(temp)
                                .build();
                        customAdapter.notifyDataSetChanged();
                        Dialog.removeAllViews();
                        Dialog.setVisibility(View.GONE);
                        showDialog=false;
                    }
                });


            }
        });
        editheaderPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(MainActivity.getGlobal());
                BalloonOverlayShape balloonOverlayShape=BalloonOverlayRect.INSTANCE;
                ballooncameragallery=new Balloon.Builder(getContext())
                        .setLayout(R.layout.tooltip_cameragallery)
                        .setArrowSize(0)
                        .setArrowOrientation(ArrowOrientation.BOTTOM)
                        .setArrowPosition(0.0f)
                        .setMarginLeft((int)MainActivity.getGlobal().getResources().getDimension(R.dimen._50sdp))
                        .setMarginBottom(0)
                        .setCornerRadius(0)
                        .setBackgroundColor(Color.parseColor("#00000000"))
                        .setBalloonAnimation(BalloonAnimation.FADE)
                        .setIsVisibleOverlay(true) // sets the visibility of the overlay for highlighting an anchor.
                        .setOverlayColorResource(R.color.OverlyToolTip) // background color of the overlay using a color resource.
                        .setOverlayPadding(0)// sets a padding value of the overlay shape internally.
                        .setBalloonOverlayAnimation(BalloonOverlayAnimation.FADE) // default is fade.
                        .setDismissWhenOverlayClicked(false)
                        .setOverlayShape(balloonOverlayShape)
                        .setOverlayPosition(point)
                        .build();
                ballooncameragallery.show(editheaderPhoto);
                if (Build.VERSION.SDK_INT >= 23) {
                    if (!Setting.checkPermissionWrite())
                        ActivityCompat.requestPermissions(MainActivity.getGlobal(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);

                }
                camera=ballooncameragallery.getContentView().findViewById(R.id.camera);
                gallery=ballooncameragallery.getContentView().findViewById(R.id.gallery);
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        gallery.setType("image/*");
                        command="EditHeader";
                        mGetContent.launch(gallery);
                        ballooncameragallery.dismiss();
                    }
                });
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        command="HeaderCamera";
                        openCamera(command);
                        ballooncameragallery.dismiss();
                    }
                });

            }
        });
        rel_addtweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(rel_addtweet, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        hideSoftKeyboard(MainActivity.getGlobal());
                        BalloonOverlayShape balloonOverlayShape= BalloonOverlayRect.INSTANCE;
                        balloon=new Balloon.Builder(getContext())
                                .setLayout(R.layout.dialog_edit_tweets)
                                .setArrowSize(0)
                                .setArrowOrientation(ArrowOrientation.TOP)
                                .setArrowPosition(0.0f)
                                .setMarginLeft(22)
                                .setMarginBottom(20)
                                .setCornerRadius(0)
                                .setBackgroundColor(Color.parseColor("#00000000"))
                                .setBalloonAnimation(BalloonAnimation.FADE)
                                .setIsVisibleOverlay(true) // sets the visibility of the overlay for highlighting an anchor.
                                .setOverlayColorResource(R.color.OverlyToolTip) // background color of the overlay using a color resource.
                                .setOverlayPadding(0)// sets a padding value of the overlay shape internally.
                                .setBalloonOverlayAnimation(BalloonOverlayAnimation.FADE) // default is fade.
                                .setDismissWhenOverlayClicked(false)
                                .setOverlayShape(balloonOverlayShape)
                                .setOverlayPosition(point)
                                .build();
                        balloon.show(rel_addtweet);
                        balloon_addTweet(rel_addtweet);
                    }
                });

            }
        });
        editprofilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(MainActivity.getGlobal());
                BalloonOverlayShape balloonOverlayShape=BalloonOverlayRect.INSTANCE;
                ballooncameragallery=new Balloon.Builder(getContext())
                        .setLayout(R.layout.tooltip_cameragallery)
                        .setArrowSize(0)
                        .setArrowOrientation(ArrowOrientation.BOTTOM)
                        .setArrowPosition(0.0f)
                        .setMarginLeft((int)MainActivity.getGlobal().getResources().getDimension(R.dimen._15sdp))
                        .setMarginBottom(0)
                        .setCornerRadius(0)
                        .setBackgroundColor(Color.parseColor("#00000000"))
                        .setBalloonAnimation(BalloonAnimation.FADE)
                        .setIsVisibleOverlay(true) // sets the visibility of the overlay for highlighting an anchor.
                        .setOverlayColorResource(R.color.OverlyToolTip) // background color of the overlay using a color resource.
                        .setOverlayPadding(0)// sets a padding value of the overlay shape internally.
                        .setBalloonOverlayAnimation(BalloonOverlayAnimation.FADE) // default is fade.
                        .setDismissWhenOverlayClicked(false)
                        .setOverlayShape(balloonOverlayShape)
                        .setOverlayPosition(point)
                        .build();
                ballooncameragallery.show(editprofilePhoto);
                if (Build.VERSION.SDK_INT >= 23)
                    if (!Setting.checkPermissionWrite())
                        ActivityCompat.requestPermissions(MainActivity.getGlobal(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);

                camera=ballooncameragallery.getContentView().findViewById(R.id.camera);
                gallery=ballooncameragallery.getContentView().findViewById(R.id.gallery);
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        gallery.setType("image/*");
                        command="EditProfile";
                        mGetContent.launch(gallery);
                        ballooncameragallery.dismiss();
                        Dialog_CameraGallery.setVisibility(View.GONE);
                        Dialog_CameraGallery.removeAllViews();
                    }
                });
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        command="ProfileCamera";
                        openCamera(command);
                        ballooncameragallery.dismiss();
                        Dialog_CameraGallery.setVisibility(View.GONE);
                        Dialog_CameraGallery.removeAllViews();
                    }
                });

            }
        });

    }



    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult  result) {
                    showDialogCameraGallery=false;
                    if(result.getResultCode()==Activity.RESULT_OK && command.equals("EditHeader")){
                        try {
                            imageUri=result.getData().getData();
                            Cursor cursor = MediaStore.Images.Media.query(getActivity().getContentResolver(), imageUri, new String[]{MediaStore.Images.Media.DATA});

                            if (cursor != null && cursor.moveToFirst()) {
                                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                                //Create ImageCompressTask and execute with Executor.
                                imageCompressTask = new ImageCompressTask(getContext(), path, iImageCompressTaskListener);

                                mExecutorService.execute(imageCompressTask);
                            }
                        }
                        catch (Throwable e){
                            e.printStackTrace();
                        }
                    }
                    else if(result.getResultCode()==Activity.RESULT_OK && command.equals("HeaderCamera")){
                        try {
                            File file = new File(currentPhotoPath);
                            realPath = ImageFilePath.getPath(MainActivity.getGlobal(), Uri.fromFile(file));
                            imageCompressTask = new ImageCompressTask(getContext(), currentPhotoPath, iImageCompressTaskListener);
                            mExecutorService.execute(imageCompressTask);
                            Dialog_CameraGallery.setVisibility(View.GONE);
                        }
                        catch (Throwable e){
                            e.printStackTrace();
                        }
                    }
                    else if(result.getResultCode()==Activity.RESULT_OK && command.equals("ProfileCamera")){
                        try {
                            File file = new File(currentPhotoPath);
                            realPath = ImageFilePath.getPath(MainActivity.getGlobal(), Uri.fromFile(file));
                            imageCompressTask = new ImageCompressTask(getContext(), currentPhotoPath, iImageCompressTaskListener);
                            mExecutorService.execute(imageCompressTask);
                            Dialog_CameraGallery.setVisibility(View.GONE);
                        }
                        catch (Throwable e){
                            e.printStackTrace();
                        }
                    }
                    else if(result.getResultCode()==Activity.RESULT_OK && command.equals("EditProfile")){
                        try {
                            imageUri=result.getData().getData();
                            Cursor cursor = MediaStore.Images.Media.query(getActivity().getContentResolver(), imageUri, new String[]{MediaStore.Images.Media.DATA});

                            if (cursor != null && cursor.moveToFirst()) {
                                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                                //Create ImageCompressTask and execute with Executor.
                                imageCompressTask = new ImageCompressTask(getContext(), path, iImageCompressTaskListener);

                                mExecutorService.execute(imageCompressTask);
                            }
                        }
                        catch (Throwable e){
                            e.printStackTrace();
                        }
                    }

                }
            });
    boolean tweeter_tick=true;

    private void balloon_addTweet(View view){
        Point point=new Point();
        point.set(10000,10000);
        add_imagetweets=balloon.getContentView().findViewById(R.id.add_image_tweets);
        one=balloon.getContentView().findViewById(R.id.add_image_one);
        two=balloon.getContentView().findViewById(R.id.add_image_two);
        three=balloon.getContentView().findViewById(R.id.add_image_three);
        four=balloon.getContentView().findViewById(R.id.add_image_four);
        gallery=balloon.getContentView().findViewById(R.id.add_image_tweets_gallery);
        EditText hour,tweet;
        hour=balloon.getContentView().findViewById(R.id.Edittext_tweet_hour);
        tweet=balloon.getContentView().findViewById(R.id.Edittext_tweet_tweets);
        TextView add_tweet=balloon.getContentView().findViewById(R.id.add_tweet);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (!Setting.checkPermissionWrite())
                        ActivityCompat.requestPermissions(MainActivity.getGlobal(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
                    else {
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        gallery.setType("image/*");
                        command = "EditProfile";
                        commandTweet = "addImageTweet";
                        mGetContent.launch(gallery);
                    }
                }
                else {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    gallery.setType("image/*");
                    command = "EditProfile";
                    commandTweet = "addImageTweet";
                    mGetContent.launch(gallery);
                }
            }
        });
        add_imagetweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(add_imagetweets, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {

                        /*BalloonOverlayShape balloonOverlayShape=BalloonOverlayRect.INSTANCE;
                        ballooncameragallery=new Balloon.Builder(getContext())
                                .setLayout(R.layout.tooltip_cameragallery)
                                .setArrowSize(0)
                                .setArrowOrientation(ArrowOrientation.BOTTOM)
                                .setArrowPosition(0.0f)
                                .setMarginLeft((int)MainActivity.getGlobal().getResources().getDimension(R.dimen._15sdp))
                                .setMarginBottom(0)
                                .setCornerRadius(0)
                                .setBackgroundColor(Color.parseColor("#00000000"))
                                .setBalloonAnimation(BalloonAnimation.FADE)
                                .setIsVisibleOverlay(true) // sets the visibility of the overlay for highlighting an anchor.
                                .setOverlayColorResource(R.color.OverlyToolTip) // background color of the overlay using a color resource.
                                .setOverlayPadding(0)// sets a padding value of the overlay shape internally.
                                .setBalloonOverlayAnimation(BalloonOverlayAnimation.FADE) // default is fade.
                                .setDismissWhenOverlayClicked(false)
                                .setOverlayShape(balloonOverlayShape)
                                .setOverlayPosition(point)
                                .build();
                        balloon.dismiss();
                        ballooncameragallery.show(view);*/
                        if (Build.VERSION.SDK_INT >= 23) {
                            if (!Setting.checkPermissionWrite())
                                ActivityCompat.requestPermissions(MainActivity.getGlobal(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
                            else {
                                command = "ProfileCamera";
                                commandTweet = "addImageTweet";
                                openCamera(command);
                            }
                        }else {
                            command="ProfileCamera";
                            commandTweet="addImageTweet";
                            openCamera(command);
                        }

                        /*camera=balloon.getContentView().findViewById(R.id.add_image_tweets);
                        gallery=ballooncameragallery.getContentView().findViewById(R.id.add_image_tweets_gallery);
                        gallery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                command="EditProfile";
                                commandTweet="addImageTweet";
                                mGetContent.launch(gallery);
                                ballooncameragallery.dismiss();
                            }
                        });
                        camera.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                command="ProfileCamera";
                                commandTweet="addImageTweet";
                                openCamera(command);
                                ballooncameragallery.dismiss();
                            }
                        });*/
                    }
                });

            }
        });
        add_tweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(add_tweet, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        if (!hour.getText().toString().equals("")) {
                            ListviewTweet temp = new ListviewTweet();
                            temp.setTweet(tweet.getText().toString());
                            temp.setHour(". "+hour.getText().toString());
                            temp.setProfile(imageProfile.getDrawable());
                            temp.setId(tweeterid.getText().toString());
                            temp.setName(Username.getText().toString());
                            temp.setBodyColor("#707070");
                            if (one.getDrawable() != null) {
                                temp.setImageOne(one.getDrawable());
                                one.setImageDrawable(null);
                            }
                            if (two.getDrawable() != null) {
                                temp.setImageTwo(two.getDrawable());
                                two.setImageDrawable(null);
                            }
                            if (three.getDrawable() != null) {
                                temp.setImageThree(three.getDrawable());
                                three.setImageDrawable(null);
                            }
                            if (four.getDrawable() != null) {
                                temp.setImageFour(four.getDrawable());
                                four.setImageDrawable(null);
                            }
                            one.setImageDrawable(null);
                            two.setImageDrawable(null);
                            three.setImageDrawable(null);
                            four.setImageDrawable(null);
                            listTweet.add(temp);
                            if (isDarkmode) {
                                for (int i = 0; i < listTweet.size(); i++) {
                                    listTweet.get(i).setBodyColor("#FFFFFF");
                                }
                            }
                            balloon.dismiss();
                            Done.performClick();
                            Done.setPressed(true);
                        }
                        else
                            Snackbar.make(getContext(),MainActivity.getGlobal().getCurrentFragment().getView(),"باید ساعت وارد شود",Snackbar.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }


    public void openCamera(String type) {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePicture.resolveActivity(parent.getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                imageUri = FileProvider.getUriForFile(getContext(),
                        "com.diaco.fakepage",
                        photoFile);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                if (type.equals("HeaderCamera"))
                    mGetContent.launch(takePicture);
                else if (type.equals("ProfileCamera")) {
                    mGetContent.launch(takePicture);
                }
            }
        }
        Dialog.setVisibility(View.VISIBLE);

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + Setting.PersianToEnglish(timeStamp) + "_";
        File storageDir = MainActivity.getGlobal().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }



    private final IImageCompressTaskListener iImageCompressTaskListener = new IImageCompressTaskListener() {
        @Override
        public void onComplete(List<File> compressed) {
            MainActivity.getGlobal().hideMainDialogs();
            Dialog.setVisibility(View.VISIBLE);
            File file = compressed.get(0);
            bitmapSend = BitmapFactory.decodeFile(file.getAbsolutePath());
            //bitmapSend=getRoundedCornerBitmap(bitmapSend,20);
            if(command.equals("EditHeader") || command.equals("HeaderCamera")){
                if(commandTweet.equals("")){
                    imageUri = Uri.parse(file.getAbsolutePath());
                    headerPhoto.setImageURI(imageUri);}
                else if(commandTweet.equals("addImageTweet")){
                    if(four.getDrawable()==null){
                        four.setImageBitmap(bitmapSend);
                    }
                    else if(three.getDrawable()==null){
                        three.setImageBitmap(bitmapSend);
                    }
                    else if(two.getDrawable()==null){
                        two.setImageBitmap(bitmapSend);
                    }
                    else{
                        one.setImageBitmap(bitmapSend);
                    }
                    balloon.show(rel_addtweet);
                }
            }
            else if(command.equals("ProfileCamera") || command.equals("EditProfile")){
                if(commandTweet.equals("")){
                    profilePhoto.setImageURI(imageUri);}
                else if(commandTweet.equals("addImageTweet")){
                    if(four.getDrawable()==null){
                        four.setImageBitmap(bitmapSend);
                    }
                    else if(three.getDrawable()==null){
                        three.setImageBitmap(bitmapSend);
                    }
                    else if(two.getDrawable()==null){
                        two.setImageBitmap(bitmapSend);
                    }
                    else{
                        one.setImageBitmap(bitmapSend);
                    }
                    balloon.show(rel_addtweet);
                }
            }



        }

        @Override
        public void onError(Throwable error) {

        }
    };


    @Override
    public void mBackPressed() {
        super.mBackPressed();
        if(showDialogCameraGallery){
            if(Dialog_CameraGallery!=null) {
                Dialog_CameraGallery.setVisibility(View.GONE);
                Dialog_CameraGallery.removeAllViews();
            }
            if(Dialog_camera!=null) {
                Dialog_camera.removeAllViews();
                Dialog_camera.setVisibility(View.GONE);
            }

            showDialogCameraGallery=false;
        }
        else if(!showDialog){
            Dialog.setVisibility(View.VISIBLE);
            showDialog=true;
            Dialog.removeAllViews();
            Dialog.addView(new Dialog_Description(MainActivity.getGlobal(),this,null));
        }
        else if(showDialog){
            Dialog.removeAllViews();
            showDialog=false;
            header.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    public  void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    MainActivity.getGlobal().getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
