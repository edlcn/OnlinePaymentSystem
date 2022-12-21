#! /bin/zsh
mvn package -DskipTests
docker build -t users:latest .
kubectl delete -f users-setup.yaml
kubectl apply -f users-setup.yaml
