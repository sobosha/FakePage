package com.diaco.fakepage.Core;


public interface IView<T> {
    void SendRequest();

    void OnSucceed(T object);

    void OnError(String error, int statusCode);
}