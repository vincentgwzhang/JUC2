package juc.study._04Callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CallableDemo1 {

    static class MyThread implements Runnable {
        @Override
        public void run() {

        }
    }

    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName()+" come in callable");
            return 200;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * Solution 1: Runnable接口创建线程
         */
        new Thread(new MyThread(),"AA").start();

        /**
         * Solution 2: FutureTask
         */
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MyCallable());

        FutureTask<Integer> futureTask2 = new FutureTask<>(()->{
            System.out.println(Thread.currentThread().getName()+" come in callable");
            return 1024;
        });

        /**
         * Solution 3: Use FutureTask
         */
        new Thread(futureTask2,"lucy").start();
        new Thread(futureTask1,"mary").start();

        while(!futureTask2.isDone()) {
            System.out.println("wait.....");
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println(futureTask2.get());
        System.out.println(futureTask1.get());
    }
}
