package com.diaco.fakepage.Main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.diaco.fakepage.Core.DataModelResponse;
import com.diaco.fakepage.Core.IView;
import com.diaco.fakepage.Core.MarketResult;
import com.diaco.fakepage.Core.Presenter;
import com.diaco.fakepage.Core.bzResponce;
import com.diaco.fakepage.Core.reqBuy;
import com.diaco.fakepage.Core.reqBuyResult;
import com.diaco.fakepage.Core.shopResult2;
import com.diaco.fakepage.Core.shop_item;
import com.diaco.fakepage.Core.shoping;
import com.diaco.fakepage.MainActivity;
import com.diaco.fakepage.MarketPurchase;
import com.diaco.fakepage.PurchaseEvent;
import com.diaco.fakepage.R;
import com.diaco.fakepage.Setting.CustomClasses.CustomRel;
import com.diaco.fakepage.Setting.IAnimationEnd;
import com.diaco.fakepage.Setting.Setting;
import com.diaco.fakepage.Setting.mAnimation;
import com.diaco.fakepage.Setting.mLocalData;
import com.diaco.fakepage.StoreParent;
import com.google.android.material.snackbar.Snackbar;

public class Dialog_pardakht extends CustomRel implements StoreParent {

    RelativeLayout Dialog,background,cost;
    TextView buy,screen,TextCost;
    Context context;
    int bz;
    shop_item shop_itemChild;
    String sku;
    fragment_instagram fragment_instagram;
    fragment_tweeter fragment_tweeter;
    ProgressBar ProgressBuy,ProgressCost;
    boolean buyClick=false;
    public Dialog_pardakht(Context context,fragment_instagram fragment_instagram,fragment_tweeter fragment_tweeter) {
        super(context, R.layout.tooltip_screenshot);
        this.context=context;
        this.fragment_instagram=fragment_instagram;
        this.fragment_tweeter=fragment_tweeter;
    }

    public void pardakht(RelativeLayout dialog, RelativeLayout takeScreen, RelativeLayout setting){
        Dialog=dialog;
        buy=MainActivity.getGlobal().findViewById(R.id.btn_buy);
        cost=MainActivity.getGlobal().findViewById(R.id.Rel_cost);
        ProgressBuy=MainActivity.getGlobal().findViewById(R.id.progress_buy);
        ProgressCost=MainActivity.getGlobal().findViewById(R.id.progress_cost);
        TextCost=MainActivity.getGlobal().findViewById(R.id.textview_cost);
        if(Setting.isNetworkConnect()){
            cost.setVisibility(GONE);
            ProgressCost.setVisibility(VISIBLE);
            Presenter.get_global().OnCreate(MainActivity.getGlobal(), "https://tools.hooshejavandeh.com/", "");
            if(mLocalData.getToken(MainActivity.getGlobal()).equals("")) {
                Presenter.get_global().GetAction(new IView<DataModelResponse>() {
                    @Override
                    public void SendRequest() {

                    }

                    @Override
                    public void OnSucceed(DataModelResponse object) {
                        mLocalData.SetToken(MainActivity.getGlobal(), object.getData().getToken());
                        //Toast.makeText(MainActivity.getGlobal(), mLocalData.getToken(MainActivity.getGlobal()), Toast.LENGTH_SHORT).show();
                        getBz();
                    }

                    @Override
                    public void OnError(String error, int statusCode) {
                        //Toast.makeText(MainActivity.getGlobal(), "توکن نگرفت"+statusCode, Toast.LENGTH_SHORT).show();

                    }
                }, "users", "create", "3", DataModelResponse.class);
            }else {
                getBz();

            }
        }
        else{
            ProgressCost.setVisibility(GONE);
            buy.setVisibility(GONE);
        }

        screen=MainActivity.getGlobal().findViewById(R.id.btn_screenshot_withwatermark);
        background=MainActivity.getGlobal().findViewById(R.id.rel_screenshotDialog);
        background.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog.removeAllViews();
                if(fragment_instagram!=null) {
                    fragment_instagram.showDialog = false;
                    fragment_instagram.setHighlights();
                    fragment_instagram.Screen = false;
                    fragment_instagram.customAdapter.notifyDataSetChanged();
                }
            }
        });
        screen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.OnAnimationEnd(mAnimation.townScaleMiddleCustom(screen, 1, 1, 0.9f, 0.9f, 0, 100), new IAnimationEnd() {
                    @Override
                    public void TheEnd() {
                        Dialog.removeAllViews();
                        if(fragment_instagram!=null)
                            fragment_instagram.showDialog=false;
                        else if(fragment_tweeter!=null)
                            fragment_tweeter.showDialog=false;
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
                        MainActivity.getGlobal().findViewById(R.id.textview_screenshot).setVisibility(View.INVISIBLE);

                        if(takeScreen.getVisibility()==View.INVISIBLE && setting.getVisibility()==View.INVISIBLE){
                            Dialog.removeAllViews();
                            if(fragment_instagram!=null)
                                fragment_instagram.showDialog=false;
                            else if(fragment_tweeter!=null)
                                fragment_tweeter.showDialog=false;
                            if (Build.VERSION.SDK_INT >= 23 ) {
                                if (Setting.checkPermissionWrite()) {
                                    if(fragment_instagram!=null) {
                                        Setting.takeScreenshotWithWatermark(getContext(), MainActivity.getGlobal(), "instagram");
                                    }
                                    else if(fragment_tweeter!=null){
                                        Setting.takeScreenshotWithWatermark(getContext(), MainActivity.getGlobal(), "tweeter");
                                    }

                                    takeScreen.setVisibility(View.VISIBLE);
                                    setting.setVisibility(View.VISIBLE);
                                    MainActivity.getGlobal().findViewById(R.id.textview_screenshot).setVisibility(View.VISIBLE);
                                }
                                else {
                                    if(fragment_instagram!=null)
                                        ActivityCompat.requestPermissions(MainActivity.getGlobal(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                                    else if(fragment_tweeter!=null)
                                        ActivityCompat.requestPermissions(MainActivity.getGlobal(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);

                                }
                            }
                            else {
                                if(fragment_instagram!=null) {
                                    Setting.takeScreenshotWithWatermark(getContext(), MainActivity.getGlobal(), "instagram");
                                }
                                else if(fragment_tweeter!=null){
                                    Setting.takeScreenshotWithWatermark(getContext(), MainActivity.getGlobal(), "tweeter");
                                }
                                takeScreen.setVisibility(View.VISIBLE);
                                setting.setVisibility(View.VISIBLE);
                                MainActivity.getGlobal().findViewById(R.id.textview_screenshot).setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            }
        });
        buy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Setting.isNetworkConnect()) {
                    buy.setVisibility(GONE);
                    MainActivity.getGlobal().findViewById(R.id.progress_buy).setVisibility(VISIBLE);
                    buyClick=true;
                    Presenter.get_global().OnCreate(MainActivity.getGlobal(), "https://tools.hooshejavandeh.com/", "");
                    if(mLocalData.getToken(MainActivity.getGlobal()).equals("")) {
                        Presenter.get_global().GetAction(new IView<DataModelResponse>() {
                            @Override
                            public void SendRequest() {

                            }

                            @Override
                            public void OnSucceed(DataModelResponse object) {
                                mLocalData.SetToken(MainActivity.getGlobal(), object.getData().getToken());
                                //Toast.makeText(MainActivity.getGlobal(), mLocalData.getToken(MainActivity.getGlobal()), Toast.LENGTH_SHORT).show();
                                getBz();
                            }

                            @Override
                            public void OnError(String error, int statusCode) {
                                //Toast.makeText(MainActivity.getGlobal(), "توکن نگرفت"+statusCode, Toast.LENGTH_SHORT).show();

                            }
                        }, "users", "create", "3", DataModelResponse.class);
                    }
                    else{ getBz();
                    }
                }
                else
                    MainActivity.getGlobal().showSnackBar("warning","لطفا اینترنت خود را متصل کنید",1000);
            }
        });

    }

    public void getBz(){
        Presenter.get_global().GetAction(new IView<bzResponce>() {
            @Override
            public void SendRequest() { }
            @Override
            public void OnSucceed(bzResponce object) {
                bz=object.getBz();
                //setData(result);
                Presenter.get_global().PostAction(new IView<shopResult2>() {
                    @Override
                    public void SendRequest() {

                    }

                    @Override
                    public void OnSucceed(shopResult2 object) {
                        shop_itemChild = object.getData().get(0);
                        int takhfifValue=0;
                        if (takhfifValue == 0)
                            sku = shop_itemChild.getType() + "-" + shop_itemChild.getQuantity();
                        else
                            sku = shop_itemChild.getType() + "-" + shop_itemChild.getQuantity() + "-" + takhfifValue;
                        TextCost.setText(shop_itemChild.getPrice()+"");
                        cost.setVisibility(VISIBLE);
                        ProgressCost.setVisibility(GONE);



                        if(buyClick) {
                            MarketPurchase.BuyMarket(sku, bz + "", "", new PurchaseEvent() {
                                @Override
                                public void NormalPay() {
                                    GotoPayGate(shop_itemChild.getId(), Dialog_pardakht.this, 0);

                                }

                                @Override
                                public void SuccessPay(MarketResult result) {
                                    onSuccedBuy();
                                }

                                @Override
                                public void ErrorPay() {
                                    //Toast.makeText(context, "eeeeee", Toast.LENGTH_SHORT).show();
                                    buy.setVisibility(VISIBLE);
                                    MainActivity.getGlobal().findViewById(R.id.progress_buy).setVisibility(GONE);
                                }
                            });
                        }
//                        Dialog.removeAllViews();
//                        Dialog.addView(dialog_buy);
//                        dialog_buy.buy(Dialog,bz,sku,shop_itemChild);


                    }

                    @Override
                    public void OnError(String error, int statusCode) {
                        Toast.makeText(context, error + "...." + statusCode, Toast.LENGTH_SHORT).show();
                    }
                }, "shop", "getItem", "", new shoping("fake"), shopResult2.class);

                //Toast.makeText(context, bz+"", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void OnError(String error, int statusCode) {
                Toast.makeText(context, "Bz nagereft", Toast.LENGTH_SHORT).show();
                //MainActivity.getGlobal().showSnackBar("reject", "مشکلی پیش امده.عزیزم مجددا تلاش کن ", 2500);
            }
        } , "users" , "getBz" , "" , bzResponce.class);
        MainActivity.getGlobal().CheckMarketStore();



    }

    public void GotoPayGate(int id, StoreParent storeParent, final int gate) {
        Presenter.get_global().PostAction(new IView<reqBuyResult>() {
            @Override
            public void SendRequest() {
                GotoPayGate(id, storeParent, gate);
            }

            @Override
            public void OnSucceed(reqBuyResult object) {
                //((mProgress) findViewById(R.id.mProgress)).onSucceed();
                storeParent.onHideDialog();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(object.getUrl()));
                getContext().startActivity(browserIntent);
                MainActivity.getGlobal().findViewById(R.id.progress_buy).setVisibility(GONE);
                buy.setVisibility(VISIBLE);
                Dialog.removeAllViews();
                if(fragment_instagram!=null)
                    fragment_instagram.showDialog = false;
                else if(fragment_tweeter!=null)
                    fragment_tweeter.showDialog = false;
            }

            @Override
            public void OnError(String error, int statusCode) {
                //((mProgress) findViewById(R.id.mProgress)).onSucceed();
                if (gate == 0)
                    GotoPayGate(id, storeParent, 1);

            }
        }, "shop", "buy", "", new reqBuy(id + "", "", gate), reqBuyResult.class);

    }

    @Override
    public void onSuccedBuy() {

        mLocalData.setUserInfo(MainActivity.getGlobal(),true);
    }

    @Override
    public void onErrorBuy() {

    }

    @Override
    public void onHideDialog() {

    }
}
