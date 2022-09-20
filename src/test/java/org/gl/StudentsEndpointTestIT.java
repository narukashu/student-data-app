package org.gl;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
public class StudentsEndpointTestIT {
    @Test
    public void testAllStudents() {

         //Create the Pear:
         given()
         .when()
         .body("{\"names\":\"Shubham\",\"age\":21}")
         .contentType("application/json")
         .post("/students")
         .then()
         .statusCode(201);

       
        //get User using Id
        given() 
                .when().get("/students/1")
                .then()
                .statusCode(200)
                .body(containsString("Shubham"));

       /*  //Get the Student Status Pass or Fails
        given()
                .when().get("/students/Average/21")
                .then()
                .statusCode(200)
                .body(containsString("Fail")); */

        //update the student
        given() 
                .when()
                .body("{\"id\" : 1,\"names\":\"Yash\",\"age\":21}")
                .contentType("application/json")
                .put("/students/1")
                .then()
                .statusCode(204);

        
        //Delete the student:
        given()
                .when().delete("/students/1")
                .then()
                .statusCode(204);
                

    }

}

