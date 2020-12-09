#!/bin/bash

lb=$(kubectl --context arn:aws:eks:ap-south-1:5634988257:cluster/eks-stage get svc ${next}-${app} -o=jsonpath="{.status.loadBalancer.ingress[0].hostname}")

curl http://$lb
