package executorservicedemo.executorservicedemo;

/**
 * Created by a on 2016/12/8.
 */

public class PrintCurrentThreadName {
    //    类名，此类不是Thread类的子类
    public void printCurrentThreadName() {
        //        打印当前线程名字的方法
        Thread currentThread = Thread.currentThread();  // 获得当前的线程
        String threadName = currentThread.getName();
        //        得到当前线程的名字
        System.out.println("执行代码的线程名叫做：" + threadName);
        //        向控制台输出当前线程的名字
    }
}
