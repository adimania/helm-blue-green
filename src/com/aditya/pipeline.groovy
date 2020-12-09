#!/usr/bin/groovy
package com.aditya;

def deploynext() {
  sh '''
        echo current app is $app
        color=$(kubectl --context arn:aws:eks:ap-south-1:5634988257:cluster/eks-stage get svc main-$app -o=jsonpath="{.spec.selector.color}")
        echo $color > color
        echo "Current color $color"
        next=blue
        if [ "$color" = "blue" ]
        then
          next=green
        fi
        echo $next > next
        helm --kube-context arn:aws:eks:ap-south-1:5634988257:cluster/eks-stage upgrade $app --set $color.enabled=true .
        '''
}

def testnext() {
  sh '''
        color=$(cat color) next=$(cat next) bash Jenkinstest.sh 
        '''
}

def updatecolor() {
  sh '''
        color=$(cat color)
        next=$(cat next)
        helm --kube-context arn:aws:eks:ap-south-1:5634988257:cluster/eks-stage upgrade $app --set $next.enabled=true .
        '''
}

return this
