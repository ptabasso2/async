package com.example.asyncmethod;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;


public class ThreadPoolTaskExecutorService {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.initialize();

        Runnable r1 = () -> {
            int iterations1 = 100;
            double result = 0;
            for (int i = 0; i < iterations1; i++) {
                // perform a CPU intensive operation
                result = Math.sqrt(Math.random() * 1000000);
                //System.out.println("Iterations: " + i);
            }
            System.out.println("Thread -- " + Thread.currentThread().getName() + " has finished with result: " + result);

        };

        Supplier s1 = new Supplier() {
            @Override
            public Object get() {
                System.out.println("Thread -- " + Thread.currentThread().getName() + " has finished with result: " + 2);
                return 2;
            }
        };


        CompletableFuture<Void> task1Future = CompletableFuture.runAsync(r1, executor);
        CompletableFuture<Void> task2Future = CompletableFuture.runAsync(r1, executor);
        CompletableFuture<Void> task3Future = CompletableFuture.runAsync(r1, executor);


        //CompletableFuture<AtomicInteger> taskTest = test(executor);

        CompletableFuture<Integer> taskSupply = CompletableFuture.supplyAsync(s1, executor);


        CompletableFuture.allOf(task1Future, task2Future, task3Future, taskSupply).join();

        System.out.println("Get taskTest result: " + taskSupply.get());
        System.out.println("All tasks completed");
        executor.shutdown();

    }


    /*public static CompletableFuture<AtomicInteger> test(Executor ex){

        AtomicInteger val= new AtomicInteger();
        ex.execute(() -> {
            val.set(2);            // complete the future with the result
            System.out.println("Thread -- " + Thread.currentThread().getName() + " has finished");
        });

        return CompletableFuture.completedFuture(val);
    }*/
}
