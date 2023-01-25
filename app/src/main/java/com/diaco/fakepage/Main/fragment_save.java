package com.diaco.fakepage.Main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.diaco.fakepage.MainActivity;
import com.diaco.fakepage.R;
import com.diaco.fakepage.Setting.CustomClasses.CustomFragment;
import com.diaco.fakepage.Setting.IAnimationEnd;
import com.diaco.fakepage.Setting.Setting;
import com.diaco.fakepage.Setting.mAnimation;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class fragment_save extends CustomFragment {

    RelativeLayout save;
    ListViewPost item;
    ImageView image;
    @Override
    public int layout() {
        return R.layout.fragment_save;
    }

    public fragment_save(ListViewPost item) {
        this.item = item;
    }

    @Override
    public void onCreateMyView() {
        save=parent.findViewById(R.id.btn_save);
        image=parent.findViewById(R.id.imageSave);
        image.setImageBitmap(item.getBit_image());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(save, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        Date now = new Date();
                        android.text.format.DateFormat.format("yyyy_MM_dd_hh_mm_ss", now);
                        SimpleDateFormat postFormater = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");

                        String newDateStr = postFormater.format(now);
                        String mPath;
                        try {
                            //String mPath = Environment.getExternalStorageDirectory() + "/" + now + ".jpg";
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                mPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + newDateStr+".jpg";
                            }
                            else
                                mPath = Environment.getExternalStorageDirectory().toString() + "/" + newDateStr+".jpg" ;
                            Bitmap bitmap = Bitmap.createBitmap(item.bit_image);
                            File imageFile = new File(mPath);
                            imageFile.createNewFile();
                            FileOutputStream outputStream = new FileOutputStream(imageFile);
                            int quality = 100;
                            bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream);
                            outputStream.flush();
                            outputStream.close();
                            Toast.makeText(getContext(), "در گالری ذخیره شد", Toast.LENGTH_SHORT).show();
                            MainActivity.getGlobal().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(imageFile)));
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    @Override
    public void mBackPressed() {
        super.mBackPressed();
        MainActivity.getGlobal().FinishFragStartFrag(new fragment_screenshottab());
    }
}
