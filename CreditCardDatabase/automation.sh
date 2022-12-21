#! /bin/zsh
mvn package -DskipTests
docker build -t credit-card-db:latest .
kubectl delete -f creditcarddb-setup.yaml
kubectl apply -f creditcarddb-setup.yaml
