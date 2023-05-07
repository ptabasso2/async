package com.example.asyncmethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class AsyncNetTime {
    private static final Logger logger = LoggerFactory.getLogger(AsyncNetTime.class);

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        Runnable r1 = () -> {
                subprocessing();
                processing();
                System.out.println("Thread -- " + Thread.currentThread().getName() + " has finished with result: " + processing());
        };


        CompletableFuture<Void> task = CompletableFuture.runAsync(r1);
        CompletableFuture.allOf(task).join();


        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("Invocation successful\n");
    }

    public static double processing() {
        subprocessing();
        return subprocessing();
    }

    public  static double subprocessing(){
        int iterations1 = 100000;
        double result = 0;
        for (int i = 0; i < iterations1; i++) {
            // perform a CPU intensive operation
            result = Math.sqrt(Math.random() * 1000000);
            //System.out.println("Iterations: " + i);
        }
        return result;
    }
}
