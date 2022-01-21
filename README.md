# Spring Boot Oauth2 Shopping Cart
This is a backend implementation project for a simple online shopping cart.

## Technology
Java 8, Spring Boot (version 2.1.2), RESTful, Oauth2, JPA 2.0, Swagger, Maven, MySql Database

## How it works
You need to first log in to system to be authorised to use this application. After successful login, you can create number of products in the system and add them as cart items to your shopping cart. You can also do this operation again in the same way. If you want, you can remove each item added to your cart too. There is a role permission definition in application which you can restrict the application access. In order to have a full functionality, I chose a user as an admin user who has all the necessary permissions to test the system.

## How to Run
1. update your database credentials in `application.properties` file.
2. run the file `Application.java` and wait until server started.
3. execute queries provided in `data.txt`.
4. for login, follow below images to set up admin credentials as part of header with authorization details of user by using POSTMAN:

   ![alt text](https://user-images.githubusercontent.com/17501289/150565088-33a3d648-e546-4257-b8ff-75a1e1cb57fc.png?raw=true)
   ![alt text](https://user-images.githubusercontent.com/17501289/150566643-0ab72068-878f-4634-bd2d-9d1c2bf28d9b.png?raw=true)

   then, hit the POST method **`http://localhost:8080/oauth/token`** and get the HTTP response like:

   ![alt text](https://user-images.githubusercontent.com/17501289/150566027-9cf9c45f-991d-4ee5-99be-6d5f2a37163b.png?raw=true)

5. for being authorised to call each end points, you should pass the **`access_token`** generated in the previous step as part of the request header:

   ![alt text](https://user-images.githubusercontent.com/17501289/150568129-0266be1e-5db7-4889-a663-becb1cdee63a.png?raw=true)

6. obviously, if you need to login with different user you are required to generate a new token mentioned in step 4.

## About the Service
### API information and documentation
To ses all API information, there is a `spring-boot-oauth2.postman_collection.json` file which this file should import on postman for testing. Also, to view Swagger API documentation, You need to run the server and browse to http://localhost:8080/swagger-ui.html
