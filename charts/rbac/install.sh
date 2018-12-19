#!/bin/bash

# Cluster admin access
kubectl config use-context docker-for-desktop

# Setup namespace, RBAC and User role bindings
kubectl apply -f ns-and-rbac.yaml

# Create user certificate
# https://docs.bitnami.com/kubernetes/how-to/configure-rbac-in-your-kubernetes-cluster/
kubectl apply -f ubuntu-hack.yaml
sleep 10
kubectl wait --timeout=60s --for condition=ready pod -l name=ubuntu-hack
mkdir -p pki
kubectl cp ubuntu-hack:/host/run/config/pki/ca.crt pki/ca.crt
kubectl cp ubuntu-hack:/host/run/config/pki/ca.key pki/ca.key
openssl genrsa -out employee.key 2048
openssl req -new -key employee.key -out employee.csr -subj "/CN=employee/O=nuance"
openssl x509 -req -in employee.csr -CA pki/ca.crt -CAkey pki/ca.key -CAcreateserial -out employee.crt -days 1000

# Create kubectl context for new user
kubectl config set-credentials employee --client-certificate=$(pwd)/employee.crt  --client-key=$(pwd)/employee.key
kubectl config set-context employee-context --cluster=docker-for-desktop-cluster --namespace=ambassador-poc --user=employee
kubectl --context=employee-context get pods

# Delete pod from above hack
kubectl delete pod ubuntu-hack

# Switch to employee context
kubectl config use-context employee-context

# Install Tiller
helm init --tiller-namespace ambassador-poc --service-account ambassador-poc-tiller-service-account
