# Bulgarian State Railways ticket portal (In development)

### Overview:
<hr>

Clone of the train system used by the Bulgarian State Railways.

Lets you create routes, trains, upload train stations by coordinates (latitude and longitude). 

The system supports ticket discounts based on the client's profile and applies outside peak hour discounts.

The application is deployed at: https://bdz-ticket-portal-production.up.railway.app/user/login


### Technologies:
<hr>

* Java and Gradle
* SpringBoot and more concrete:
    * Spring Data JPA
    * Spring Security
* PostgreSQL
* Lombok
* Thymeleaf
* Flyway
* Deployed on Railway


### Installation and Getting Started:
<hr>

Installation:
Register as a new user

Search for trains at:
https://bdz-ticket-portal-production.up.railway.app/trains

Click on "Click here for more information about the train"

Click on "Click here for more information about the train carriage" for the train carriage you prefer

Click on "Reserve seat" on the seat you would like to reserve


#### Note!
Some of the functionality is not enabled for users.

You have to be logged in as admin

Use the following account for that:

email: admin@admin.com

password: 123

### Bussines requirments:
<hr>

* The system must allow management of user accounts
* The system must allow reservation and purchase of tickets for train
* The system must apply discounts on tickets depending on rules 
* The system must allow management of trains, train stops and routes
