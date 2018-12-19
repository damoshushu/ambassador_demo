# Setup

## Namespace, RBAC and User setup
Here I'm assuming that we're using Docker for Windows/Mac. For other clusters, you need to find alternative ways to set this up. In the end, we should have
- A namespace called ambassador-poc
- A user called "test-user"
- A service account for tiller with full access to above namespace
- Tiller installed in ambassador-poc namespace
- kubectl configured to use "test-user" in namespace "ambassador-poc"

```bash
helm --tiller-namespace ambassador-poc upgrade --install a hello-world-service/.
helm --tiller-namespace ambassador-poc upgrade --install b hello-world-service/.
```

The following commands will install Ambassador via its Helm chart and override a couple of settings via ambassador-values.yaml. Overrides
- No RBAC or ServiceAccount creation. The Helm chart assumes a cluster-wide install and we only want it in one namespace
- 2 replicas instead of 1
- HTTP port 9090 instead of 80
- Disable HTTPS
- Set ID for Ambassador which we use in weights.yaml to match the Mapping to the Ambassador instance

```bash
kubectl apply -f ambassador-service-account.yaml
helm repo add datawire https://www.getambassador.io/helm
helm upgrade \
            --tiller-namespace ambassador-poc \
            --install ambassador\
            --wait \
            -f ambassador-values.yaml \
            datawire/ambassador
```

http://localhost:9090/ambassador/v0/diag/

