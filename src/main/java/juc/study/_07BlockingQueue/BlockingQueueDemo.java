package juc.study._07BlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 此处考察重点是 BlockingQueue
 * 1, add and remove would throw exception
 * 2, offer and poll would not throw exception
 * 3, put and take would block
 */
public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        /**
         * Group 1: add and remove would throw exception
         *
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        System.out.println(blockingQueue.element());

        // System.out.println(blockingQueue.add("w"));// 此处抛出 IllegalStateException 异常, message = Queue full
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());// 此处抛出 NoSuchElementException 异常
         */

        /**
         * Group 2: offer and poll would not throw exception
         *
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("www")); // here would return false

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());// here would return null
         */

        /**
         * Group 3: put and take would block
         *
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
        // blockingQueue.put("w");// here would block

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        //System.out.println(blockingQueue.take());// here would block
         */

        /**
         * Group 4: offer would have time-out false
         *
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("w",3L, TimeUnit.SECONDS));
         */
    }
}
