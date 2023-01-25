package com.diaco.fakepage.Main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.diaco.fakepage.Setting.ThreadManager;
import com.diaco.fakepage.Setting.mAnimation;
import com.diaco.fakepage.Setting.mLocalData;
import com.google.android.material.snackbar.Snackbar;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.skydoves.balloon.OnBalloonClickListener;
import com.skydoves.balloon.OnBalloonOutsideTouchListener;
import com.skydoves.balloon.overlay.BalloonOverlayAnimation;
import com.skydoves.balloon.overlay.BalloonOverlayCircle;
import com.skydoves.balloon.overlay.BalloonOverlayOval;
import com.skydoves.balloon.overlay.BalloonOverlayRect;
import com.skydoves.balloon.overlay.BalloonOverlayShape;


import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.hdodenhof.circleimageview.CircleImageView;

public class fragment_instagram extends CustomFragment  {
    Uri imageUri;
    View highlightView;
    EditText name_highlights;
    public CustomAdapter customAdapter;
    CustomAdapter customAdapterPost,customAdapterHighlight;
    Balloon balloon,balloontemp,ballooncameragallery;
    List<ImageData> imageData;
    CircleImageView image_editprofile,profile,profilebottom,image_add_highlight,plus_highlights;
    RelativeLayout dialog_camera_gallery,Dialog,Main,post_tab,rel_addPost,
            rel_addHighlights,rel_minusPost,rel_minushighlights,setting,takeScreen;
    public List<ListViewPost> listPost,listPreviewPost;
    List<ListHighlights> highlights,listPreviewHighlight;
    TextView text_Name,text_Username,text_Bio,text_Website,changeProfilePhoto,editProfile,
            number_post,number_following,number_followers,text_post,text_following,text_followers,
            textview_highlights,textView_keephighlights,new_highlights,text_highlight_1,text_highlight_2
            ,text_highlight_3,firstPost,DialogAddPost;
    ImageView ok,menu,plus,highlight_1,highlight_2,highlight_3,blue_tick,igtv,location,location_2,down,plus_post,lock,home,search,like
            ,addpost,screenshot,minus_post,add_highlight,minus_highlight,post,post2;
    public ImageView delete_highlight_1,delete_highlight_2,delete_highlight_3,delete_highlight_4;
    LinearLayout bottommenu,headerpost_menu,headerpost_menu2;
    String type_post;
    ImageCompressTask imageCompressTask;
    String realPath;
    String currentPhotoPath;
    Bitmap bitmapSend ;
    RecyclerView RecListEditPost,RecListEditHighlight;
    public static boolean showDialog=false;
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(1);
    public boolean Screen=false;

    int postadd=0;
    boolean Darkmode;



    @Override
    public int layout() {
        return R.layout.fragment_instagram;
    }

    @Override
    public void onCreateMyView() {
        instance=this;
        Window window=MainActivity.getGlobal().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(Color.parseColor("#FFFFFF"));
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
            View view = MainActivity.getGlobal().getWindow().getDecorView();
            view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setting=parent.findViewById(R.id.btn_setting);
        takeScreen=parent.findViewById(R.id.btn_screenshot);
        headerpost_menu2=parent.findViewById(R.id.linear_header_post_2);
        headerpost_menu=parent.findViewById(R.id.linear_header_post);
        Dialog=parent.findViewById(R.id.instagram_dialog);
        Main=parent.findViewById(R.id.rel_MainInstagram);
        plus=parent.findViewById(R.id.instagram_plus);
        bottommenu=parent.findViewById(R.id.linear_bottommenu);
        home=parent.findViewById(R.id.instagram_home);
        addpost=parent.findViewById(R.id.instagram_addpost);
        profilebottom=parent.findViewById(R.id.instagram_profile_bottommenu);
        search=parent.findViewById(R.id.instagram_search);
        like=parent.findViewById(R.id.instagram_like);
        post=parent.findViewById(R.id.post);
        post2=parent.findViewById(R.id.post2);
        post_tab=parent.findViewById(R.id.relative_post_tab);
        textview_highlights=parent.findViewById(R.id.textview_highlightsBold);
        textView_keephighlights=parent.findViewById(R.id.text_highlightscaption);
        text_Name=parent.findViewById(R.id.textview_name);
        text_Username=parent.findViewById(R.id.textview_Username);
        blue_tick=parent.findViewById(R.id.instagram_blue_tick);
        text_Bio=parent.findViewById(R.id.textview_bio);
        text_Website=parent.findViewById(R.id.textview_website);
        menu=parent.findViewById(R.id.menu);
        number_followers=parent.findViewById(R.id.number_followers);
        number_following=parent.findViewById(R.id.number_following);
        number_post=parent.findViewById(R.id.number_post);
        text_followers=parent.findViewById(R.id.textview_followers);
        text_following=parent.findViewById(R.id.textview_following);
        text_post=parent.findViewById(R.id.textview_post);
        editProfile=parent.findViewById(R.id.textview_editProfile);
        plus_highlights=parent.findViewById(R.id.plus_highlights);
        highlight_1=parent.findViewById(R.id.highlight_1);
        highlight_2=parent.findViewById(R.id.highlight_2);
        highlight_3=parent.findViewById(R.id.highlight_3);
        text_highlight_1=parent.findViewById(R.id.text_highlights_1);
        text_highlight_2=parent.findViewById(R.id.text_highlights_2);
        text_highlight_3=parent.findViewById(R.id.text_highlights_3);
        new_highlights=parent.findViewById(R.id.text_new_highlights);
        down=parent.findViewById(R.id.instgram_down);
        igtv=parent.findViewById(R.id.igtv);
        location=parent.findViewById(R.id.location);
        location_2=parent.findViewById(R.id.location_2);
        lock=parent.findViewById(R.id.lock);
        delete_highlight_1=parent.findViewById(R.id.delete_highlight_0);
        delete_highlight_2=parent.findViewById(R.id.delete_highlight_1);
        delete_highlight_3=parent.findViewById(R.id.delete_highlight_2);
        delete_highlight_4=parent.findViewById(R.id.delete_highlight_3);
        highlights=new ArrayList<>();
        listPreviewPost=new ArrayList<>();
        listPreviewHighlight=new ArrayList<>();
        noPost();
        setHighlights();
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(setting, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        editProfileDialog();
                    }
                });

            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(plus, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        editProfileDialog();
                    }
                });
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(menu, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        editProfileDialog();
                    }
                });
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(editProfile, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        editProfileDialog();
                    }
                });
            }
        });
        listPost=new ArrayList<>();
        //listPost.add(new ListViewPost());
        //listPost.add(new ListViewPost());
        //listPost.add(new ListViewPost());
        //listPost.add(new ListViewPost());
        //listPost.add(new ListViewPost());
        //listPost.add(new ListViewPost());
        //listPost.add(new ListViewPost());
        //listPost.add(new ListViewPost());
        //listPost.add(new ListViewPost());
        customAdapter = new CustomAdapter.RecyclerBuilder<ListViewPost, RelitemPost>(getContext(), parent.findViewById(R.id.recycler_post), listPost)
                .setView(() -> new RelitemPost(getContext(),1))
                .setBind((position, list, rel, selectItem, customAdapter) -> rel.onStartServices(list.get(position)))
                .orientation(RecyclerView.VERTICAL)
                .grid(3)
                .build();
        /*menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.PressClick(menu), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        Dialog.addView(new instagram_bottomsheet(getContext(),R.layout.instagram_bottom_sheet));
                        Dialog.setVisibility(View.VISIBLE);
                        showDialog=true;
                        bottomSheetSetting();
                    }
                });
            }
        });*/
        takeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Screen=true;
                customAdapter.notifyDataSetChanged();
                delete_highlight_1.setVisibility(View.INVISIBLE);
                delete_highlight_2.setVisibility(View.INVISIBLE);
                delete_highlight_3.setVisibility(View.INVISIBLE);
                delete_highlight_4.setVisibility(View.INVISIBLE);
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(takeScreen, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        if(!mLocalData.getUserInfo(MainActivity.getGlobal())){
                            Dialog.removeAllViews();
                            showDialog=true;
                            Dialog.setVisibility(View.VISIBLE);
                            Dialog_pardakht pardakht=new Dialog_pardakht(getContext(),fragment_instagram.this,null);
                            Dialog.addView(pardakht);
                            pardakht.pardakht(Dialog,takeScreen,setting);
                        }
                        else{
                            MainActivity.getGlobal().setVisibility(new Visibility() {
                                @Override
                                public void Allow() {
                                    takeScreen.setVisibility(View.VISIBLE);
                                    setting.setVisibility(View.VISIBLE);
                                    MainActivity.getGlobal().findViewById(R.id.textview_screenshot).setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void Deny() {
                                    takeScreen.setVisibility(View.VISIBLE);
                                    setting.setVisibility(View.VISIBLE);
                                    MainActivity.getGlobal().findViewById(R.id.textview_screenshot).setVisibility(View.VISIBLE);
                                }
                            });

                            setting.clearAnimation();
                            takeScreen.clearAnimation();
                            takeScreen.setVisibility(View.INVISIBLE);
                            setting.setVisibility(View.INVISIBLE);
                            parent.findViewById(R.id.textview_screenshot).setVisibility(View.INVISIBLE);

                            if(takeScreen.getVisibility()==View.INVISIBLE && setting.getVisibility()==View.INVISIBLE){
                                Dialog.removeAllViews();
                                showDialog=false;
                                if (Build.VERSION.SDK_INT >= 23 ) {
                                    if (Setting.checkPermissionWrite()) {
                                        Setting.takeScreenshotWithoutWatermark(getContext(), MainActivity.getGlobal(), "instagram");
                                        takeScreen.setVisibility(View.VISIBLE);
                                        setting.setVisibility(View.VISIBLE);
                                        MainActivity.getGlobal().findViewById(R.id.textview_screenshot).setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        ActivityCompat.requestPermissions(MainActivity.getGlobal(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                                    }
                                }
                                else {
                                    Setting.takeScreenshotWithoutWatermark(getContext(), MainActivity.getGlobal(), "instagram");
                                    takeScreen.setVisibility(View.VISIBLE);
                                    setting.setVisibility(View.VISIBLE);
                                    MainActivity.getGlobal().findViewById(R.id.textview_screenshot).setVisibility(View.VISIBLE);
                                }
                            }
                        }




                    }
                });

            }
        });
        firstPost=parent.findViewById(R.id.textview_nopost_share);
        firstPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPostBallon(firstPost);
            }
        });
        delete_highlight_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlights.remove(highlights.get(highlights.size()-1));
                setHighlights();
            }
        });
        delete_highlight_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlights.remove(highlights.get(highlights.size()-1));
                setHighlights();
            }
        });
        delete_highlight_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlights.remove(highlights.get(highlights.size()-1));
                setHighlights();
            }
        });
        delete_highlight_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlights.remove(highlights.get(highlights.size()-1));
                setHighlights();
            }
        });

    }

    private static fragment_instagram instance;
    public static fragment_instagram getInstance(Context context){
        if(instance==null){
            instance=new fragment_instagram();
            return instance;
        }
        else return instance;
    }


    public void setHighlights() {
        if(highlights.isEmpty()){
            if(Darkmode)
                plus_highlights.setImageResource(R.drawable.plus_highlights_black);
            else
            plus_highlights.setImageResource(R.drawable.plus_highlights_white);
            new_highlights.setText("New");
            text_highlight_1.setText("");
            text_highlight_2.setText("");
            text_highlight_3.setText("");
            highlight_1.setImageResource(R.drawable.shape_circlehighlights);
            highlight_2.setImageResource(R.drawable.shape_circlehighlights);
            highlight_3.setImageResource(R.drawable.shape_circlehighlights);
            highlight_2.setVisibility(View.VISIBLE);
            highlight_3.setVisibility(View.VISIBLE);
            plus_highlights.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(highlights.isEmpty()) {
                        highlightView=plus_highlights;
                        add_BalloonHighlights(highlightView);

                    }
                }
            });
            delete_highlight_1.setVisibility(View.GONE);
        }
        else if(highlights.size()==1){
            plus_highlights.setImageURI(highlights.get(0).getImage());
            new_highlights.setText(highlights.get(0).getText());
            if(Darkmode)
                highlight_1.setImageResource(R.drawable.plus_highlights_black);
            else
                highlight_1.setImageResource(R.drawable.plus_highlights_white);
            text_highlight_1.setText("New");
            highlight_2.setVisibility(View.INVISIBLE);
            highlight_3.setVisibility(View.INVISIBLE);
            text_highlight_2.setText("");
            text_highlight_3.setText("");
            highlight_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(highlights.size()==1) {
                        highlightView=highlight_2;
                        add_BalloonHighlights(highlightView);

                    }
                }
            });
            delete_highlight_1.setVisibility(View.VISIBLE);
            delete_highlight_2.setVisibility(View.GONE);
        }
        else if(highlights.size()==2){
            plus_highlights.setImageURI(highlights.get(0).getImage());
            new_highlights.setText(highlights.get(0).getText());
            highlight_1.setImageURI(highlights.get(1).getImage());
            text_highlight_1.setText(highlights.get(1).getText());
            highlight_2.setVisibility(View.VISIBLE);
            if(Darkmode)
                highlight_2.setImageResource(R.drawable.plus_highlights_black);
            else
                highlight_2.setImageResource(R.drawable.plus_highlights_white);
            text_highlight_2.setText("New");
            highlight_3.setVisibility(View.INVISIBLE);
            text_highlight_3.setText("");
            highlight_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(highlights.size()==2){
                        highlightView=highlight_3;
                        add_BalloonHighlights(highlightView);
                    }

                }
            });

            delete_highlight_1.setVisibility(View.VISIBLE);
            delete_highlight_2.setVisibility(View.VISIBLE);
            delete_highlight_3.setVisibility(View.GONE);
        }
        else if(highlights.size()==3){
            plus_highlights.setImageURI(highlights.get(0).getImage());
            new_highlights.setText(highlights.get(0).getText());
            highlight_1.setImageURI(highlights.get(1).getImage());
            text_highlight_1.setText(highlights.get(1).getText());
            highlight_2.setVisibility(View.VISIBLE);
            highlight_2.setImageURI(highlights.get(2).getImage());
            text_highlight_2.setText(highlights.get(2).getText());
            highlight_3.setVisibility(View.VISIBLE);
            if(Darkmode)
                highlight_3.setImageResource(R.drawable.plus_highlights_black);
            else
                highlight_3.setImageResource(R.drawable.plus_highlights_white);

            text_highlight_3.setText("New");
            highlight_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(highlights.size()==3) {
                        highlightView=highlight_3;
                        add_BalloonHighlights(highlightView);
                    }
                }
            });
            delete_highlight_1.setVisibility(View.VISIBLE);
            delete_highlight_2.setVisibility(View.VISIBLE);
            delete_highlight_3.setVisibility(View.VISIBLE);
            delete_highlight_4.setVisibility(View.GONE);
        }
        else if(highlights.size()==4){
            plus_highlights.setImageURI(highlights.get(0).getImage());
            new_highlights.setText(highlights.get(0).getText());
            highlight_1.setImageURI(highlights.get(1).getImage());
            text_highlight_1.setText(highlights.get(1).getText());
            highlight_2.setVisibility(View.VISIBLE);
            highlight_2.setImageURI(highlights.get(2).getImage());
            text_highlight_2.setText(highlights.get(2).getText());
            highlight_3.setVisibility(View.VISIBLE);
            highlight_3.setImageURI(highlights.get(3).getImage());
            text_highlight_3.setText(highlights.get(3).getText());
            delete_highlight_1.setVisibility(View.VISIBLE);
            delete_highlight_2.setVisibility(View.VISIBLE);
            delete_highlight_3.setVisibility(View.VISIBLE);
            delete_highlight_4.setVisibility(View.VISIBLE);
        }
    }
    private void add_BalloonHighlights(View view){
        if(highlights.size()<4) {
            Point point = new Point();
            point.set(1000000, 1000000);
            if (view == highlightView) {
                BalloonOverlayShape balloonOverlayShape = BalloonOverlayRect.INSTANCE;
                balloon = new Balloon.Builder(getContext())
                        .setLayout(R.layout.tooltip_addhighlights)
                        .setArrowSize(0)
                        .setArrowOrientation(ArrowOrientation.TOP)
                        .setArrowPosition(0.0f)
                        .setMarginLeft(22)
                        .setMarginBottom(20)
                        .setCornerRadius(0)
                        .setBackgroundColor(Color.parseColor("#00000000"))
                        .setBalloonAnimation(BalloonAnimation.ELASTIC)
                        .setIsVisibleOverlay(true) // sets the visibility of the overlay for highlighting an anchor.
                        .setOverlayColorResource(R.color.OverlyToolTip) // background color of the overlay using a color resource.
                        .setOverlayPadding(0)// sets a padding value of the overlay shape internally.
                        .setBalloonOverlayAnimation(BalloonOverlayAnimation.FADE) // default is fade.
                        .setDismissWhenOverlayClicked(false)
                        .setOverlayShape(balloonOverlayShape)
                        .setOverlayPosition(point)
                        .build();
                balloon.show(view);
            }
            balloontemp = balloon;
            image_add_highlight = balloontemp.getContentView().findViewById(R.id.circleimage_add_highlights);
            name_highlights = balloontemp.getContentView().findViewById(R.id.edittext_highlights_name_add);
            balloon.getContentView().findViewById(R.id.edittext_highlights_name_add).setFocusableInTouchMode(true);
            balloon.getContentView().findViewById(R.id.edittext_highlights_name_add).setFocusable(true);
            name_highlights.requestFocus();
            Setting.ShowKeyBoard(getContext());
            TextView btn_add = balloontemp.getContentView().findViewById(R.id.btn_add_highlight);


            image_add_highlight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(image_add_highlight, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                        @Override
                        public void TheEnd() {
                        /*dialog_camera_gallery=parent.findViewById(R.id.dialog_camera_gallery);
                        dialog_camera_gallery.addView(new camera_gallery(getContext(),R.layout.dialog_camera_gallery));
                        dialog_camera_gallery.setVisibility(View.VISIBLE);*/
                            Setting.HideKeyBoard();
                            BalloonOverlayShape balloonOverlayShape = BalloonOverlayRect.INSTANCE;
                            ballooncameragallery = new Balloon.Builder(getContext())
                                    .setLayout(R.layout.tooltip_cameragallery)
                                    .setArrowSize(0)
                                    .setArrowOrientation(ArrowOrientation.TOP)
                                    .setArrowPosition(0.0f)
                                    .setMarginLeft(22)
                                    .setMarginBottom(20)
                                    .setCornerRadius(0)
                                    .setBackgroundColor(Color.parseColor("#00000000"))
                                    .setBalloonAnimation(BalloonAnimation.ELASTIC)
                                    .setIsVisibleOverlay(true) // sets the visibility of the overlay for highlighting an anchor.
                                    .setOverlayColorResource(R.color.OverlyToolTip) // background color of the overlay using a color resource.
                                    .setOverlayPadding(0)// sets a padding value of the overlay shape internally.
                                    .setBalloonOverlayAnimation(BalloonOverlayAnimation.FADE) // default is fade.
                                    .setDismissWhenOverlayClicked(false)
                                    .setOverlayShape(balloonOverlayShape)
                                    .setOverlayPosition(point)
                                    .build();
                            balloon.dismiss();
                            ballooncameragallery.show(view);
                            ImageView Camera = ballooncameragallery.getContentView().findViewById(R.id.camera);
                            ImageView Gallery = ballooncameragallery.getContentView().findViewById(R.id.gallery);
                            Camera.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    openCamera(3);
                                }
                            });
                            Gallery.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    gallery(3);
                                }
                            });
                        }
                    });

                }
            });
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(btn_add, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                        @Override
                        public void TheEnd() {
                            if (!name_highlights.getText().toString().equals("") && imageUri!=null) {
                                ListHighlights temp = new ListHighlights();
                                temp.setImage(imageUri);
                                temp.setText(name_highlights.getText().toString());
                                if(showDialog) {
                                    listPreviewHighlight.add(temp);
                                    customAdapterHighlight.notifyDataSetChanged();
                                    RecListEditHighlight.setVisibility(View.VISIBLE);
                                    if(listPreviewHighlight.size()==4){
                                        rel_addHighlights.setAlpha(0.2f);
                                    }
                                    else{
                                        rel_addHighlights.setAlpha(1);
                                    }
                                    parent.findViewById(R.id.TextViewAddHighlight).setVisibility(View.GONE);
                                }
                                else{
                                    highlights.add(temp);
                                    setHighlights();
                                }
                                imageUri=null;
                                balloon.dismiss();
                            }else if(imageUri==null){
                                MainActivity.getGlobal().showSnackBar("warning","لطفا عکس انتخاب کنید",1000);
                            }
                        }
                    });

                }
            });
        }
    }


    private void editProfileDialog(){
        Point point=new Point();
        point.set(1000000,1000000);
        Dialog=parent.findViewById(R.id.instagram_dialog);
        Dialog.addView(new editDialog(getContext(),R.layout.dialog_editprofile));
        Dialog.setVisibility(View.VISIBLE);
        ImageView close=parent.findViewById(R.id.close_edit);
        ok=parent.findViewById(R.id.blue_check_editProfile);
        EditText Name=parent.findViewById(R.id.edittext_name);
        EditText Username=parent.findViewById(R.id.edittext_username);
        EditText Bio=parent.findViewById(R.id.edittext_Bio);
        EditText Website=parent.findViewById(R.id.edittext_website);
        EditText followers=parent.findViewById(R.id.instagram_edittext_followers);
        EditText post=parent.findViewById(R.id.instagram_edittext_post);
        EditText following=parent.findViewById(R.id.instagram_edittext_following);
        changeProfilePhoto=parent.findViewById(R.id.textview_changepPofilePhoto);
        image_editprofile=parent.findViewById(R.id.imageview_dialog_editprofile);
        profile=parent.findViewById(R.id.image_profile);
        image_editprofile.setImageDrawable(profile.getDrawable());
        Name.setText(text_Name.getText());
        Username.setText(text_Username.getText());
        Bio.setText(text_Bio.getText());
        Website.setText(text_Website.getText());
        followers.setText(number_followers.getText());
        post.setText(number_post.getText());
        following.setText(number_following.getText());
        DialogAddPost=parent.findViewById(R.id.TextViewAddPost);
        rel_addPost=parent.findViewById(R.id.rel_addPost);
        RecListEditHighlight=parent.findViewById(R.id.RecListEditHighlight);
        RecListEditPost=parent.findViewById(R.id.RecListEditPost);
        customAdapterPost = new CustomAdapter.RecyclerBuilder<ListViewPost, RelItemPreview>(getContext(), RecListEditPost, listPreviewPost)
                .setView(() -> new RelItemPreview(getContext()))
                .setBind((position, list, rel, selectItem, customAdapter) -> rel.PrevPost(list.get(position),list,customAdapter))
                .orientation(RecyclerView.HORIZONTAL)
                .build();
        customAdapterHighlight = new CustomAdapter.RecyclerBuilder<ListHighlights, RelItemPreview>(getContext(), RecListEditHighlight, listPreviewHighlight)
                .setView(() -> new RelItemPreview(getContext()))
                .setBind((position, list, rel, selectItem, customAdapter) -> rel.PrevHighlight(list.get(position),list,customAdapter))
                .orientation(RecyclerView.HORIZONTAL)
                .build();
        rel_addHighlights=parent.findViewById(R.id.rel_addHighlights);
        rel_addHighlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(highlights.size()+listPreviewHighlight.size()<4) {
                    Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(rel_addHighlights, 1, 1, 0.9f, 0.9f, 0, 50), new IAnimationEnd() {
                        @Override
                        public void TheEnd() {
                        /*BalloonOverlayShape balloonOverlayShape=BalloonOverlayRect.INSTANCE;
                        balloon=new Balloon.Builder(getContext())
                                .setLayout(R.layout.tooltip_addhighlights)
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
                        balloon.show(rel_addHighlights);*/
                            highlightView = rel_addHighlights;
                            add_BalloonHighlights(highlightView);
                        }
                    });
                }
                else{
                    MainActivity.getGlobal().showSnackBar("warning","حد مجاز هایلایت پر شده است",1000);
                }
            }
        });
        rel_addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(rel_addPost, 1, 1, 0.9f, 0.9f, 0, 50), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        addPostBallon(rel_addPost);
                    }
                });


            }
        });
        rel_minushighlights=parent.findViewById(R.id.rel_minusHighlights);
        rel_minusPost=parent.findViewById(R.id.rel_minusPost);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_Name.setText(Name.getText().toString());
                text_Username.setText(Username.getText().toString());
                text_Bio.setText(Bio.getText().toString());
                text_Website.setText(Website.getText().toString());
                number_followers.setText(followers.getText().toString());
                number_post.setText(post.getText().toString());
                number_following.setText(following.getText().toString());
                profile.setImageDrawable(image_editprofile.getDrawable());
                profilebottom.setImageDrawable(image_editprofile.getDrawable());
                if(listPreviewHighlight.size()>0){
                    highlights.addAll(listPreviewHighlight);
                    setHighlights();
                    listPreviewHighlight.clear();
                }
                if(listPreviewPost.size()>0){
                    listPost.addAll(listPreviewPost);
                    customAdapter.notifyDataSetChanged();
                    listPreviewPost.clear();
                    customAdapterPost.notifyDataSetChanged();
                }
                Dialog.setVisibility(View.GONE);
                Dialog.removeAllViews();
                showDialog=false;
            }
        });
        rel_minusPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listPost.size()>0) {
                    Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(rel_minusPost, 1, 1, 0.9f, 0.9f, 0, 50), new IAnimationEnd() {
                        @Override
                        public void TheEnd() {
                            boolean temp = false;
                            if (postadd != 0) {
                                listPost.remove(listPost.size() - 1);
                                customAdapter.notifyDataSetChanged();
                                for (int i = 0; i < listPost.size(); i++) {
                                    if (listPost.get(i).getType() == R.drawable.igtv_white_topor) {
                                        temp = true;
                                    }
                                }
                                if (!temp) {
                                    headerpost_menu2.setVisibility(View.VISIBLE);
                                    headerpost_menu.setVisibility(View.INVISIBLE);
                                }
                                postadd -= 1;
                                noPost();
                                ok.performClick();
                                ok.setPressed(true);
                                showDialog = false;
                            }
                        }
                    });
                }


            }
        });
        rel_minushighlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!highlights.isEmpty()) {
                    Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(rel_minushighlights, 1, 1, 0.9f, 0.9f, 0, 50), new IAnimationEnd() {
                        @Override
                        public void TheEnd() {

                            highlights.remove(highlights.size() - 1);
                            setHighlights();
                            ok.performClick();
                            ok.setPressed(true);
                            Dialog.removeAllViews();
                            showDialog = false;

                        }
                    });
                }


            }
        });
        SwitchCompat darkMode=parent.findViewById(R.id.darkSwitch);
        SwitchCompat blueTick=parent.findViewById(R.id.blueTickSwitch);
        SwitchCompat private_acc=parent.findViewById(R.id.privateSwitch);
        blueTick.setChecked(tick);
        private_acc.setChecked(private_bool);
        if(Darkmode){
            darkMode.setChecked(Darkmode);
        }
        private_acc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(private_acc.isChecked()){
                    private_bool=true;
                    lock.setVisibility(View.VISIBLE);
                }
                else{
                    private_bool=false;
                    lock.setVisibility(View.GONE);
                }
                /*ok.performClick();
                ok.setPressed(true);*/
                ok.callOnClick();
                Dialog.removeAllViews();
                showDialog=false;
            }
        });
        blueTick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(blueTick.isChecked()){
                    tick=true;
                    blue_tick.setVisibility(View.VISIBLE);
                }
                else{
                    tick=false;
                    blue_tick.setVisibility(View.GONE);
                }
                ok.performClick();
                ok.setPressed(true);
                Dialog.removeAllViews();
                showDialog=false;
            }
        });
        darkModeSetting(darkMode,ok);



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(close, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        Dialog.setVisibility(View.GONE);
                        ok.performClick();
                        ok.setPressed(true);
                        Dialog.removeAllViews();
                        showDialog=false;
                    }
                });

            }
        });

        changeProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                dialog_camera_gallery=parent.findViewById(R.id.dialog_camera_gallery);
                dialog_camera_gallery.setVisibility(View.VISIBLE);
                dialog_camera_gallery.addView(new editDialog(getContext(),R.layout.dialog_camera_gallery));*/
                BalloonOverlayShape balloonOverlayShape=BalloonOverlayRect.INSTANCE;
                ballooncameragallery=new Balloon.Builder(getContext())
                        .setLayout(R.layout.tooltip_cameragallery)
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
                ballooncameragallery.show(changeProfilePhoto);
                ImageView Camera=ballooncameragallery.getContentView().findViewById(R.id.camera);
                ImageView Gallery=ballooncameragallery.getContentView().findViewById(R.id.gallery);
                Camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCamera(0);
                    }
                });
                Gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gallery(0);
                    }
                });
            }
        });
        showDialog=true;
    }



    public void darkModeSetting(SwitchCompat darkMode,ImageView ok){
        darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Main.setBackgroundColor(Color.BLACK);
                    Darkmode=true;
                    text_Username.setTextColor(Color.WHITE);
                    text_Bio.setTextColor(Color.WHITE);
                    text_Name.setTextColor(Color.WHITE);
                    text_followers.setTextColor(Color.WHITE);
                    text_following.setTextColor(Color.WHITE);
                    text_post.setTextColor(Color.WHITE);
                    new_highlights.setTextColor(Color.WHITE);
                    number_followers.setTextColor(Color.WHITE);
                    number_following.setTextColor(Color.WHITE);
                    number_post.setTextColor(Color.WHITE);
                    editProfile.setTextColor(Color.WHITE);
                    textView_keephighlights.setTextColor(Color.WHITE);
                    textview_highlights.setTextColor(Color.WHITE);
                    text_highlight_1.setTextColor(Color.WHITE);
                    text_highlight_2.setTextColor(Color.WHITE);
                    text_highlight_3.setTextColor(Color.WHITE);
                    menu.setImageResource(R.drawable.menu_white);
                    plus.setImageResource(R.drawable.feed_white);
                    text_Website.setTextColor(Color.WHITE);
                    if(highlights.size()==0){
                        plus_highlights.setImageResource(R.drawable.plus_highlights_black);}
                    else if(highlights.size()==1)
                        highlight_1.setImageResource(R.drawable.plus_highlights_black);
                    else if(highlights.size()==2)
                        highlight_2.setImageResource(R.drawable.plus_highlights_black);
                    else if(highlights.size()==3)
                        highlight_3.setImageResource(R.drawable.plus_highlights_black);
                    if(postadd==0){
                        ImageView nopost=parent.findViewById(R.id.no_post_black);
                        TextView nopostcomment=parent.findViewById(R.id.textview_nopost_comment);
                        TextView nopostProfile=parent.findViewById(R.id.textview_profile_nopost);
                        nopost.setImageResource(R.drawable.no_post_white);
                        nopostcomment.setTextColor(Color.parseColor("#FFFFFF"));
                        nopostProfile.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                    /*igtv.setImageResource(R.drawable.igtv_black);*/
                    /*location.setImageResource(R.drawable.instagram_tag);
                    location_2.setImageResource(R.drawable.instagram_tag);*/
                    down.setImageResource(R.drawable.down_white);
                    lock.setImageResource(R.drawable.lock_white);
                    home.setImageResource(R.drawable.instagram_home_white);
                    search.setImageResource(R.drawable.instagram_search_white);
                    like.setImageResource(R.drawable.instagram_heart_white);
                    bottommenu.setBackgroundColor(Color.BLACK);
                    post.setImageResource(R.drawable.post_black);
                    post2.setImageResource(R.drawable.post_black);
                    post_tab.setBackground(getContext().getResources().getDrawable(R.drawable.shape_border_bottom_white));
                    addpost.setImageResource(R.drawable.feed_white);
                    profilebottom.setBorderColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = MainActivity.getGlobal().getWindow();
                        /*window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(Color.BLACK);*/
                        window.setNavigationBarColor(Color.parseColor("#000000"));
                        window.setStatusBarColor(Color.parseColor("#000000"));
                        View view = MainActivity.getGlobal().getWindow().getDecorView();
                        view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }
                }
                else{
                    Main.setBackgroundColor(Color.WHITE);
                    Darkmode=false;
                    text_Username.setTextColor(Color.BLACK);
                    text_Bio.setTextColor(Color.BLACK);
                    text_Name.setTextColor(Color.BLACK);
                    number_following.setTextColor(Color.BLACK);
                    number_post.setTextColor(Color.BLACK);
                    number_followers.setTextColor(Color.BLACK);
                    new_highlights.setTextColor(Color.BLACK);
                    text_following.setTextColor(Color.BLACK);
                    text_followers.setTextColor(Color.BLACK);
                    text_post.setTextColor(Color.BLACK);
                    editProfile.setTextColor(Color.BLACK);
                    plus.setImageResource(R.drawable.feed);
                    menu.setImageResource(R.drawable.menu);
                    textView_keephighlights.setTextColor(Color.BLACK);
                    textview_highlights.setTextColor(Color.BLACK);
                    text_highlight_1.setTextColor(Color.BLACK);
                    text_highlight_2.setTextColor(Color.BLACK);
                    text_highlight_3.setTextColor(Color.BLACK);
                    text_Website.setTextColor(Color.parseColor("#1A707A"));
                    if(highlights.size()==0){
                        plus_highlights.setImageResource(R.drawable.plus_highlights_white);}
                    else if(highlights.size()==1)
                        highlight_1.setImageResource(R.drawable.plus_highlights_white);
                    else if(highlights.size()==2)
                        highlight_2.setImageResource(R.drawable.plus_highlights_white);
                    else if(highlights.size()==3)
                        highlight_3.setImageResource(R.drawable.plus_highlights_white);
                    if(postadd==0){
                        ImageView nopost=parent.findViewById(R.id.no_post_black);
                        TextView nopostcomment=parent.findViewById(R.id.textview_nopost_comment);
                        TextView nopostProfile=parent.findViewById(R.id.textview_profile_nopost);
                        nopost.setImageResource(R.drawable.no_post_black);
                        nopostcomment.setTextColor(Color.parseColor("#000000"));
                        nopostProfile.setTextColor(Color.parseColor("#000000"));
                    }
                    igtv.setImageResource(R.drawable.igtv);
                    location.setImageResource(R.drawable.instagram_tag_black);
                    location_2.setImageResource(R.drawable.instagram_tag_black);
                    down.setImageResource(R.drawable.down);
                    lock.setImageResource(R.drawable.lock_black);
                    home.setImageResource(R.drawable.instagram_home_black);
                    search.setImageResource(R.drawable.instagram_search_black);
                    like.setImageResource(R.drawable.instagram_heart_black);
                    post.setImageResource(R.drawable.post);
                    post2.setImageResource(R.drawable.post);
                    bottommenu.setBackgroundColor(Color.WHITE);
                    addpost.setImageResource(R.drawable.feed);
                    profilebottom.setBorderColor(Color.BLACK);
                    post_tab.setBackground(getContext().getResources().getDrawable(R.drawable.shape_border_bottom_black));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = MainActivity.getGlobal().getWindow();
                        /*window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(Color.parseColor("#00000000"));*/
                        window.setNavigationBarColor(Color.parseColor("#FFFFFF"));
                        window.setStatusBarColor(Color.parseColor("#FFFFFF"));
                        View view = MainActivity.getGlobal().getWindow().getDecorView();
                        view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }

                }
                ok.performClick();
                ok.setPressed(true);
                Dialog.removeAllViews();
                showDialog=false;
            }
        });

    }

    public void openGallery(int type) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.setType("image/*");
        if(type==0)
        startActivityForResult(gallery, 100);
        else if(type==1){startActivityForResult(gallery, 101);}
        else if(type==2) startActivityForResult(gallery, 102);
        else if(type==3) startActivityForResult(gallery, 103);


    }

    public void openCamera(int type){
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
                if(type==0)
                startActivityForResult(takePicture, 0);
                else if(type==1){startActivityForResult(takePicture, 1);}
                else if(type==2){startActivityForResult(takePicture, 2);}
                else if(type==3){startActivityForResult(takePicture, 3);}
            }
        }

    }

    int code ;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        code = requestCode ;
        if (resultCode == Activity.RESULT_OK && requestCode == 100){
            Uri imageUri = data.getData();
            try {
                Cursor cursor = MediaStore.Images.Media.query(getActivity().getContentResolver(), imageUri, new String[]{MediaStore.Images.Media.DATA});

                if (cursor != null && cursor.moveToFirst()) {
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                    //Create ImageCompressTask and execute with Executor.
                    imageCompressTask = new ImageCompressTask(getContext(), path, iImageCompressTaskListener);

                    mExecutorService.execute(imageCompressTask);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ballooncameragallery.dismiss();
        }
        else if (resultCode == Activity.RESULT_OK && requestCode == 0){
            File file = new File(currentPhotoPath);
            realPath = ImageFilePath.getPath(MainActivity.getGlobal(), Uri.fromFile(file));
            imageCompressTask = new ImageCompressTask(getContext(), currentPhotoPath, iImageCompressTaskListener);
            mExecutorService.execute(imageCompressTask);
            ballooncameragallery.dismiss();
        }
        else if (resultCode == Activity.RESULT_OK && requestCode == 1){
            File file = new File(currentPhotoPath);
            realPath = ImageFilePath.getPath(MainActivity.getGlobal(), Uri.fromFile(file));
            imageCompressTask = new ImageCompressTask(getContext(), currentPhotoPath, iImageCompressTaskListener);
            mExecutorService.execute(imageCompressTask);
            /*ok.performClick();
            ok.setPressed(true);
            Dialog.removeAllViews();
            showDialog=false;*/
            ballooncameragallery.dismiss();

        }
        else if (resultCode == Activity.RESULT_OK && requestCode == 2){
            File file = new File(currentPhotoPath);
            realPath = ImageFilePath.getPath(MainActivity.getGlobal(), Uri.fromFile(file));
            imageCompressTask = new ImageCompressTask(getContext(), currentPhotoPath, iImageCompressTaskListener);
            mExecutorService.execute(imageCompressTask);
            dialog_camera_gallery.removeAllViews();
            Dialog.setVisibility(View.VISIBLE);
        }
        else if (resultCode == Activity.RESULT_OK && requestCode == 3){
            File file = new File(currentPhotoPath);
            realPath = ImageFilePath.getPath(MainActivity.getGlobal(), Uri.fromFile(file));
            imageCompressTask = new ImageCompressTask(getContext(), currentPhotoPath, iImageCompressTaskListener);
            mExecutorService.execute(imageCompressTask);
            //dialog_camera_gallery.removeAllViews();
            Dialog.setVisibility(View.VISIBLE);
            ballooncameragallery.dismiss();
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 101){
            imageUri = data.getData();
            try {
                Cursor cursor = MediaStore.Images.Media.query(getActivity().getContentResolver(), imageUri, new String[]{MediaStore.Images.Media.DATA});

                if (cursor != null && cursor.moveToFirst()) {
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                    //Create ImageCompressTask and execute with Executor.
                    imageCompressTask = new ImageCompressTask(getContext(), path, iImageCompressTaskListener);

                    mExecutorService.execute(imageCompressTask);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*ok.performClick();
            ok.setPressed(true);
            Dialog.removeAllViews();
            showDialog=false;*/
            ballooncameragallery.dismiss();
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 102){
            imageUri = data.getData();
            try {
                Cursor cursor = MediaStore.Images.Media.query(getActivity().getContentResolver(), imageUri, new String[]{MediaStore.Images.Media.DATA});

                if (cursor != null && cursor.moveToFirst()) {
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                    //Create ImageCompressTask and execute with Executor.
                    imageCompressTask = new ImageCompressTask(getContext(), path, iImageCompressTaskListener);

                    mExecutorService.execute(imageCompressTask);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog_camera_gallery.removeAllViews();
            Dialog.setVisibility(View.VISIBLE);
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 103){
            imageUri = data.getData();
            try {
                Cursor cursor = MediaStore.Images.Media.query(getActivity().getContentResolver(), imageUri, new String[]{MediaStore.Images.Media.DATA});

                if (cursor != null && cursor.moveToFirst()) {
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                    //Create ImageCompressTask and execute with Executor.
                    imageCompressTask = new ImageCompressTask(getContext(), path, iImageCompressTaskListener);

                    mExecutorService.execute(imageCompressTask);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //dialog_camera_gallery.removeAllViews();
            Dialog.setVisibility(View.VISIBLE);
            ballooncameragallery.dismiss();
        }
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
            File file = compressed.get(0);
            bitmapSend = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (code == 100 || code==0) {
                imageUri = Uri.parse(file.getAbsolutePath());
                image_editprofile.setImageURI(imageUri);
                imageUri=null;
            }
            else if(code==101 || code==1){
                //listPost.get(postadd).setImage(Uri.parse(file.getAbsolutePath()));
                ListViewPost temp=new ListViewPost();
                temp.setImage(Uri.parse(file.getAbsolutePath()));
                if(type_post.equals("igtv")){
                    //listPost.get(postadd).setType(R.drawable.igtv_white_topor);
                    temp.setType(R.drawable.igtv_white_topor);
                    headerpost_menu2.setVisibility(View.INVISIBLE);
                    headerpost_menu.setVisibility(View.VISIBLE);
                }
                else if(type_post.equals("multiphoto")){
                    temp.setType(R.drawable.instagram_multi_photo_white);
                    //listPost.get(postadd).setType(R.drawable.instagram_multi_photo_white);
                }
                else if(type_post.equals("video")){
                    temp.setType(R.drawable.play_white);
                    //listPost.get(postadd).setType(R.drawable.play_white);
                }
                if(showDialog){
                    listPreviewPost.add(temp);
                    customAdapterPost.notifyDataSetChanged();
                    RecListEditPost.setVisibility(View.VISIBLE);
                    DialogAddPost.setVisibility(View.GONE);
                }
                else{
                    listPost.add(temp);
                    customAdapter.notifyItemChanged(postadd);
                }
                postadd+=1;
                noPost();
                imageUri=null;
            }
            else if(code==102 || code==2){
                imageUri = Uri.parse(file.getAbsolutePath());
                image_add_highlight.setImageURI(imageUri);
            }
            else if(code==103 || code==3){
                imageUri = Uri.parse(file.getAbsolutePath());
                balloon.show(highlightView);
                ((ImageView)balloon.getContentView().findViewById(R.id.circleimage_add_highlights)).setImageURI(imageUri);
            }

        }

        @Override
        public void onError(Throwable error) {
        }
    };

    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mExecutorService != null) {
            mExecutorService.shutdown();
            mExecutorService = null;
            imageCompressTask = null;
        }
    }

    boolean tick=false,private_bool=true;


    public void dialog_choice_camera_gallery(View view){
        Setting.HideKeyBoard();
        Point point=new Point();
        point.set(1000000,1000000);
        BalloonOverlayShape balloonOverlayShape= BalloonOverlayOval.INSTANCE;
        ballooncameragallery=new Balloon.Builder(getContext())
                .setLayout(R.layout.tooltip_cameragallery)
                .setArrowSize(0)
                .setArrowOrientation(ArrowOrientation.TOP)
                .setArrowPosition(0.0f)
                .setMarginLeft(22)
                .setMarginBottom(20)
                .setCornerRadius(0)
                .setBackgroundColor(Color.parseColor("#00000000"))
                .setBalloonAnimation(BalloonAnimation.ELASTIC)
                .setIsVisibleOverlay(true) // sets the visibility of the overlay for highlighting an anchor.
                .setOverlayColorResource(R.color.OverlyToolTip) // background color of the overlay using a color resource.
                .setOverlayPadding(4f)// sets a padding value of the overlay shape internally.
                .setBalloonOverlayAnimation(BalloonOverlayAnimation.FADE) // default is fade.
                .setDismissWhenOverlayClicked(false)
                .setOverlayShape(balloonOverlayShape)
                .setOverlayPosition(point)
                .build();
        ballooncameragallery.show(view);
                /*Dialog.removeAllViews();
                Dialog.addView(new editDialog(getContext(),R.layout.dialog_camera_gallery));
                showDialog=true;*/
                ImageView Camera=ballooncameragallery.getContentView().findViewById(R.id.camera);
                ImageView Gallery=ballooncameragallery.getContentView().findViewById(R.id.gallery);
                Camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCamera(1);
                    }
                });
                Gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gallery(1);
                    }
                });
    }

    public void gallery (int type) {
        InputMethodManager imm = (InputMethodManager) MainActivity.getGlobal().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(!imm.isAcceptingText())
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        if (Build.VERSION.SDK_INT >= 23 ) {
            if (checkPermissionWrite())
                openGallery(type);
            else {
                MainActivity.getGlobal().typetemp=String.valueOf(type);
                ActivityCompat.requestPermissions(MainActivity.getGlobal(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,String.valueOf(type)}, 2);
            }
        }
        else
            openGallery(type);
    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkPermissionWrite() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return MainActivity.getGlobal().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;
        }
        else return true ;
    }

    @Override
    public void mBackPressed() {
        super.mBackPressed();
        if(ballooncameragallery!=null) {
            ballooncameragallery.dismiss();
        }
        if(showDialog){
            Dialog.removeAllViews();
            showDialog=false;
        }
        else if(!showDialog){
            Dialog.removeAllViews();
            showDialog=true;
            Dialog.setVisibility(View.VISIBLE);
            Dialog.addView(new Dialog_Description(MainActivity.getGlobal(),null,this));
        }

    }
    private void addPostBallon(View view){
        Point point=new Point();
        point.set(100000,100000);
        BalloonOverlayShape balloonOverlayShape= BalloonOverlayOval.INSTANCE;
        balloon=new Balloon.Builder(getContext())
                .setLayout(R.layout.tooltip_addpost)
                .setArrowSize(0)
                .setArrowOrientation(ArrowOrientation.TOP)
                .setArrowPosition(0.0f)
                .setMarginLeft(22)
                .setMarginBottom(20)
                .setCornerRadius(0)
                .setBackgroundColor(Color.parseColor("#00000000"))
                .setBalloonAnimation(BalloonAnimation.ELASTIC)
                .setIsVisibleOverlay(true) // sets the visibility of the overlay for highlighting an anchor.
                .setOverlayColorResource(R.color.OverlyToolTip) // background color of the overlay using a color resource.
                .setOverlayPadding(4f)// sets a padding value of the overlay shape internally.
                .setBalloonOverlayAnimation(BalloonOverlayAnimation.FADE) // default is fade.
                .setDismissWhenOverlayClicked(false)
                .setOverlayShape(balloonOverlayShape)
                .setOverlayPosition(point)
                .build();
        balloon.show(view);
        RelativeLayout igtv=balloon.getContentView().findViewById(R.id.tooltip_igtv);
        RelativeLayout onephoto=balloon.getContentView().findViewById(R.id.tooltip_photo);
        RelativeLayout multiphoto=balloon.getContentView().findViewById(R.id.tooltip_multi);
        RelativeLayout video=balloon.getContentView().findViewById(R.id.tooltip_video);

        igtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_post="igtv";
                balloon.dismiss();
                dialog_choice_camera_gallery(view);
            }
        });
        onephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_post="onephoto";
                balloon.dismiss();
                dialog_choice_camera_gallery(view);
            }
        });
        multiphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_post="multiphoto";
                balloon.dismiss();
                dialog_choice_camera_gallery(view);
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_post="video";
                balloon.dismiss();
                dialog_choice_camera_gallery(view);
            }
        });

    }


    public void noPost(){
        RelativeLayout noPost=parent.findViewById(R.id.relative_nopost);
        noPost.addView(new No_post(getContext(),R.layout.recycleritem_nopost));
        if(postadd==0){
            noPost.setVisibility(View.VISIBLE);
        headerpost_menu2.setVisibility(View.VISIBLE);
        headerpost_menu.setVisibility(View.INVISIBLE);
            ImageView nopost=parent.findViewById(R.id.no_post_black);
            nopost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(nopost, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                        @Override
                        public void TheEnd() {
                            editProfileDialog();
                        }
                    });
                }
            });
        }
        else {
            noPost.setVisibility(View.GONE);
            noPost.removeAllViews();
        }

    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public  void screenShot(){


    }
}
