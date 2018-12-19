package com.nuance.test.grpc.server;

import com.nuance.test.grpc.greeter.GreeterGrpc;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.sbb.esta.openshift.gracefullshutdown.IProbeController;
import io.grpc.health.v1.HealthCheckResponse.ServingStatus;
import io.grpc.services.HealthStatusManager;

@Component
public class GrpcProbeController implements IProbeController {

    private static final Log log = LogFactory.getLog(GrpcProbeController.class);

    @Autowired
    private HealthStatusManager healthStatusManager;

    @Override
    public void setReady(boolean ready) {
        log.info("Disabling gRPC service " + GreeterGrpc.SERVICE_NAME);
        healthStatusManager.setStatus(GreeterGrpc.SERVICE_NAME, ServingStatus.NOT_SERVING);
    }

}


