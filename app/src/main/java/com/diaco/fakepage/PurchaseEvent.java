package com.diaco.fakepage;


import com.diaco.fakepage.Core.MarketResult;

public interface PurchaseEvent {
    void NormalPay();
    void SuccessPay(MarketResult result);
    void ErrorPay();
}
