package com.example.asyncmethod;


import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class AsyncController {

    private static final Logger logger = LoggerFactory.getLogger(AsyncController.class);

    @Autowired
    Tracer gobaltracer;

    @Value("#{environment['threadnum'] ?: '20'}")
    public int threadnum;

    @Value("#{environment['iteration'] ?: '10000'}")
    public int iteration;

    private static int iterations;

    private static Tracer tracer;

    @Value("#{environment['iteration'] ?: '10000'}")
    public void setIterationStatic(int iteration){
        AsyncController.iterations = iteration;
    }

    @PostConstruct
    public void setTracerStatic() {
        AsyncController.tracer = gobaltracer;
    }
    @RequestMapping("/invoke")
    public String invoke() throws InterruptedException, ExecutionException {
        // Start the clock
        long start = System.currentTimeMillis();


        Runnable r1 = () -> {
            try {
                subprocessing();
                processing();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };


        CompletableFuture<Void>[] tasks = new CompletableFuture[threadnum - 1];
        for (int i = 0; i < threadnum - 1; i++) {
            tasks[i] = CompletableFuture.runAsync(r1);
        }

        CompletableFuture.allOf(tasks).join();

        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("Invocation successful\n");
        return "Processing done...\n";
    }


    public static void processing() throws InterruptedException {

        Span parentSpan = tracer.spanBuilder("processing").
                setAttribute("span.type", "web").setAttribute("resource.name", "AsyncProcessing.processing").
                startSpan();

        try (Scope scope = parentSpan.makeCurrent()) {
            subprocessing();
            System.out.println("Thread -- " + Thread.currentThread().getName() + " has finished with result: " + subprocessing());
        } finally {
            parentSpan.end();
        }

    }

    public static double subprocessing(){

        double result = 0;
        Span childSpan = tracer.spanBuilder("subprocessing").
                setAttribute("span.type", "web").setAttribute("resource.name", "AsyncProcessing.subprocessing").
                startSpan();
        try (Scope scope = childSpan.makeCurrent()) {
            // do stuff
            for (int i = 0; i < iterations; i++) {
                // perform a CPU intensive operation
                result = Math.sqrt(Math.random() * 1000000);
                //System.out.println("Iterations: " + i);
            }
            } finally {
                childSpan.end();
        }
        return result;
    }
}