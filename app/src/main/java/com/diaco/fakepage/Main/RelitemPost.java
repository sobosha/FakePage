package com.diaco.fakepage.Main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.diaco.fakepage.MainActivity;
import com.diaco.fakepage.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import retrofit2.http.FormUrlEncoded;

public class RelitemPost extends RelativeLayout {
    public RelitemPost(Context context,int type) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(type==1){
        inflater.inflate(R.layout.recycleritem_post, this, true);}
        else if(type==2){
            inflater.inflate(R.layout.recycler_item_tweeter, this, true);
        }
        else if(type==3){
            inflater.inflate(R.layout.recyleritem_screenshot, this, true);
        }
    }

    public void onStartServices(ListViewPost item)
    {
/*        Bitmap bitmap = null;
        if(item.getImage()!=null){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                bitmap= ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContext().getContentResolver(), item.getImage()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), item.getImage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }}*/
        ImageView recyler_post=findViewById(R.id.image_recycler_post);
        ImageView type_post=findViewById(R.id.type_post);
        type_post.setImageResource(item.getType());
        recyler_post.setImageURI(item.getImage());
        ImageView delete=findViewById(R.id.delete_post);
        if(item.getImage()!=null){
            delete.setVisibility(VISIBLE);
            if(fragment_instagram.getInstance(MainActivity.getGlobal()).Screen){
                delete.setVisibility(GONE);
            }else{
                delete.setVisibility(VISIBLE);
            }
        }
        else{
            delete.setVisibility(GONE);
        }
        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_instagram.getInstance(MainActivity.getGlobal()).listPost.remove(item);
                fragment_instagram.getInstance(MainActivity.getGlobal()).customAdapter.notifyDataSetChanged();
                fragment_instagram.getInstance(MainActivity.getGlobal()).postadd-=1;
                fragment_instagram.getInstance(MainActivity.getGlobal()).noPost();

            }
        });
    }

    public void onStartTweet(ListviewTweet item)
    {
        TextView body,name,tweet,hour,id;
        id=findViewById(R.id.id_tweets);
        hour=findViewById(R.id.hour_tweets);
        tweet=findViewById(R.id.tweets_body);
        id.setText(item.getId());
        hour.setText(item.getHour());
        tweet.setText(item.getTweet());
        body= findViewById(R.id.tweets_body);
        name=findViewById(R.id.name_tweets);
        ImageView profile=findViewById(R.id.recycler_image_tweet);
        item.setCustomview_multiImage(findViewById(R.id.image_multi_tweets));
        if(item.getImageFour()!=null){
            item.getCustomview_multiImage().addImage(item.getImageFour());
            item.getCustomview_multiImage().getLayoutParams().height=Math.round(MainActivity.getGlobal().getResources().getDimension(R.dimen._150sdp));
            item.getCustomview_multiImage().requestLayout();
            item.setImageFour(null);
        }
        if(item.getImageThree()!=null){
            item.getCustomview_multiImage().addImage(item.getImageThree());
            item.getCustomview_multiImage().getLayoutParams().height=Math.round(MainActivity.getGlobal().getResources().getDimension(R.dimen._150sdp));
            item.getCustomview_multiImage().requestLayout();
            item.setImageThree(null);
        }
        if(item.getImageTwo()!=null){
            item.getCustomview_multiImage().addImage(item.getImageTwo());
            item.getCustomview_multiImage().getLayoutParams().height=Math.round(MainActivity.getGlobal().getResources().getDimension(R.dimen._150sdp));
            item.getCustomview_multiImage().requestLayout();
            item.setImageTwo(null);
        }
        if(item.getImageOne()!=null){
            item.getCustomview_multiImage().addImage(item.getImageOne());
            item.getCustomview_multiImage().getLayoutParams().height=Math.round(MainActivity.getGlobal().getResources().getDimension(R.dimen._150sdp));
            item.getCustomview_multiImage().requestLayout();
            item.setImageOne(null);
        }
        profile.setImageDrawable(item.getProfile());
        body.setTextColor(Color.parseColor(item.getBodyColor()));
        name.setTextColor(Color.parseColor(item.getBodyColor()));
        name.setText(item.getName());
    }

    public void onStartScreenshot(ListViewPost item){
        ImageView image=findViewById(R.id.recycler_item_screenshot);
        image.setImageBitmap(item.getBit_image());
        image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getGlobal().FinishFragStartFrag(new fragment_save(item));
            }
        });
    }


/*
    public Bitmap rotateImage(Bitmap source, float angle) {
        ExifInterface ei = null;
        File file=new File(item.getImage().toString());
        Bitmap rotatedBitmap = null;
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(MainActivity.getGlobal().getContentResolver(), item.getImage());
            ei = new ExifInterface(file.getAbsolutePath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
*/

}
