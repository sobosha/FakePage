package com.diaco.fakepage.Main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.diaco.fakepage.MainActivity;
import com.diaco.fakepage.R;
import com.diaco.fakepage.Setting.CustomClasses.CustomAdapter;
import com.diaco.fakepage.Setting.CustomClasses.CustomFragment;
import com.diaco.fakepage.Setting.ITaskInThread;
import com.diaco.fakepage.Setting.IUpdateInUi;
import com.diaco.fakepage.Setting.ThreadManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class fragment_screenshot extends CustomFragment {

    CustomAdapter customAdapter;
    List<ListViewPost> listView;
    List<ImageData> imageData;
    int page=0;
    String type="";
    @Override
    public int layout() {
        return R.layout.fragment_scrennshot;
    }


    public void setType(String type) {
        this.type = type;
    }

    ThreadManager threadManager = new ThreadManager();
    @Override
    public void onCreateMyView() {
        listView=new ArrayList<>();
        AppDatabase db=AppDatabase.getInstance(getContext());
        ImageDao dao=db.imageDao();

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                ListViewPost temp=new ListViewPost();
                imageData=dao.getAll();
                temp.setImage(Uri.parse(imageData.get(0).getImage()));
                listView.add(temp);

            }
        }).start();*/
        customAdapter=new CustomAdapter.RecyclerBuilder<ListViewPost,RelitemPost>(getContext(),parent.findViewById(R.id.recycler_screenshot),listView)
                .setView(() -> new RelitemPost(getContext(),3))
                .setBind((position, list, rel, selectItem, customAdapter) -> rel.onStartScreenshot(list.get(position)))
                .orientation(RecyclerView.VERTICAL)
                .grid(3)
                .build();
        if(type.equals("instagram"))
        {
            threadManager.setTaskTwoThread(()->{
                imageData=dao.getAllInsta();
                for(int i=0;i<imageData.size();i++){
                    if(imageData.get(i).getType().equals("instagram")){
                        ListViewPost temp=new ListViewPost();
                        Bitmap bitmap= BitmapFactory.decodeByteArray(imageData.get(i).getImage() , 0, imageData.get(i).getImage().length);
                        temp.setBit_image(bitmap);
                        listView.add(temp);}
                }
            },()->{
                customAdapter.notifyDataSetChanged();
                parent.findViewById(R.id.recycler_screenshot).setVisibility(View.VISIBLE);
            });
        }
        else{
            threadManager=new ThreadManager();
            threadManager.setTaskTwoThread(() -> {
                // other thread
                imageData=dao.getAllInsta();
                for(int i=0;i<imageData.size();i++){
                    if(imageData.get(i).getType().equals("tweeter")){
                        ListViewPost temp=new ListViewPost();
                        Bitmap bitmap= BitmapFactory.decodeByteArray(imageData.get(i).getImage() , 0, imageData.get(i).getImage().length);
                        temp.setBit_image(bitmap);
                        listView.add(temp);
                    }
                }
            }, () -> {
                // main thread
                if(listView.size()<=0)
                {
                    fragment_screenshottab.getInstance(getContext()).viewPager.setCurrentItem(1);
                }
                customAdapter.notifyDataSetChanged();
                parent.findViewById(R.id.recycler_screenshot).setVisibility(View.VISIBLE);
            });
        }


    }

    @Override
    public void mBackPressed() {
        super.mBackPressed();
        if(page==1){
            MainActivity.getGlobal().FinishFragStartFrag(new fragment_screenshot());
        }
        else if (page==0){
            MainActivity.getGlobal().FinishFragStartFrag(new choice_app());
        }

    }
}
