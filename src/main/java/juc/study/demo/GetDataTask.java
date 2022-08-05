package juc.study.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class GetDataTask implements Callable<String> {

    private String str;

    public GetDataTask(String str) {
        this.str = str;
    }

    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(5);

        Integer x = Integer.parseInt(str);

        if (x % 3 == 0) {
            throw new RuntimeException("x=" + x);
        }

        System.out.println("x=" + x + " run finish");

        return str;
    }

}
