# gesporte-crawler
This is a [Globo Esporte](https://globoesporte.globo.com/) web-crawler.

It will keep you updated for the next days about soccer matches, with the championship, date and time, sou you won't
lose any match result. Time to cheer for your team!

> Informations about championships, teams and matches are all Globo Esporte responsability.

## Configuring in your machine
For you to run this application, you will need to follow these instructions.

Start with cloning this repo into a folder. Go to a folder you want to clone this repo and use:
```
git clone git@github.com:joaopedroguimaraes/gesporte-crawler.git
```

Now, you'll need [Maven](https://maven.apache.org/) installed. I recommend you use IntelliJ IDEA, once Maven comes
with installation.

You'll need Java 8 JDK, too. You can download and install it [here](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).

Once you get here, open the project repo and add a file named "application.properties" inside this path:

```
src\main\resources\
```

This file is responsible for DB connection. Add these lines with your own configurations for MySQL:
```
## Spring DATASOURCE (DataSourceAutoConfiguration & DataSourceProperties)
spring.datasource.url = jdbc:mysql://[IP OR LOCALHOST]:[PORT]/[DATABASE NAME]
spring.datasource.username = [MYSQL USER]
spring.datasource.password = [PASSWORD FOR THIS USER]


## Hibernate Properties
# The SQL dialect makes Hibernate generate better SQL for the chosen database
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect

# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto = update
```

Now, open your cmd, go to the project root folder and do:

```
mvn clean install
```

Wait until it is finished and do:
```
mvn spring-boot:run
```

## What we love

This project was developed using the following tools:
- Java 1.8
- Spring Boot 2.1.3
- HTMLUnit 2.33
- IntelliJ IDEA Community 2018.3.4
- MySQL 5.7.24-0ubuntu0.18.04.1
- Maybe some tool I forgot
- Spotify (to get inspired)