package com.diaco.fakepage.Main;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diaco.fakepage.R;

public class BornAdapt extends RelativeLayout {
    public BornAdapt(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.recycler_item_born, this, true);}

    public void onBorn(relItemBorn item){
        ImageView image=findViewById(R.id.image_born);
        TextView textView=findViewById(R.id.textview_born);
        image.setImageResource(item.getImage());
        textView.setText(item.getContent());
        textView.setTextColor(Color.parseColor(item.getColorContent()));
    }

}


