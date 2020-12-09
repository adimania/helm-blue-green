@Library("test-blue-green-lib@master")

import com.aditya.*
import hudson.model.*

node {
git url: 'git@github.com:devopscloudnexus/helm-blue-green.git'
pl = new pipeline()

pl.deploynext()
pl.testnext()
pl.updatecolor()
}
