package com.diaco.fakepage.Setting;

import android.os.Handler;
import android.os.Looper;

public class ThreadManager extends Thread {

    private ITaskInThread iTaskInThread;
    private IUpdateInUi iUpdateInUi ;
    private Handler handler = new Handler(Looper.getMainLooper());
    public void setTaskInOtherThread (ITaskInThread iTaskInThread) {
        this.iTaskInThread = iTaskInThread;
        start();
    }
    public void setTaskTwoThread (ITaskInThread iTaskInThread , IUpdateInUi iUpdateInUi) {
        this.iTaskInThread = iTaskInThread;
        this.iUpdateInUi = iUpdateInUi;
        start();
    }

    @Override
    public void run() {
        iTaskInThread.task();
        handler.post(() -> {
            //main
            iUpdateInUi.update();
            interrupt();
            handler.removeCallbacksAndMessages(null);
        });

    }

}
