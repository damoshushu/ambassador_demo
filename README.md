Current state
- hello-world/server successfully builds a Docker image
- hello-world/client is a work in progress, it currently creates requests in an endless loop
- charts/ambassador contains a fixed chart. The official chart doesn't handle AMBASSADOR_SINGLE_NAMESPACE correctly
- charts/hello-world-service is a Helm chart for the hello-world GRPC service
- charts/weights.yaml configured the A/B behavior in Ambassador
- hello-world uses a headless service
- Ambassador does a nice round-robin load balancing
- Readiness probe in GRPC server pods has been adjusted so that 
  - pod starts failing health check and thus readiness probe when requested to terminate
  - pod still serves traffic normally for the next 35 seconds because the readiness probe takes time to propagate through headless service DNS (probe time plus 30 seconds TTL)
  - after that, it shuts down normally
- Works without cluster role
  - disabled RBAC and ServiceAccount creation in Helm chart, manually creating Role, RoleBinding and ServiceAccount. 
- Pod eviction of Ambassador
  - Single request fails due to closing connection 
  - New connection immediately works

TODO:
- Auto GRPC-to-JSON transcoding

