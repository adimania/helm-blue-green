# Helm Blue-Green Deployment

This helm chart consist of the following objects:
- Blue deployment
- Green deployment
- A main service for user traffic
- A blue service for testing blue deployment
- A green service for testing green deployment

## How to do first deployment?
Edit values.yml (or create custom) and update the image name, tags, app etc. For the first deployment, the blue and the green tag can be the same.
#### Note that for the Jenkins pipelines to work well, it is advisable to set the app name in values.yaml same as the helm commandline
Execute first deployment:
```
helm install <app_name> --set blue.enabled=true .
```

## How to do subsequent deployment?
If the current user traffic is being served by Blue, then our goal is to deploy update the green cluster, test it and shift traffic. 
Step 1: Update green cluster by changing the tag in the values.yaml. Then update the helm deployment:
```
helm upgrade nginx-bg --set blue.enabled=true .
```
Note that in the step above, still blue is enabled. So blue will continue to serve traffic while green is being updated to new tag.
Step 2: Execute tests on the green-{{ app }} service. 
Step 3: If the tests pass, enable green.
```
helm upgrade nginx-bg --set green.enabled=true .
```

# Jenkins Pipeline
The file Jenkinsfile.original can be used as a pipeline. Just add it to the application repo. It has three stages:
1. *deploy next*: finds the current color and deploys the next color. It does not switch the traffic though. 
2. *test the service*: The Jenkinstest.sh should be put in every repo and can be used to call curl commands or anything else to execute a test.
3. *change color*: If everything passes, update the color so that traffic passes on to the next color.

# Jenkins Library
This repo can be used as a library. The code is in src/ dir
1. Go to <jenkins_url>/configure and configure this repo as Global Pipeline Libraries.
2. Create the Jenkinsfile in app repo similar to the Jenkinsfile in this repo.
3. the stages are similar to the Pipeline described above.
