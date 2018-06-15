# concur-interview-vijay
RESTful web service to post and get items.

# Instructions to run the code

I have created a simple spring-boot maven Project. Please follow
the steps below to checkout, build and run the code.

1. Checkout the code to a local directory

> git clone https://github.com/vijaykas/concur-interview-vijay.git

2. cd to the project directory and build code using maven

> cd concur-interview-vijay

> mvn clean install

3. Run the jar file generated under target folder. By default spring boot
application will start up on port 8080

> java -jar target/concur-items-rest-1.0.0.0-SNAPSHOT.jar

# Testing REST API's

You can either use curl command to call REST API's or I have added 
swagger dependency to test and document API's. swagger-ui provides a
simple UI to test REST API's (https://swagger.io/tools/swagger-ui/)

# Steps to test REST API's using Swagger UI

1. Once concur-items-rest-1.0.0.0-SNAPSHOT application has been
started, you can open the browser to go this url,
http://localhost:8080/swagger-ui.html
(I did not customize the UI, I'm just using their default view)

2. You should see "Items : Item Rest Controller", click on Show/Hide 
to see the list of API's

3. To test creating a new item, click on POST /items, provide sample 
data in itemDto field and click on "Try it out!" button. Scrolling down 
will show you the Response code along with other response header information.
Example:
{
  "item": {
    "id": 123,
    "timestamp": "2018-06-15T06:01:01.000Z"
  }
}

4. To test GET API, click on GET /items and click on "Try it out!" button.
Sample Response:
[
  {
    "item": {
      "id": 123,
      "timestamp": "2018-06-15T06:01:01.000Z"
    }
  },
  {
    "item": {
      "id": 124,
      "timestamp": "2018-06-15T06:01:01.053Z"
    }
  },
  {
    "item": {
      "id": 125,
      "timestamp": "2018-06-15T06:01:02.043Z"
    }
  }
]
