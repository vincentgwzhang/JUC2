package juc.study._09FolkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 案例： 1 + 2 + 3 + ... + 100, 拆分差值不能超过10
 *
 * 重点：
 *      1, 继承 RecursiveTask
 *      2, Task.folk
 *      3, Task.join
 */
public class ForkJoinDemo {

    static class MyTask extends RecursiveTask<Integer> {

        private static final Integer VALUE = 10;
        private int begin ;
        private int end;
        private int result ;

        public MyTask(int begin,int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if((end-begin)<=VALUE) {
                for (int i = begin; i <=end; i++) {
                    result = result+i;
                }
            } else {
                int middle = (begin+end)/2;
                MyTask task01 = new MyTask(begin,middle);
                MyTask task02 = new MyTask(middle+1,end);

                /**
                 * 实际上这里是把task 发送 Queue 里
                 */
                task01.fork();
                task02.fork();

                result = task01.join()+task02.join();
            }
            return result;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyTask myTask = new MyTask(0,100);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(myTask);
        Integer result = forkJoinTask.get();// blocking here
        System.out.println(result);
        forkJoinPool.shutdown();
    }
}
