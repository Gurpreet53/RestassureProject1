import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class EmployeeAPITest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test(priority = 1)
    public void addEmployeeTest() {
        String requestBody = "{ \"name\": \"John Doe\", \"job\": \"Software Engineer\" }";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("John Doe"))
                .body("job", equalTo("Software Engineer"))
                .log().all();
    }

    @Test(priority = 2)
    public void retrieveEmployeeDetailsTest() {
        Response response = given()
                .queryParam("id", 2) // Assuming employee ID 2 exists
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .extract().response();

        String responseBody = response.asPrettyString();
        System.out.println("Employee Details:\n" + responseBody);
    }

    @Test(priority = 3)
    public void updateEmployeeDetailsTest() {
        String updatedRequestBody = "{ \"name\": \"Jane Doe\", \"job\": \"Senior Software Engineer\" }";

        given()
                .contentType(ContentType.JSON)
                .body(updatedRequestBody)
                .when()
                .put("/api/users/2") // Assuming employee ID 2 exists
                .then()
                .statusCode(200)
                .body("name", equalTo("Jane Doe"))
                .body("job", equalTo("Senior Software Engineer"))
                .log().all();
    }
}
