package com.nuance.test.grpc.server;

import com.nuance.test.grpc.greeter.GreeterGrpc;
import com.nuance.test.grpc.greeter.HelloReply;
import com.nuance.test.grpc.greeter.HelloRequest;

import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;

@GrpcService
public class GreeterService extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
      String podName = System.getenv("POD_NAME");
      podName = (podName != null ? podName : "POD_NAME");

      HelloReply reply = HelloReply
                          .newBuilder()
                          .setMessage("Hello " + req.getName())
                          .setPod(podName)
                          .build();
      responseObserver.onNext(reply);
      responseObserver.onCompleted();
    }
}
