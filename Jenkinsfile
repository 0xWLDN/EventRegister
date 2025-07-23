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
        MONGO_INITDB_ROOT_USERNAME = ''
        MONGO_INITDB_ROOT_PASSWORD = ''
        MONGO_DB_NAME = 'EventRegister'
    }

    stages {
        stage('Install Java & Maven, then Build JAR') {
            steps {
                sh '''
                    apk add --no-cache openjdk24 maven
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
                // Run MongoDB container with env vars for username/password
                sh """
                    docker run -d --name ${env.MONGO_CONTAINER} \\
                        -e MONGO_INITDB_ROOT_USERNAME=${env.MONGO_INITDB_ROOT_USERNAME} \\
                        -e MONGO_INITDB_ROOT_PASSWORD=${env.MONGO_INITDB_ROOT_PASSWORD} \\
                        -e MONGO_INITDB_DATABASE=${env.MONGO_DB_NAME} \\
                        -p ${env.MONGO_PORT}:${env.MONGO_PORT} mongo:6
                """
            }
        }

        stage('Run Application Container') {
            steps {
                // Remove existing app container if exists
                sh "docker rm -f ${env.APP_CONTAINER} || true"

                // Run app container with environment variables to connect to MongoDB
                // Assuming your app reads MONGO_URI or similar from env vars
                sh """
                    docker run -d --name ${env.APP_CONTAINER} -p ${env.APP_PORT}:${env.APP_PORT} \\
                        --link ${env.MONGO_CONTAINER}:mongo \\
                        -e MONGO_URI="mongodb://${env.MONGO_INITDB_ROOT_USERNAME}:${env.MONGO_INITDB_ROOT_PASSWORD}@mongo:${env.MONGO_PORT}/${env.MONGO_DB_NAME}?authSource=admin" \\
                        hello-app
                """
            }
        }
    }
}
