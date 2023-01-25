package com.diaco.fakepage.Main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.diaco.fakepage.MainActivity;
import com.diaco.fakepage.R;
import com.diaco.fakepage.Setting.CustomClasses.CustomAdapter;

import java.util.List;

public class RelItemPreview extends RelativeLayout {
    public RelItemPreview(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.rec_item_ph_edit, this, true);
    }

    public void PrevPost(ListViewPost item, List<ListViewPost> list,CustomAdapter customAdapter){
        ImageView Delete,Image;
        Delete=findViewById(R.id.Delete_Preview);
        Image=findViewById(R.id.PreviewPostHighlight);
        Image.setImageURI(item.getImage());
        Delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(item);
                customAdapter.notifyDataSetChanged();
                if(list.size()==0){
                    fragment_instagram.getInstance(MainActivity.getGlobal()).DialogAddPost.setVisibility(VISIBLE);
                    fragment_instagram.getInstance(MainActivity.getGlobal()).RecListEditPost.setVisibility(GONE);

                }

            }
        });
    }

    public void PrevHighlight(ListHighlights item, List<ListHighlights> list, CustomAdapter customAdapter){
        ImageView Delete,Image;
        Delete=findViewById(R.id.Delete_Preview);
        Image=findViewById(R.id.PreviewPostHighlight);
        Image.setImageURI(item.getImage());
        Delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(item);
                customAdapter.notifyDataSetChanged();
                if(list.size()==0){
                    fragment_instagram.getInstance(MainActivity.getGlobal()).parent.findViewById(R.id.TextViewAddHighlight).setVisibility(View.VISIBLE);
                    fragment_instagram.getInstance(MainActivity.getGlobal()).RecListEditHighlight.setVisibility(View.GONE);
                }
                if(list.size()<4){
                    fragment_instagram.getInstance(MainActivity.getGlobal()).rel_addHighlights.setAlpha(1);
                }
            }
        });
    }
}
