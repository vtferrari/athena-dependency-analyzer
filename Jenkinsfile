#!/usr/bin/env groovy
import groovy.transform.Field
import groovy.json.JsonSlurper
import groovy.json.internal.LazyMap
import java.util.concurrent.TimeUnit

@Field String CUSTOM_TAG = new Date().format('yyyyMMdd-HHmm')
@Field String PROJECT = "athena-dependency-analyzer"
@Field String EUREKA_NAME = "athena"
@Field String GIT_ORGANIZATION = "netshoes"
@Field Boolean DOCKER_IMAGE_CREATED = false
@Field String NS_GITHUB_OAUTH_TOKEN = "5155e1441dad3be0e10912db6cd9292bd4b473ad"
@Field String NS_DOCKER_REGISTRY = "registry.netshoes.local:5000"
@Field String APPTAG = "TAG"
@Field String APP_FOLDER = "app"
@Field String DOCKERFILE_PATH = "Dockerfile"
@Field String MEMORY_LIMIT = "750M"
@Field String SECRET = "secret"
@Field String PARALLELISM = "1"
@Field String DELAY = "30s"
@Field int docker_port = 2376

class NSEnvironment implements Serializable {

    List<String> managers
    String env
    String customTag
    String tokenUri
    String secret
    String eurekaUri
    String branch
    String dockerCompose
    String urlEureka
    int maxInstances

    NSEnvironment(List<String> managers, String env, String customTag, String tokenUri, String secret, String eurekaUri, String branch, String dockerCompose, String urlEureka, int maxInstances) {
        this.managers = managers
        this.env = env
        this.customTag = customTag
        this.tokenUri = tokenUri
        this.secret = secret
        this.eurekaUri = eurekaUri
        this.branch = branch
        this.dockerCompose = dockerCompose
        this.urlEureka = urlEureka
        this.maxInstances = maxInstances
    }
}


def PRD = new NSEnvironment(
  /*managers*/      ["prd-swarm-01.netshoes.local", "prd-swarm-02.netshoes.local","prd-swarm-03.netshoes.local"],
  /*env*/           "PRD",
  /*customTag*/     TAG,
  /*tokenUri*/      "https://ws-gateway.ns2online.com.br/rest/security/oauth/token",
  /*secret*/        "51teSWOF",
  /*eurekaUri*/     "https://ws-config.ns2online.com.br",
  /*branch*/        "production",
  /*dockerCompose*/ "docker-compose.yml",
  /*urlEureka*/     "COLOCAR O VIP",
  /*maxInstances*/  1)

def clearWorkspace() {
    stage("Cleaning Workspace for ${PROJECT}") {
        sh 'pwd'
        deleteDir()
    }
}

def gitClone() {
    stage("Git Clone ${PROJECT}") {
        git credentialsId: '7106d5b0-1b2d-460a-b737-2d5d86d41779', url: "https://github.com/${GIT_ORGANIZATION}/${PROJECT}"
    }
}


def checkIfAppIsRunningIn(nsEnvironment) {
    stage("Checking if APP is UP $nsEnvironment.env") {
        int ATTEMPTS = 1
        int SLEEP_TIME_IN_SECONDS = 10
        int MAX_ATTEMPT = 30

        while (ATTEMPTS <= MAX_ATTEMPT) {
            try {
                def response = httpRequest nsEnvironment.urlEureka
                if( response.status == 200 ) {
                    println "App is Running in ${nsEnvironment.urlEureka}"
                    break
                } else {
                    println "App didn't start yet ${nsEnvironment.urlEureka}"
                }
            } catch (Exception e) {
                println "App didn't start yet in url ${nsEnvironment.urlEureka}, please wait..."
            }
            ATTEMPTS++
            sleep time: SLEEP_TIME_IN_SECONDS, unit: TimeUnit.SECONDS
            if (ATTEMPTS > MAX_ATTEMPT) {
                def totalTimeWaiting = (SLEEP_TIME_IN_SECONDS * ATTEMPTS)
                throw new RuntimeException("Failed to Start the application ${EUREKA_NAME} after $totalTimeWaiting seconds")
            }
        }
    }
}

def buildJava() {
    stage("Build Java application") {
        withMaven(maven: 'maven-3.3.9') {
            sh "cd ${APP_FOLDER} && mvn clean package"
        }
    }
}

def buildDockerImage() {
    stage("Create Docker Image Tag") {
        docker.build("${NS_DOCKER_REGISTRY}/${PROJECT}:${CUSTOM_TAG}", "-f ${DOCKERFILE_PATH} .")
        DOCKER_IMAGE_CREATED = true
    }
}

def pushDockerImageToRegistry() {
    stage("Publish Docker Image to Netshoes Registry") {
        sh """
            docker push '${NS_DOCKER_REGISTRY}/${PROJECT}:${CUSTOM_TAG}'
        """
    }
}

def startDockerIn(appEnv) {
    stage("Starting docker in ${appEnv.env}") {
        try {
            if (appEnv.env != "DEV") {
                ArrayList manager_ok = new ArrayList()
                for (String manager : appEnv.managers) {
                    try {
                        (new Socket(manager, docker_port)).close()
                        manager_ok.add(manager)
                    }
                    catch (SocketException e) {
                        println("Manager " + manager + " is down")
                    }
                }
                if (manager_ok.size() < 3) {
                    throw new RuntimeException("There's a problem in docker swarm cluster")
                }
                sh """
                    export DOCKER_HOST=${manager_ok[0]}:${docker_port}
                    if [[ \$(docker service ls | grep ${PROJECT} | wc -l) == 0 ]]
                    then
                        docker service create \
                        --name ${PROJECT} \
                        --replicas ${appEnv.maxInstances} \
                        --network host \
                        --constraint 'node.role != manager' \
                        --constraint 'node.labels.env == fixedport' \
                        --env MONGODB_URI=mongodb://athena:Vpcdlssn70s@prd-athena-mongodb-01.netshoes.local:27017/athena \
                        --env RABBITMQ_ADRESSES=prd-athena-rabbitmq-01.netshoes.local:15672 \
                        --GITHUB_HOST=api.github.com \
                        --GITHUB_ORGANIZATION=$GIT_ORGANIZATION \
                        --GITHUB_TOKEN=$NS_GITHUB_OAUTH_TOKEN \
                        --ADMIN_USERNAME=admin \
                        --ADMIN_PASSWORD=admin \
                        --env NODE_ENV="${appEnv.env}" \
                        --detach=false \
                        ${NS_DOCKER_REGISTRY}/athena:${appEnv.customTag}
                    else
                        docker service update \
                        --force \
                        --update-parallelism ${PARALLELISM} \
                        --update-delay ${DELAY} \
                        --detach=false \
                        --image ${NS_DOCKER_REGISTRY}/athena:${appEnv.customTag} \
                        ${PROJECT}
                    fi
                """
            } else {
                sh """
                    export PROJECT=${PROJECT}
                    export ${APPTAG}=${appEnv.customTag}
                    docker-compose -f ${appEnv.dockerCompose} up -d
             """
            }
        }
        catch (Exception e) {
            println("Failed to start docker in ${appEnv}")
            println(e)
        }
    }
}

def notifyBuild() {
    stage("Notify Pipeline Finish") {
        def buildStatus = currentBuild.result
        def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
        def summary = "${subject} (${env.BUILD_URL})"
        def yellow = "#FFFF00"
        def green = "#00FF00"
        def red = "#FF0000"

        def colorCode
        if (buildStatus == 'STARTED') {
            colorCode = yellow
        } else if (buildStatus == 'SUCCESS') {
            colorCode = green
        } else {
            colorCode = red
        }
        slackSend(color: colorCode, message: summary)
    }
}

node("dev-shoestock") {
    if (environment == "PRD") {
        try {
            clearWorkspace()
            gitClone()

            buildJava()
            buildDockerImage()
            startDockerIn(PRD)
            pushDockerImageToRegistry()
            //checkIfAppIsRunningIn(PRD)

            currentBuild.result = "SUCCESS"
        }catch (Exception err) {
            println(err)
            currentBuild.result = "FAILURE"
         }
    } 
}
