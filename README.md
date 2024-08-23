**Technology used
Spring boot version 3.3.2
MYSQL database
Swagger UI for API documentation
JWT for Security
JavaMailSender for email
Java 21
Intellij Idea Ide**

**How to run it**
Create MYSQL database and name it assessmentdb then put your database username and password in those fields.
You also need to generate an app password from your email and paste it so that the app can use it in sending mails to registered users.

**Authentication**
Before you can be able to use the endpoint, you need to register with a valid email address using this endpoint api/v1/auth/register.
You will need to check the source code for the required parameters. 
After a successful registration, a 6-digit code will be sent to the registered email address for authentication.
Open your email, copy the 6-digit code and call this other endpoint api/v1/auth/activate-account and then pass the token to it for account activation.
After successful account activation, call this other endpoint api/v1/auth/authenticate and then passed your email and password to it.
If the authentication passed, a signed JWT will be issued to you and the token is what you will use to perform any manipulation on 
the endpoint depending on the role(s) that was assigned to your token.
Note that the 6-digit code expires after 20 minutes. If after 20 minute you then try to activate account with it,
you will receive a message that the code has expired and a new 6-digit code will be generated and sent to your email address instantly.

**Swagger UI **
When you open the swagger, you need to provide your jwt to the Authorization header of the swagger in other for you
to be able to use it else you won’t be able to perform any manipulation there.
Your jwt expires after 24 hours.You can decide to increase the expiration time. When it expires, you need to login again with either Postman or any other tool with
your valid email and password and a new one will be generated to you.

**Curls for the project
Account Creation**
curl -X 'POST' \
  'http://localhost:8080/api/v1/auth/register' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "firstname": "Samuel",
  "lastname": "Sampson",
  "email": "elijah@gmail.com",
  "password": "samuel"
}'

**Token Activation**
curl -X 'GET' \
  'http://localhost:8080/api/v1/auth/activate-account/317973' \
  -H 'accept: */*'

  **Account Authentication**
  curl -X 'POST' \
  'http://localhost:8080/api/v1/auth/authenticate' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "email": "ukemedmet@gmail.com",
  "password": "elijah"
}'

**Suppliers query**
curl -X 'POST' \
  'http://localhost:8080/api/supplier/query' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "city": "Abuja",
  "natureOfBusiness": "Small_scale",
  "manufacturingProcesses": "moulding",
  "pageNumber": 0,
  "pageSize": 2
}'
