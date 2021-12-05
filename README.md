# use-csv-upload
Rest API to upload users details using csv file and provide basic crud operations in H2 DB.

How to run project:
mvn spring-boot:run

Use sample.csv to import users 

## API Endpoints
1. localhost:8080/upload POST\
    Upload users from csv file
2. localhost:8080/users GET\
    Get all users
3. localhost:8080/users?min=2000&max=3000 GET\
    Get users whose salary is between min and max
4. localhost:8080/users?offset=0 GET\
    GET usres by setting offset
5. localhost:8080/users?sort=SALARY GET\
    GET users by sorting by NAME or SALARY

## Test Cases
1. If the csv file format is not correct, for example, salary is not a valid double, it will return\
{
  "Message": "CSV is in wrong format",
  "Success": 0
}

