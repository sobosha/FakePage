package com.diaco.fakepage.Main;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.diaco.fakepage.MainActivity;
import com.diaco.fakepage.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Customview_multiImage extends RelativeLayout {
    ImageView one,two,three,four;
    LinearLayout top,bottom;
    int number_image;
    AttributeSet attrs_temp;
    public Customview_multiImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.customview_multipleimageview,this,true);
        one=findViewById(R.id.topleft);
        two=findViewById(R.id.topright);
        three=findViewById(R.id.bottomleft);
        four=findViewById(R.id.bottomright);
        top=findViewById(R.id.linear_top);
        bottom=findViewById(R.id.linear_bottom);
        attrs_temp=attrs;
        number_image=0;
        /*setAttr(attrs);*/
    }


    public void addImage(Drawable res){
        number_image+=1;
        if(number_image==1){
            top.getLayoutParams().width=LayoutParams.MATCH_PARENT;
            top.getLayoutParams().height=LayoutParams.MATCH_PARENT;
            bottom.getLayoutParams().width=0;
            bottom.getLayoutParams().height=0;
            one.setImageDrawable(res);
            one.getLayoutParams().height=LayoutParams.MATCH_PARENT;
            one.getLayoutParams().width=LayoutParams.MATCH_PARENT;
            two.getLayoutParams().height=0;
            two.getLayoutParams().width=0;
            three.getLayoutParams().height=0;
            three.getLayoutParams().width=0;
            four.getLayoutParams().height=0;
            four.getLayoutParams().width=0;
            one.requestLayout();
            two.requestLayout();
            three.requestLayout();
            four.requestLayout();
        }
        else if(number_image==2){
            top.getLayoutParams().width=LayoutParams.MATCH_PARENT;
            top.getLayoutParams().height=LayoutParams.MATCH_PARENT;
            bottom.getLayoutParams().width=0;
            bottom.getLayoutParams().height=0;
            two.setImageDrawable(res);
            one.getLayoutParams().height=LayoutParams.MATCH_PARENT;
            one.getLayoutParams().width= LayoutParams.MATCH_PARENT;
            two.getLayoutParams().height=LayoutParams.MATCH_PARENT;
            two.getLayoutParams().width=LayoutParams.MATCH_PARENT;
            three.getLayoutParams().height=0;
            three.getLayoutParams().width=0;
            four.getLayoutParams().height=0;
            four.getLayoutParams().width=0;
            one.requestLayout();
            two.requestLayout();
            three.requestLayout();
            four.requestLayout();
        }
        else if(number_image==3){
            top.getLayoutParams().width=LayoutParams.MATCH_PARENT;
            top.getLayoutParams().height=LayoutParams.MATCH_PARENT;
            bottom.getLayoutParams().width=LayoutParams.MATCH_PARENT;
            bottom.getLayoutParams().height=LayoutParams.MATCH_PARENT;
            three.setImageDrawable(res);
            one.getLayoutParams().height=LayoutParams.MATCH_PARENT;
            one.getLayoutParams().width= LayoutParams.MATCH_PARENT;
            one.setScaleType(ImageView.ScaleType.FIT_XY);
            two.getLayoutParams().height=LayoutParams.MATCH_PARENT;
            two.getLayoutParams().width=LayoutParams.MATCH_PARENT;
            two.setScaleType(ImageView.ScaleType.FIT_XY);
            three.getLayoutParams().height=LayoutParams.MATCH_PARENT;
            three.setScaleType(ImageView.ScaleType.CENTER_CROP);
            three.getLayoutParams().width=LayoutParams.MATCH_PARENT;
            four.getLayoutParams().height=0;
            four.getLayoutParams().width=0;
            one.requestLayout();
            two.requestLayout();
            three.requestLayout();
            four.requestLayout();
        }
        else if(number_image==4){
            top.getLayoutParams().width=LayoutParams.MATCH_PARENT;
            top.getLayoutParams().height=LayoutParams.MATCH_PARENT;
            bottom.getLayoutParams().width=LayoutParams.MATCH_PARENT;
            bottom.getLayoutParams().height=LayoutParams.MATCH_PARENT;
            four.setImageDrawable(res);
            one.getLayoutParams().height=LayoutParams.MATCH_PARENT;
            one.getLayoutParams().width= LayoutParams.MATCH_PARENT;
            two.getLayoutParams().height=LayoutParams.MATCH_PARENT;
            two.getLayoutParams().width=LayoutParams.MATCH_PARENT;
            three.getLayoutParams().height=LayoutParams.MATCH_PARENT;
            three.getLayoutParams().width=LayoutParams.MATCH_PARENT;
            four.getLayoutParams().height=LayoutParams.MATCH_PARENT;
            four.getLayoutParams().width=LayoutParams.MATCH_PARENT;
            four.setScaleType(ImageView.ScaleType.FIT_XY);
            one.requestLayout();
            two.requestLayout();
            three.requestLayout();
            four.requestLayout();
        }
        else{

        }
    }


}
