#! /bin/zsh
mvn package -DskipTests
docker build -t payment:latest .
kubectl delete -f payment-setup.yaml
kubectl apply -f payment-setup.yaml
