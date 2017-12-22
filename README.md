# Athena Dependency Analyzer

## Developing
The java application is available at app.

Inside of java app the code is available at `src/main/java` and the frontend files are in 
`src/main/frontend`.

### Running the backend
Run `Application` class from your IDE.

### Running the frontend
Go to `src/main/frontend` and run `npm start`. (Run `npm install` before that if it's the first time)

Now we should work with `http://localhost:9090` (this is where we'll see our live changes reflected)
 instead of `http://localhost:8080`.
 
### Run everything from Maven

    mvn generate-resources spring-boot:run

The Maven goal `generate-resources` will execute the frontend-maven-plugin to install Node
and Npm the first time, run npm install to download all the libraries that are not 
present already and tell webpack to generate our `bundle.js`. It's the equivalent of running `npm run build` or `npm start` on a terminal.

### Packaging

    mvn package

## Docker

### Building

    docker build . -t netshoes/athena-dependency-manager:1.0
  
### Running

    export TAG=1.0
    export GITHUB_HOST=api.github.com
    export GITHUB_ORGANIZATION=my_organization
    export GITHUB_TOKEN=my_token
    export ADMIN_USERNAME=admin
    export ADMIN_PASSWORD=admin
    
    docker-compose up 