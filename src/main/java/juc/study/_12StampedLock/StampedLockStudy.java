package juc.study._12StampedLock;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampedLockStudy {

    // 加锁的时候和解锁的时候同一个值 (stamp, long type)

    // ReentrantLock 的特点是在读操作的时候无法获取 write lock
    // 而 StampLock 的特点是读操作的时候依然能获取 write lock

    // StampLock 是不可重入的

    // 三种访问模式

    StampedLock stampedLock = new StampedLock();

    int number = 37;

    private void testWrite() {
        System.out.println(Thread.currentThread().getName() + "\t Prepare to write");
        long stamped = stampedLock.writeLock();// 悲观锁，在读锁释放前无法获得
        try {
            number++;
        } finally {
            stampedLock.unlockWrite(stamped);
        }
        System.out.println(Thread.currentThread().getName() + "\t end to write");
    }

    private void testRead() {
        System.out.println(Thread.currentThread().getName() + "\t Prepare to read");
        long stamped = stampedLock.readLock();
        try {
            TimeUnit.SECONDS.sleep(4);
            int result = number;
            System.out.println("Get the value : " + result);
        } catch (Exception e) {
        } finally {
            stampedLock.unlockRead(stamped);
        }
        System.out.println(Thread.currentThread().getName() + "\t end to write");
    }

    private void testRead2() {
        System.out.println(Thread.currentThread().getName() + "\t Prepare to read");
        long stamped = stampedLock.tryOptimisticRead();
        try {
            System.out.println(Thread.currentThread().getName() + "=== Boolean:" + stampedLock.validate(stamped));
            TimeUnit.SECONDS.sleep(4);
            if (!stampedLock.validate(stamped)) {
                System.out.println(Thread.currentThread().getName() + "=== Deteacted soneone updated, read again");
                stamped = stampedLock.readLock();
                int result = number;
                System.out.println(Thread.currentThread().getName() + "=== Get the value : " + result);
                stampedLock.unlockRead(stamped);
            } else {
                int result = number;
                System.out.println(Thread.currentThread().getName() + "=== Get the value : " + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t end to write");
    }

    @Test
    public void testScenario1() throws InterruptedException {
        StampedLockStudy stampedLockStudy = new StampedLockStudy();
        new Thread(stampedLockStudy::testRead, "read_thread").start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(stampedLockStudy::testWrite, "write_thread").start();

        while(Thread.activeCount() > 2) {
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Test
    public void testScenario2() throws InterruptedException {
        StampedLockStudy stampedLockStudy = new StampedLockStudy();
        new Thread(stampedLockStudy::testRead2, "read_thread2_A").start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(stampedLockStudy::testRead2, "read_thread2_B").start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(stampedLockStudy::testWrite, "write_thread").start();

        while(Thread.activeCount() > 2) {
            TimeUnit.SECONDS.sleep(1);
        }
    }

}
