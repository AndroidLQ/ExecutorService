package executorservicedemo.executorservicedemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

//    所有线程池都是继承ThreadPoolExecutor得来
//    public ThreadPoolExecutor(int corePoolSize,
//                              int maximumPoolSize,
//                              long keepAliveTime,
//                              TimeUnit unit,
//                              BlockingQueue<Runnable> workQueue,
//                              ThreadFactory threadFactory,
//                              RejectedExecutionHandler handler){...}
//    参数作用：corePoolSize：线程池中的核心线程数量
//    maximumPoolSize：线程池中的最大线程数量
//    keepAliveTime：这个就是上面说到的“保持活动时间“，上面只是大概说明了一下它的作用，不过它起作用必须在一个前提下，就是当线程池中的线程数量超过了corePoolSize时，它表示多余的空闲线程的存活时间，即：多余的空闲线程在超过keepAliveTime时间内没有任务的话则被销毁。而这个主要应用在缓存线程池中
//    unit：它是一个枚举类型，表示keepAliveTime的单位，常用的如：TimeUnit.SECONDS（秒）、TimeUnit.MILLISECONDS（毫秒）
//    workQueue：任务队列，主要用来存储已经提交但未被执行的任务，不同的线程池采用的排队策略不一样，稍后再讲
//    threadFactory：线程工厂，用来创建线程池中的线程，通常用默认的即可
//    handler：通常叫做拒绝策略，1、在线程池已经关闭的情况下 2、任务太多导致最大线程数和任务队列已经饱和，无法再接收新的任务 。在上面两种情况下，只要满足其中一种时，在使用execute()来提交新的任务时将会拒绝，而默认的拒绝策略是抛一个RejectedExecutionException异常


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //        createNewFixedThreadPool();
        //        createNewSingleThreadExecutor();
        //        createNewCachedThreadPool();
        AsyncTask
    }

    /**
     * 实现runable接口
     */
    private void startThread() {
        MyRunable myRunable = new MyRunable();
        Thread firstThread = new Thread(myRunable);
        firstThread.setName("first thread");
        Thread secondThread = new Thread(myRunable);
        secondThread.setName("second thread");
        firstThread.start();
        secondThread.start();
    }

    // newFixedThreadPool()
    // 　作用：该方法返回一个固定线程数量的线程池，该线程池中的线程数量始终不变，即不会再创建新的线程，
    // 　　　　也不会销毁已经创建好的线程，自始自终都是那几个固定的线程在工作，所以该线程池可以控制线程的最大并发数。
    //　　栗子：假如有一个新任务提交时，线程池中如果有空闲的线程则立即使用空闲线程来处理任务，如果没有，
    // 　　　　　则会把这个新任务存在一个任务队列中，一旦有线程空闲了，则按FIFO方式处理任务队列中的任务。
    private void createNewFixedThreadPool() {
//        创建一个固定线程数量的线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    Log.v("zxy", "线程：" + threadName + ",正在执行第" + index + "个任务");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    //newSingleThreadExecutor
    // 作用：该方法返回一个只有一个线程的线程池，即每次只能执行一个线程任务，多余的任务会保存到一个任务队列中，等待这一个线程空闲，
    // 　　　当这个线程空闲了再按FIFO方式顺序执行任务队列中的任务。
    private void createNewSingleThreadExecutor() {
//        创建一个只有一个线程的线程池，每次只能执行一个线程任务，多余的任务会保存到一个任务队列中，
        // 等待线程处理完再依次处理任务队列中的任务
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        for (int i = 1; i <= 10; i++) {
            final int index = i;
            singleThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    Log.v("zxy", "线程：" + threadName + ",正在执行第" + index + "个任务");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    //newCachedThreadPool() ：
    //作用：该方法返回一个可以根据实际情况调整线程池中线程的数量的线程池。即该线程池中的线程数量不确定，是根据实际情况动态调整的。
    //栗子：假如该线程池中的所有线程都正在工作，而此时有新任务提交，那么将会创建新的线程去处理该任务，而此时假如之前有一些线程完成了任务，
    //      现在又有新任务提交，那么将不会创建新线程去处理，而是复用空闲的线程去处理新任务。那么此时有人有疑问了，那这样来说该线程池的线程岂不是会越集越多？
    //      其实并不会，因为线程池中的线程都有一个“保持活动时间”的参数，通过配置它，如果线程池中的空闲线程的空闲时间超过该“保存活动时间”则立刻停止该线程，而该线程池默认的“保持活动时间”为60s。
    private void createNewCachedThreadPool() {
//        创建一个可以根据实际情况调整线程池中线程的数量的线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 10; i++) {
            final int index = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    Log.v("zxy", "线程：" + threadName + ",正在执行第" + index + "个任务");
                    try {
                        long time = index * 500;
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    //newScheduledThreadPool()
    //作用：该方法返回一个可以控制线程池内线程定时或周期性执行某任务的线程池。
    private void createNewScheduledThreadPool() {
//        创建一个可以定时或者周期性执行任务的线程池
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
        //延迟2秒后执行该任务
        scheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {

            }
        }, 2, TimeUnit.SECONDS);
        //延迟1秒后，每隔2秒执行一次该任务
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

            }
        }, 1, 2, TimeUnit.SECONDS);
    }

    //newSingleThreadScheduledExecutor
    //作用：该方法返回一个可以控制线程池内线程定时或周期性执行某任务的线程池。只不过和上面的区别是该线程池大小为1，而上面的可以指定线程池的大小。
    private void createNewSingleThreadScheduledExecutor() {
        //创建一个可以定时或者周期性执行任务的线程池，该线程池的线程数为1
        ScheduledExecutorService singleThreadScheduledPool = Executors.newSingleThreadScheduledExecutor();
        //延迟1秒后，每隔2秒执行一次该任务
        singleThreadScheduledPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                Log.v("zxy", "线程：" + threadName + ",正在执行");
            }
        }, 1, 2, TimeUnit.SECONDS);
    }
}
