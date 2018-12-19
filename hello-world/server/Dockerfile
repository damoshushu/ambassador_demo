FROM azul/zulu-openjdk-alpine:11
MAINTAINER Nikolai Grigoriev <nikolai.grigoriev@nuance.com>

EXPOSE 9090/tcp

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/helloworld_v2/helloworld_v2.jar"]
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/helloworld_v2/helloworld_v2.jar

RUN GRPC_HEALTH_PROBE_VERSION=v0.2.0 && \
    wget -qO/bin/grpc_health_probe https://github.com/grpc-ecosystem/grpc-health-probe/releases/download/${GRPC_HEALTH_PROBE_VERSION}/grpc_health_probe-linux-amd64 && \
    chmod +x /bin/grpc_health_probe
    