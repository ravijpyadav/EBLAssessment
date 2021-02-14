# EBLAssessment

## To Build this repo please follow below steps


1. clone the report
   `git clone https://github.com/ravijpyadav/EBLAssessment.git`
2. Go to project directory `cd EBLAssessment`
3. To make it a maven project run `mvn eclipse:clean eclipse:eclipse` if using eclipse, if using intelliJ use `mvn idea:clean idea:idea`
4. run maven command to compile test and install the project in one go `mvn clean install`

After this you should have your project build with Junit tests ran and as jacoco pluning is configured, you should also have the coverage report generated in target/site/jacoco.

    you can open the index.html to see it.
5. if you want to run the application on your machine then run `mvn spring-boot:run`


To run it in docker contaniner follow blow steps.

     a. run `docker build -t ebl/ebl-demo .`
     b. then run `docker run -d -p 8080:8080 ebl/ebl-demo`
     
     Now you services are up and running in docker container.
     
     
 6. As service is up and running, you can access following REST APIs.
 
 
  All the APIs are secured with spring security with JWT ( Java Web Token)
  
  So first register a User.
  
      a. Register URL : http://localhost:8080/users
         Method: POST
         Payload :  {
                      "email": "ravi.yadav@ebl.com",
                      "name": "Ravi Yadav",
                      "age":35,
                      "password": "the-Secret-123"
                    }
        Response:  {
                     "jwt":            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYXZpLnlhZGF2QGVibC5jb20iLCJleHAiOjE2MTMyODg2NDZ9.joOWEcQegod15fynPRwyhsJF_qizNEWwTuhDQymnpTtun3SfSDs4JhXAc_n3N98vhnyMN-lVuaSQrbrwIkp3UQ",
                    "refresh_token": "87208688-a063-46f6-955c-bd2c62c0a5e0"
                   }
                   
                   
              jwt token must be assed in all secure APIs in header with key `X-Access-Token`   and access_token is used to refresh the token and get a new one.
              
  
  
  
  
 *****************         **In subsequent calls actual jwt and access_token generate in your env MUST be used**              *****************
              
              
     b. To see current logged in User
        URL : http://localhost:8080/me
        Method: GET
        Request Header Key: X-Access-Token , value: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYXZpLnlhZGF2QGVibC5jb20iLCJleHAiOjE2MTMyODg2NDZ9.joOWEcQegod15fynPRwyhsJF_qizNEWwTuhDQymnpTtun3SfSDs4JhXAc_n3N98vhnyMN-lVuaSQrbrwIkp3UQ
        Response: {
                    "name": "Ravi Yadav",
                    "email": "ravi.yadav@ebl.com"
                  }
                   
                   
                   
      c. To refresh and get new token  -- in case token gets compromized
         URL : http://localhost:8080/access-tokens/refresh
         Method: POST
         Request Header Key: X-Access-Token , value: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYXZpLnlhZGF2QGVibC5jb20iLCJleHAiOjE2MTMyODg2NDZ9.joOWEcQegod15fynPRwyhsJF_qizNEWwTuhDQymnpTtun3SfSDs4JhXAc_n3N98vhnyMN-lVuaSQrbrwIkp3UQ
         Payload : {
                     "refresh_token": "87208688-a063-46f6-955c-bd2c62c0a5e02"
                   }
       Response : {
                    "jwt":             "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYXZpLnlhZGF2QGVibC5jb20iLCJleHAiOjE2MTMyOTU4Nzl9.U1NsX56lVln4AzjfM3IDnMjG_H5LfgShVB_fspsYjV9nRbuglq_LQeoRnE0Z6ydbu_JF3UAq0dhNr32btBT9eg"
                  }
       
       
       d. if User is logged out or token gets expired, User can again login with username and password.
          URL : http://localhost:8080/access-tokens
          Method: POST
          Payload : {
                      "email": "ravi.yadav@ebl.com",
                      "password": "the-Secret-123"
                    }
                   
         Response : {
                      "jwt":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYXZpLnlhZGF2QGVibC5jb20iLCJleHAiOjE2MTMyOTM1Nzd9.69jnvor1wbcu4WofLYzXBCIwGsNZ-aSV4zB5z1WoPkTfOrjpIovHJTGLHD-fOaDXCXPE75WpxO3YHhG3M-k5zQ",
                      "refresh_token":"5259f379-f971-4f56-a571-d08b2a6b56ea"
                    }          
                    
                    
        e. After Login Create Person
           URL : http://localhost:8080/persons
           Method : POST
           Request Header Key: X-Access-Token , value: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYXZpLnlhZGF2QGVibC5jb20iLCJleHAiOjE2MTMyOTM1Nzd9.69jnvor1wbcu4WofLYzXBCIwGsNZ-aSV4zB5z1WoPkTfOrjpIovHJTGLHD-fOaDXCXPE75WpxO3YHhG3M-k5zQ
           Payload : {
                       "first_name": "John",
                       "last_name": "Keynes",
                       "age": "29",
                       "favourite_colour": "red"
                     }
           Response: {
                       "id": 2,
                       "age": 29,
                       "first_name": "John",
                       "last_name": "Keynes",
                       "favourite_colour": "red",
                       "created_at": "2021-02-14T05:36:11.220+0000"
                     }
        
        f. Read Entities
           URL : http://localhost:8080/persons?page=1          Note: you need to pass page number, each page contains 10 records
           Method : GET
           Request Header Key: X-Access-Token , value: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYXZpLnlhZGF2QGVibC5jb20iLCJleHAiOjE2MTMyOTM1Nzd9.69jnvor1wbcu4WofLYzXBCIwGsNZ-aSV4zB5z1WoPkTfOrjpIovHJTGLHD-fOaDXCXPE75WpxO3YHhG3M-k5zQ
           
           Response : [
                        {
                          "id": 2,
                          "age": 29,
                          "first_name": "John",
                          "last_name": "Keynes",
                          "favourite_colour": "red",
                          "created_at": "2021-02-14T05:36:11.220+0000"
                       }
                    ]
        
        
        g. Update a Person
           URL : http://localhost:8080/persons/{id}
           Method: PUT
           Request Header Key: X-Access-Token , value: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYXZpLnlhZGF2QGVibC5jb20iLCJleHAiOjE2MTMyOTM1Nzd9.69jnvor1wbcu4WofLYzXBCIwGsNZ-aSV4zB5z1WoPkTfOrjpIovHJTGLHD-fOaDXCXPE75WpxO3YHhG3M-k5zQ
           Payload: {
                      "first_name": "John",
                      "last_name": "Keyneswwwwww",
                      "age": "29",
                      "favourite_colour": "red"
                   }
           Response: {
                       "id": 2,
                       "age": 29,
                       "first_name": "Keyneswwwwww",
                       "last_name": "Keynes",
                       "favourite_colour": "red",
                       "created_at": "2021-02-14T09:53:41.117+0000"
                     }
                   
        h. Delete Entity
           URL : http://localhost:8080/persons/{id}
           Method : DELETE
           Request Header Key: X-Access-Token , value: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYXZpLnlhZGF2QGVibC5jb20iLCJleHAiOjE2MTMyOTM1Nzd9.69jnvor1wbcu4WofLYzXBCIwGsNZ-aSV4zB5z1WoPkTfOrjpIovHJTGLHD-fOaDXCXPE75WpxO3YHhG3M-k5zQ
