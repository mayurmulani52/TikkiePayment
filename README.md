# TikkiePayment
Tikkie Payment Backend APIs which allows users to create, retrieve and search for Payments. This sample project is configured with Tikkie sandbox Payment Gateway. 

API Yaml file is residing under /yaml directory where swagger document is attached to invoke the APIs

Mongo DB is used to store audit of payment requests regardless success or failure with API integration. Number of tables are stored in Mongo DB : Audit, AuditOfPaymentRequest, AuditExternalAPI

This project has been configured with Java with CI gradle Pipeline on Github so on every push git command, Pipeline will be triggered to build the project by having this can check project build is failing or not.

All end rest points are configured with Okta OAuth2.0. Configuration with okta has been done with encryption parameters in application.properties file.

Tikkie payment API gateway also configured to integrate payment apis.

Postman Json structure is kept inside /postman-json directory to test apis through postman client


#### Building for source

For local development:
```sh
./gradlew clean build docker -x test
```
## Install Lombok
Project uses lombok to generate getter, setter, constructors and other helpful stuff. 

<img src="https://projectlombok.org/img/lombok-installer.png" width="450">