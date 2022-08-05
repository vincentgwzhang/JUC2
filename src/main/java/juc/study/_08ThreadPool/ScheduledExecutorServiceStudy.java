package juc.study._08ThreadPool;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceStudy {

    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    @Test
    public void test1() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        System.out.println(df.format(LocalDateTime.now()));

        Runnable runnable = () -> {
            try {
                System.out.println(df.format(LocalDateTime.now()));
                TimeUnit.SECONDS.sleep(12);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        // scheduledExecutorService.scheduleAtFixedRate(runnable, 5, 10, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleWithFixedDelay(runnable, 5, 10, TimeUnit.SECONDS);

        //scheduledExecutorService.shutdown();
        while (true) {
            TimeUnit.SECONDS.sleep(1);
        }
    }


}
