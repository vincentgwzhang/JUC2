package juc.study._05Utils;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 重点考察 CyclicBarrier
 *      API: cyclicBarrier.await()
 *
 * 案例： After run finish 7 threads then trigger action
 */
public class CyclicBarrierDemo {

    private static final int NUMBER = 7;

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier =
                new CyclicBarrier(NUMBER,()-> System.out.println("Action triggered: 集齐7颗龙珠就可以召唤神龙"));

        for (int i = 1; i <=NUMBER; i++) {
            new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                    System.out.println(Thread.currentThread().getName()+" 星龙被收集到了");
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }

        TimeUnit.SECONDS.sleep(10);
        System.out.println(Thread.activeCount());
    }
}
