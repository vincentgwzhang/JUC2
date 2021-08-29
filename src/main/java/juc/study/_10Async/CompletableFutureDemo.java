package juc.study._10Async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

/**
 *  重点： CompletableFuture
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception {
        /**
         * Void 代表没有返回值
         * runAsync 代表异步调用，没有返回值
         */
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(()->{
            System.out.println(Thread.currentThread().getName()+" : CompletableFuture1");
        });
        completableFuture1.get();

        /**
         * 返回值是 Integer
         * supplyAsync 代表异步调用
         */
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+" : CompletableFuture2");
            //模拟异常
            int i = 10/0;
            return 1024;
        });
        completableFuture2.whenComplete((result,ex)->{
            System.out.println("------result=" + result);
            System.out.println("------exception = "+ (ex instanceof CompletionException));
        }).get();

    }
}
