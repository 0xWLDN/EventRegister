pipeline {
    agent {
        docker {
            image 'docker:24.0.2-dind'
            args '-u root --privileged -v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    environment {
        MONGO_CONTAINER = 'mongo-db'
        APP_CONTAINER = 'eventregister-app-container'
        MONGO_PORT = '27017'
        APP_PORT = '8080'
        MONGO_DB_NAME = 'EventRegister'
    }

    stages {
        stage('Install Java & Maven, then Build JAR') {
            steps {
                sh '''
                    apk add --no-cache openjdk17 maven
                    mvn clean package -DskipTests
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t eventregister-app .'
            }
        }

        stage('Start MongoDB Container') {
            steps {
                // Remove existing container if exists
                sh "docker rm -f ${env.MONGO_CONTAINER} || true"
                // Run MongoDB container WITHOUT authentication
                sh """
                    docker run -d --name ${env.MONGO_CONTAINER} \\
                        -e MONGO_INITDB_DATABASE=${env.MONGO_DB_NAME} \\
                        -p ${env.MONGO_PORT}:${env.MONGO_PORT} mongo:6
                """
            }
        }

        stage('Run Application Container') {
            steps {
                // Remove existing app container if exists
                sh "docker rm -f ${env.APP_CONTAINER} || true"

                // Run app container with simple MONGO_URI without credentials
                sh """
                    docker run -d --name ${env.APP_CONTAINER} -p ${env.APP_PORT}:${env.APP_PORT} \\
                        --link ${env.MONGO_CONTAINER}:mongo \\
                        -e MONGO_URI="mongodb://mongo:${env.MONGO_PORT}/${env.MONGO_DB_NAME}" \\
                        eventregister-app
                """
            }
        }
    }
}
