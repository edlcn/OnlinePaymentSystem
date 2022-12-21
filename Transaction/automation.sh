#! /bin/zsh
mvn package -DskipTests
docker build -t transaction:latest .
kubectl delete -f transaction-setup.yaml
kubectl apply -f transaction-setup.yaml
