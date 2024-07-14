Current API runs on port 8080 and has 2 endpoints:

- "/new account" : It accepts 2 parameters, "customerId" which is the ID of the customer and "initialCredit" which represents the amount that will be present on the new account in case it doesn't exceed customer's balance. Type of request: Post.
- "/customeinfo" : It accepts 1 parameter, "customerID" Which represents Id of the customer that the user wants to showcase the data of. Type of Request: Get.

On start up, the Application creates 4 users with the following data:

Id, Name, Surname, balance:

(1, 'John', 'Wick', 50000),
(2, 'John', 'Smith', 199.5),
(3, 'Peter', 'Parker', 97.5),
(4, 'Tony', 'Stark', 1000000);

I'm not very creative with names :)

Currently testable via Postman.
Runnable via an IDE that supports spring boot application.
