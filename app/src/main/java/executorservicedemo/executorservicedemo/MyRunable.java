package executorservicedemo.executorservicedemo;

import android.util.Log;

/**
 * Created by a on 2016/12/8.
 */

public class MyRunable implements  Runnable{
    private volatile int count = 10;

    boolean aBoolean = true;
    @Override
    public synchronized void  run() {
        Thread currentThread = Thread.currentThread();  // 获得当前的线程
        while (aBoolean){
            count--;
            Log.i("MyRunable",currentThread.getName() + "----count:"+count);
            if(count < 0 ){
                aBoolean = false;
            }
        }


    }
}
