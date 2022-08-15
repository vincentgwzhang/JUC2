package juc.study._01sync_and_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 这里考察的重点是:
 * 1, 锁的运用，和 condition 的运用
 * 2, await 的作用， 和 singal 的作用
 * 3, 永远只有 lock.unlock 的情况下，其他的锁才能醒来, 而不是 signal 后立即醒来
 */
public class RoundCommunication {

    private static final Lock lock = new ReentrantLock();

    private static final Condition conA = lock.newCondition();
    private static final Condition conB = lock.newCondition();
    private static final Condition conC = lock.newCondition();

    private static final int ROUND_COUNT = 1;

    private static int FLAG = 1;

    private static class Printer extends Thread {

        private final int printCount;

        private final int flagCheck;

        private final Lock lock;

        private final Condition condition;

        private final Condition nextCondition;

        private final int nextFlag;

        public Printer(final int printCount, final int flagCheck, final String name, final Lock lock, final Condition condition, final Condition nextCondition, final int nextFlag) {
            this.printCount = printCount;
            this.flagCheck = flagCheck;
            this.lock = lock;
            this.condition = condition;
            this.nextCondition = nextCondition;
            this.nextFlag = nextFlag;
            this.setName(name);
        }

        @Override
        public void run() {
            for (int loop = 0; loop < ROUND_COUNT; loop ++) {
                lock.lock();//获取锁
                try {
                    while (FLAG != flagCheck) {
                        condition.await();//此时当即释放锁，其他  [一个] 线程得以在 lock.lock(); 无阻挡进入
                        //醒来后立即获取锁
                    }
                    for (int i = 1; i < printCount + 1; i++) {
                        System.out.println("Thread [" + Thread.currentThread().getName() + "] print for the [" + i + "] times");
                    }
                    FLAG = nextFlag;
                    nextCondition.signal();//此时是通知其他 condition wake up, 但是不会立即wake up, 而是等到本 lock.unlock() 才会真正 action
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();//释放锁
                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Printer p1 = new Printer(3, 1, "AA", lock, conA, conB, 2);
        Printer p2 = new Printer(3, 2, "BB", lock, conB, conC, 3);
        Printer p3 = new Printer(3, 3, "CC", lock, conC, conA, 1);

        p3.start();
        TimeUnit.SECONDS.sleep(1);
        p2.start();
        TimeUnit.SECONDS.sleep(1);
        p1.start();
    }

}
