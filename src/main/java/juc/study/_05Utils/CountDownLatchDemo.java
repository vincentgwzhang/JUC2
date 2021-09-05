package juc.study._05Utils;

import java.util.concurrent.CountDownLatch;

/**
 * 重点考 CountDownLatch
 * API:
 *      countDownLatch.countDown();
 *      countDownLatch.await();
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <=6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+" 号同学离开了教室");
                countDownLatch.countDown();

            },String.valueOf(i)).start();
        }

        /**
         * CountDownLatch 中的 state：表示一个计数器。
         * 当 state>0 时，线程调用 await 会被阻塞，
         * 当 state 值被减少为 0 时，线程会被唤醒；
         */
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+" 班长锁门走人了");
    }
}
