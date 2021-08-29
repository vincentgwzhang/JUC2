package juc.study._02LockScope;

import java.util.concurrent.TimeUnit;

/**
 * 这里考察的是，锁对其他没有锁的函数没有影响
 */
public class Scenario3 {

    private static class Phone {

        public synchronized void sendSMS() throws InterruptedException {
            TimeUnit.SECONDS.sleep(4); // sleep 不会释放锁
            System.out.println("------sendSMS");
        }

        public void getHello() {
            System.out.println("------getHello");
        }

    }

    public static void main(String[] args) throws Exception {

        Phone phone = new Phone();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "AA").start();

        Thread.sleep(100);

        new Thread(() -> {
            try {
                phone.getHello();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "BB").start();
    }

}
