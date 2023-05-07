package com.example.asyncmethod;

public class CpuIntensiveOperations {
    public static void main(String[] args) throws InterruptedException {

        Runnable r1 = () -> {
            int iterations1 = 100000000;
            for (int i = 0; i < iterations1; i++) {
                // perform a CPU intensive operation
                double result = Math.sqrt(Math.random() * 1000000);
                //System.out.println("Iterations: " + i);
            }
            System.out.println("Thread -- " + Thread.currentThread().getName() + " has finished");

        };

        Thread t1 = new Thread(r1, "Thread-1");
        Thread t2 = new Thread(r1, "Thread-2");

        t1.start();
        t2.start();


        t1.join();
        t2.join();
        System.out.println("All threads have finished.");

    }
}
