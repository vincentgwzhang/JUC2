package juc.study._06ReadWriteLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 这里考察的是 ReadWriteLock 的操作。
 *      记得：
 *      1, 在这个例子里，写锁就只能有一条线程。
 *      2, 只有写锁做完所有事情，读锁才能进入。
 *      3, 读锁能几条线程同时去读。
 *
 *      private ReadWriteLock rwLock = new ReentrantReadWriteLock();
 *      rwLock.writeLock().lock();
 *      rwLock.writeLock().unlock();
 *
 *      rwLock.readLock().lock();
 *      rwLock.readLock().unlock();
 *
 */
public class ReadWriteLockDemo {
    static class MyCache {
        private volatile Map<String,Object> map = new HashMap<>();

        private ReadWriteLock rwLock = new ReentrantReadWriteLock();

        public void put(String key,Object value) {
            rwLock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName()+" 正在写操作"+key);
                map.put(key,value);
                System.out.println(Thread.currentThread().getName()+" 写完了"+key);
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rwLock.writeLock().unlock();
            }
        }

        public Object get(String key) {
            rwLock.readLock().lock();
            Object result = null;
            try {
                System.out.println(Thread.currentThread().getName()+" 正在读取操作"+key);
                result = map.get(key);
                System.out.println(Thread.currentThread().getName()+" 取完了"+key);
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rwLock.readLock().unlock();
            }
            return result;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyCache myCache = new MyCache();
        //创建线程放数据
        for (int i = 1; i <=5; i++) {
            final int num = i;
            new Thread(()->{
                myCache.put(num+"",num+"");
            },String.valueOf(i)).start();
        }

        TimeUnit.MICROSECONDS.sleep(300);

        //创建线程取数据
        for (int i = 1; i <=5; i++) {
            final int num = i;
            new Thread(()->{
                myCache.get(num+"");
            },String.valueOf(i)).start();
        }
    }
}
