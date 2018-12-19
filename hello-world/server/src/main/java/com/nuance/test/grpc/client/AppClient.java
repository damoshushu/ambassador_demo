/*
 * ---------------------------------------------------------------------------
 *
 * COPYRIGHT (c) 2018 Nuance Communications Inc. All Rights Reserved.
 *
 * The copyright to the computer program(s) herein is the property of
 * Nuance Communications Inc. The program(s) may be used and/or copied
 * only with the written permission from Nuance Communications Inc.
 * or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program(s) have been supplied.
 *
 * ---------------------------------------------------------------------------
 */
package com.nuance.test.grpc.client;

import com.nuance.test.grpc.greeter.GreeterGrpc;
import com.nuance.test.grpc.greeter.HelloReply;
import com.nuance.test.grpc.greeter.HelloRequest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;

@SpringBootApplication
public class AppClient implements CommandLineRunner {

    @GrpcClient("hello")   // server host/port is defined in application.properties
    private GreeterGrpc.GreeterBlockingStub greeterStub;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppClient.class, args);
    }

    @Override
    public void run(String... args) {

        HelloReply response = greeterStub.sayHello(
            HelloRequest.newBuilder().setName("Nuance Client").build());
        System.out.println("Got gRPC response: " + response.getMessage());
    }
}
