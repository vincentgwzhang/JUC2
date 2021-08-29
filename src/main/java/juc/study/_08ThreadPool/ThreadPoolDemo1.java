package juc.study._08ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 重点的原理：只有
 *      1) 常驻线程在工作，
 *      2) 阻塞队列已经满的情况下，
 *      3) 在没有到最大线程数的情况下，
 *                                  才会开新的线程，
 */
public class ThreadPoolDemo1 {
    public static void main(String[] args) {

        /**
         * Executors.defaultThreadFactory(),
         * defaultHandler
         */


        /**
         * return new ThreadPoolExecutor(nThreads, nThreads,
         *                                       0L, TimeUnit.MILLISECONDS,
         *                                       new LinkedBlockingQueue<Runnable>());
         */
        ExecutorService threadPool1 = Executors.newFixedThreadPool(5);


        /**
         *         return new FinalizableDelegatedExecutorService
         *             (new ThreadPoolExecutor(1, 1,
         *                                     0L, TimeUnit.MILLISECONDS,
         *                                     new LinkedBlockingQueue<Runnable>()));
         */
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();


        /**
         *         return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
         *                                       60L, TimeUnit.SECONDS,
         *                                       new SynchronousQueue<Runnable>());
         */
        ExecutorService threadPool3 = Executors.newCachedThreadPool();


        try {
            for (int i = 1; i <=10; i++) {
                threadPool3.execute(()->{System.out.println(Thread.currentThread().getName()+" 办理业务");});
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            threadPool3.shutdown();
        }
    }

}
