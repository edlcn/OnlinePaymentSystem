#! /bin/zsh
mvn package -DskipTests
docker build -t merchant:latest .
kubectl delete -f merchant-setup.yaml
kubectl apply -f merchant-setup.yaml
