package juc.study._01sync_and_lock;

/**
 * 这里考察的是线程之间的通讯， 也就是 wait 和 notify 方法的使用
 * 这里重点在于用循环进行 wait, 避免条件错误的情况下 wake up 然后继续运行
 */
public class ThreadCommunication {

    static class Share {

        private int number = 0;

        public synchronized void incr() throws InterruptedException {
            while(number != 0) {
                this.wait();// 一旦 wait, 那么立即释放锁
            }
            number++;
            System.out.println(Thread.currentThread().getName()+" :: "+number);
            this.notifyAll();
        }

        public synchronized void decr() throws InterruptedException {
            while(number != 1) {
                this.wait();// 一旦 wait, 那么立即释放锁
            }
            number--;
            System.out.println(Thread.currentThread().getName()+" :: "+number);
            this.notifyAll();
        }
    }

    public static void main(String[] args) {
        Share share = new Share();
        new Thread(()->{
            for (int i = 1; i <=10; i++) {
                try {
                    share.incr(); //+1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();

        new Thread(()->{
            for (int i = 1; i <=10; i++) {
                try {
                    share.decr(); //-1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();

        new Thread(()->{
            for (int i = 1; i <=10; i++) {
                try {
                    share.incr(); //+1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"CC").start();

        new Thread(()->{
            for (int i = 1; i <=10; i++) {
                try {
                    share.decr(); //-1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"DD").start();
    }
}
