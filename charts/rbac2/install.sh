#!/bin/bash

#TODO use reflection to get current cluster
export CLUSTER_NAME=christian-test-cluster
export NAMSPACE_NAME=ambassador-poc

# change context to get cluster admin permissions
kubectl config use-context $CLUSTER_NAME
kubectl delete namespace $NAMESPACE_NAME

# Setup namespace, RBAC and User role bindings
kubectl apply -f ns-and-rbac.yaml

# get secret name of service account
export SECRET_NAME=$(kubectl -n "$NAMESPACE_NAME" get secret |grep test-user | cut -d ' ' -f 1)
export TOKEN=$(kubectl -n "$NAMESPACE_NAME" get secret "$SECRET_NAME" -o jsonpath={.data.token} | base64 -d)

# Create kubectl context for new user
kubectl config set-credentials test-user --token $TOKEN
kubectl config set-context test-user-context --cluster=$CLUSTER_NAME --namespace=$NAMESPACE_NAME --user=test-user
kubectl --context=test-user-context get pods

# Switch to test-user context
kubectl config use-context test-user-context

# Install Tiller
helm init --tiller-namespace $NAMESPACE_NAME --service-account helm


