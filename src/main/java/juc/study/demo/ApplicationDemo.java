package juc.study.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ApplicationDemo {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        List<GetDataTask> tasks = new ArrayList<>();

        for (int index = 0; index < 15; index ++) {
            tasks.add(new GetDataTask(index + ""));
        }

        List<Future<String>> futures = executorService.invokeAll(tasks);

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

        System.out.println("==================================================");

        futures.stream().forEach(f -> {
            System.out.println(f.isDone());
        });
    }

}
