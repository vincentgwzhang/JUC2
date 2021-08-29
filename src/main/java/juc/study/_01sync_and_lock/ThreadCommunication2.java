package juc.study._01sync_and_lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 这里的重点是考察 Condition
 * 为什么是用 Condition.await 方法呢？因为这里是使用 Lock, 而不是 Sychrnoized 方法，自然就不能用 wait 方法了。
 * 所以用 condition.await 方法代替I have
 */
public class ThreadCommunication2 {

    static class Share {
        private int number = 0;
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        public void incr() throws InterruptedException {
            lock.lock();
            try {
                while (number != 0) {
                    condition.await();
                }
                number++;
                System.out.println(Thread.currentThread().getName()+" :: "+number);
                condition.signalAll();
            }finally {
                lock.unlock();
            }
        }

        public void decr() throws InterruptedException {
            lock.lock();
            try {
                while(number != 1) {
                    condition.await();
                }
                number--;
                System.out.println(Thread.currentThread().getName()+" :: "+number);
                condition.signalAll();
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Share share = new Share();
        new Thread(()->{
            for (int i = 1; i <=10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();
        new Thread(()->{
            for (int i = 1; i <=10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();

        new Thread(()->{
            for (int i = 1; i <=10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"CC").start();
        new Thread(()->{
            for (int i = 1; i <=10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"DD").start();
    }
}
