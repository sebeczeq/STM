package com.companyname.bank;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class STMBenchmark {
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }


    @Benchmark
    @Fork(value = 1, warmups = 0)
    @BenchmarkMode(Mode.AverageTime)
    public void init(AccountBenchmark benchmark) {
        for (int i = 0; i < benchmark.iterator; i++){
            benchmark.account1.adjustBy(benchmark.r.nextInt(100));
        }
    }

    @Benchmark
    @Fork(value = 1, warmups = 0)
    @BenchmarkMode(Mode.AverageTime)
    public void initTest(AccountBenchmark2 benchmark){
        for (int i = 0; i < benchmark.iterator; i++){
            benchmark.a.transferTo(benchmark.b,10);
        }
    }

    @Benchmark
    @Fork(value = 1, warmups = 0)
    @BenchmarkMode(Mode.All)
    public void test(AccountBenchmark2 benchmark) throws InterruptedException {
        ExecutorService ex = Executors.newFixedThreadPool(2);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ex.submit(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            benchmark.b.transferTo(benchmark.a, 1);
        });
        ex.submit(() -> {
           try{
               countDownLatch.await();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           benchmark.a.transferTo(benchmark.b, 1);
        });
        countDownLatch.countDown();
        ex.awaitTermination(1, TimeUnit.NANOSECONDS);
        ex.shutdown();
    }
}
