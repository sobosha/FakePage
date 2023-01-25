package com.diaco.fakepage.Main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diaco.fakepage.Core.IView;
import com.diaco.fakepage.Core.MarketResult;
import com.diaco.fakepage.Core.Presenter;
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
import com.diaco.fakepage.Setting.Setting;
import com.diaco.fakepage.Setting.mLocalData;
import com.diaco.fakepage.StoreParent;

public class Dialog_buy extends CustomRel implements StoreParent {
    RelativeLayout Dialog,background;
    TextView Cost,buyFinal,cancleBuy;
    int bz;
    String sku;
    shop_item shop_itemChild;
    Context context;
    String offCode="";
    fragment_instagram fragment_instagram;
    fragment_tweeter fragment_tweeter;
    public Dialog_buy(Context context,fragment_instagram fragment_instagram,fragment_tweeter fragment_tweeter) {
        super(context, R.layout.tooltip_buy);
        this.context=context;
        this.fragment_instagram=fragment_instagram;
        this.fragment_tweeter=fragment_tweeter;
    }

    public void buy(RelativeLayout dialog, int Bz, String Sku, shop_item shop_itemchild){
        bz=Bz;
        shop_itemChild=shop_itemchild;
        sku=Sku;
        Dialog=dialog;
        Cost= MainActivity.getGlobal().findViewById(R.id.textview_cost);
        Cost.setText(shop_itemChild.getPrice()+"");
        buyFinal=MainActivity.getGlobal().findViewById(R.id.btn_buyFinal);
        cancleBuy=MainActivity.getGlobal().findViewById(R.id.btn_buyCancle);
        background=MainActivity.getGlobal().findViewById(R.id.rel_buyDialog);
        background.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.removeAllViews();
                if(fragment_instagram!=null)
                    fragment_instagram.showDialog=false;
                else if(fragment_tweeter!=null)
                    fragment_tweeter.showDialog=false;
            }
        });
        buyFinal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Setting.isNetworkConnect()){
                    buyFinal.setVisibility(GONE);
                    MainActivity.getGlobal().findViewById(R.id.progress_buy).setVisibility(VISIBLE);
                    MarketPurchase.BuyMarket(sku, bz + "", offCode, new PurchaseEvent() {
                        @Override
                        public void NormalPay() {
                            GotoPayGate(shop_itemChild.getId(), Dialog_buy.this, 0);

                        }

                        @Override
                        public void SuccessPay(MarketResult result) {
                            onSuccedBuy();
                        }

                        @Override
                        public void ErrorPay() {
                            Toast.makeText(context, "eeeeee", Toast.LENGTH_SHORT).show();
                            buyFinal.setVisibility(VISIBLE);
                            MainActivity.getGlobal().findViewById(R.id.progress_buy).setVisibility(GONE);
                        }
                    });
                }
                else MainActivity.getGlobal().showSnackBar("warning","لطفا اینترنت خود را متصل کنید",1000);
            }
        });
        cancleBuy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog.removeAllViews();
                if(fragment_instagram!=null)
                    fragment_instagram.showDialog=false;
                else if(fragment_tweeter!=null)
                    fragment_tweeter.showDialog=false;
            }
        });
    }

    @Override
    public void onSuccedBuy() {
        Toast.makeText(context, "خرید با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
        mLocalData.setUserInfo(MainActivity.getGlobal(),true);
    }

    @Override
    public void onErrorBuy() {
        Toast.makeText(context, "خرید ناقص انجام شد", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHideDialog() {

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
                Dialog.removeAllViews();
                fragment_instagram.showDialog = false;
            }

            @Override
            public void OnError(String error, int statusCode) {
                //((mProgress) findViewById(R.id.mProgress)).onSucceed();
                if (gate == 0)
                    GotoPayGate(id, storeParent, 1);

            }
        }, "shop", "buy", "", new reqBuy(id + "", offCode, gate), reqBuyResult.class);

    }
}
