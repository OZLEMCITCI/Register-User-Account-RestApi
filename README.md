# Register and Login RestApi
A register API allows you to register a system with valid credentials.

A login API aloows you to login system with registired username and password.

#### Technologies
- Java
- SpringBoot
- PostGre SQL
- JWT

#### How to Run
```python
mvn package

java -jar target/SpringJWT1-1.0.0-SNAPSHOT.jar server banking.yml

```

Appliaction starts on localhost port 8080 An H2 in memory database initilized with some sample transfer and account data 

- [http://localhost:8080/api/auth/register]
- [http://localhost:8080/api/auth/login]

#### Available Services

| Http Method   |    Path             |   Usage    |
| ------------- | -------------       | ---------- |
| POST          | /api/auth/register  |  register new account          |
| Content Cell  | /api/auth/login     |  login the account with JWT token          |

#### Http Status
``` python
200 OK - Everything worked as expected
204 No Content - Everything worked as expected and not additional content was sent back
404 Not Found - The requested resource does not exist
422 Unprocessable Entity - The request might have missing / incorrect parameters or failed certain conditions
```

## Account Resources
### POST/register

#### Request
``` pyhton
POST /api/auth/register
```

#### Body
``` pyhton
{
   "firstname":"abcd",
   "lastname":"abcdf",
   "username":"abcd123",
   "ssn":"000-00-0000",
   "password":"123",
   "email":"abcd@gail.com",
   "mobilePhoneNumber":"000-000-0000",
   "address":"123 abcd ave. ABC/TX 00000",
   "roles":["user","admin"]
}
```

#### Response
``` pyhton
New User is registered successfully
```

### POST/login

#### Request
``` pyhton
POST /api/auth/login
```

#### Body
``` pyhton
{
    "username":"krlmp",
    "password":"123"
}
```

#### Response
``` pyhton

{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrcmxtcCIsImlhdCI6MTYzMDk1MTkzOCwiZXhwIjoxNjMxMDM4MzM4fQ.1x7HxG675zL68FrtNa9HUPqM5uGlDsD94c-E_I2mO-EpqphHAR69xhVqyU-fWdeUh13PpdH6A6nzVgqa49d_wQ",
    "type": "Bearer",
    "id": 15,
    "ssn": "000-00-0000",
    "firstname": "abcd",
    "lastname": "abcdf",
    "username": "abcd123",
    "email": "abcd@gail.com",
    "roller": [
        "ROLE_USER",
        "ROLE_ADMIN"
    ]
}

```




