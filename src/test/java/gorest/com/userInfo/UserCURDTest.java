package gorest.com.userInfo;

import gorest.com.model.UserPojo;
import gorest.com.testbase.TestBase;
import gorest.com.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class UserCURDTest extends TestBase {
    static String name = "Sachin" + TestUtils.getRandomValue();
    static String gender = "male";
    static String status = "active";
    static String email = TestUtils.getRandomValue() + "xyz@gmail.com";
    static int userId;

    @Title("This will create a new student")
    @Test
    public void test001() {
        UserPojo userPojo= new UserPojo();
        userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);

        Response response = given()
                .header("Authorization","Bearer 72d70b64d5532cce2c74a8198603ebe97bf36b9002a3b6addb576cb98809967c")
                .contentType(ContentType.JSON)
                .when()
                .body(userPojo)
                .post();
        response.then().log().all().statusCode(201);
        userId= response.body().jsonPath().getInt("id");

    }
    @Title("Verify if the user was added to the application")
    @Test
    public void test002() {
        System.out.println(userId);
        Response response = given()
                .when()
                .get("/2565278");
        response.then().log().all().statusCode(200);

    }
    @Title("Update the user information and verify the updated information")
    @Test
    public void test003() {

        UserPojo userPojo= new UserPojo();
        userPojo.setStatus("inactive");
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .body(userPojo)
                .patch("/2565278");
        response.then().log().all().statusCode(200);
    }
    @Title("Delete the user and verify if the user is deleted!")
    @Test
    public void test004() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/2565278");
        response.then().log().all().statusCode(200);
    }


}