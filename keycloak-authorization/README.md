# Security Keycloak Authorization

This project seeks to provide a simple guide on using multi-tenant for SaaS applications.

An in-depth approach that uses one of the opinionated approaches for "multi-tenant Keycloak" can be
found [here](https://github.com/p2-inc/keycloak-orgs). You are advised to read through.

## Requirements

To compile and run this demo you will need:

- JDK 11+
- GraalVM

In addition, you will need either a MySQL database, or Docker to run one.

### Configuring GraalVM and JDK 11+

Make sure that both the `GRAALVM_HOME` and `JAVA_HOME` environment variables have
been set, and that a JDK 11+ `java` command is on the path.

See the [Building a Native Executable guide](https://quarkus.io/guides/building-native-image)
for help setting up your environment.

## Building the demo

Launch the Maven build on the checked out sources of this demo:

> ./mvnw package

## Running the demo

### Prepare a MySQL instance

Make sure you have a MySQL instance running. To set up a MySQL database with Docker:

> docker run -it --rm=true --name quarkus_test -e MYSQL_USER=inventory_test -e MYSQL_ROOT_PASSWORD=inventory_test
> -e
> MYSQL_DATABASE=inventory_test -p 5432:5432 postgres:13.3

Connection properties for the Agroal datasource are defined in the standard Quarkus configuration file,
`src/main/resources/application.properties`.

### Live coding with Quarkus

The Maven Quarkus plugin provides a development mode that supports
live coding. To try this out:

> ./mvnw quarkus:dev

In this mode you can make changes to the code and have the changes immediately applied, by just refreshing your
browser.

    Hot reload works even when modifying your JPA entities.
    Try it! Even the database schema will be updated on the fly.

### Run Quarkus in JVM mode

When you're done iterating in developer mode, you can run the application as a
conventional jar file.

First compile it:

> ./mvnw package

Then run it:

> java -jar ./target/quarkus-app/quarkus-run.jar

    Have a look at how fast it boots.
    Or measure total native memory consumption...

### Run Quarkus as a native application

You can also create a native executable from this application without making any
source code changes. A native executable removes the dependency on the JVM:
everything needed to run the application on the target platform is included in
the executable, allowing the application to run with minimal resource overhead.

Compiling a native executable takes a bit longer, as GraalVM performs additional
steps to remove unnecessary codepaths. Use the  `native` profile to compile a
native executable:

> ./mvnw package -Dnative

After getting a cup of coffee, you'll be able to run this binary directly:

> ./target/hibernate-reactive-panache-quickstart-1.0.0-SNAPSHOT-runner

    Please brace yourself: don't choke on that fresh cup of coffee you just got.
    
    Now observe the time it took to boot, and remember: that time was mostly spent to generate the tables in your database and import the initial data.
    
    Next, maybe you're ready to measure how much memory this service is consuming.

N.B. This implies all dependencies have been compiled to native;
that's a whole lot of stuff: from the bytecode enhancements that Panache
applies to your entities, to the lower level essential components such as the PostgreSQL JDBC driver, the Undertow
webserver.

### Starting and Configuring the Keycloak Server

Do not start the Keycloak server when you run the application in a dev
mode. [Dev Services for Keycloak](https://quarkus.io/guides/security-keycloak-authorization#keycloak-dev-mode) will
launch a container.

To start a Keycloak Server in production, you can run the following command:
> docker run --name keycloak -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -p 8543:8443 -v "$(pwd)"
> /config/cert.pem:/etc/cert.pem -v "$(pwd)"/config/key.pem:/etc/key.pem quay.io/keycloak/keycloak start
> --hostname-strict=false --https-certificate-file=/etc/cert.pem --https-certificate-key-file=/etc/key.pem

**NB:** Visit [this site](https://www.suse.com/support/kb/doc/?id=000018152) to create a .pem file for SSL
Certificate
installations

## See the demo in your browser

Navigate to:

<http://localhost:8080/index.html>

Have fun, and join the team of contributors!

## Running the demo in Kubernetes

This section provides extra information for running both the database and the demo on Kubernetes.
As well as running the DB on Kubernetes, a service needs to be exposed for the demo to connect to the DB.

Then, rebuild demo docker image with a system property that points to the DB.

```bash
-Dquarkus.datasource.reactive.url=mysql://<DB_SERVICE_NAME>/quarkus_test
```