# Plan

HA
- client with multiple long-lived connections and >= 10 requests per second, shows metrics in CLI
- play "chaos monkey" and kill a few pods
- should have no impact on traffic

Show A/B deployment
- Ambassador as Helm chart
- A/B weight as value to Helm upgrade
- Service A is a Helm chart
- Service B is a Helm chart

Public TLS endpoint
- Front Ambassador with HAproxy ingress controller

GRPC load balancing
- show how requests are distributed evenly
- extend proto with metadata (which server replied) and show aggregate statistics in client

Auto REST-to-gRPC transcoding
- Show how the GRPC service can be used with HTTP/1.1 REST client
- only annotations in proto required
- no custom code for this!


