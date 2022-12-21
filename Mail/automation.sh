#! /bin/zsh
mvn package -DskipTests
docker build -t mail:latest .
kubectl delete -f mail-setup.yaml
kubectl apply -f mail-setup.yaml
