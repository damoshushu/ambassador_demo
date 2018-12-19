package com.nuance.test;

import com.nuance.test.grpc.greeter.GreeterGrpc;
import com.nuance.test.grpc.greeter.HelloReply;
import com.nuance.test.grpc.greeter.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Client {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        System.out.println("Client started");

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        try {

            GreeterGrpc.GreeterBlockingStub greeter = GreeterGrpc.newBlockingStub(channel);

            // warmup
            greeter.sayHello(HelloRequest.newBuilder().setName("warmup").build());

            while (true) {
                int count = 10000;
                Map<String, Integer> pods = new HashMap<String, Integer>();

                System.out.format("Calling %d times\n", count);
                long start = System.nanoTime();
                int failures = 0;

                for (int i = 0; i < count; i++) {

                    HelloRequest request = HelloRequest
                            .newBuilder()
                            .setName(String.format("#%d", i))
                            .build();

                    HelloReply reply = null;

                    try {
                        reply = greeter.sayHello(request);
                    } catch (StatusRuntimeException ex) {
                        System.err.format("Error: %s\n", ex.getMessage());
                        failures ++;
                        continue;
                    }

                    String podName = reply.getPod();
                    pods.putIfAbsent(podName, 0);
                    pods.compute(podName, (key, value) -> value + 1);
                    System.out.print(podName.substring(podName.length() - 1, podName.length()));
                    if (i % 80 == 79) System.out.println();
                }

                System.out.println();

                long end = System.nanoTime();
                double timePerCall = (double) (end - start) / (count * 1000000.0);
                System.out.format("Average time per (sequential) call: %f ms\n", timePerCall);

                for (Map.Entry<String, Integer> entry : pods.entrySet()) {

                    System.out.format("Pod %s responded to %d of calls (%.2f %%)\n",
                            entry.getKey(),
                            entry.getValue(),
                            100.0 * entry.getValue() / count);

                }

                System.out.format("%d failures\n", failures);
            }

        } finally {

            System.out.println("Shutting down...");
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            System.out.println("Shutdown complete.");

        }



    }

}
