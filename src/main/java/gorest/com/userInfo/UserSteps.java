package gorest.com.userInfo;
import gorest.com.constants.EndPoints;
import gorest.com.model.UserPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;


public class UserSteps {
    @Step("Creating user with name : {0},  email : {1}, gender : {2} and status : {3}")
    public ValidatableResponse createUser( String name, String email,String gender, String status)
                                             {
        UserPojo userPojo = UserPojo.getUserPojo( name, email, gender, status);

        return  SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .body(userPojo)
                .post()
                .then();
    }
    @Step("Getting the user information with name : {0}")
    public HashMap<String, Object> getUserInfoByName(String Name){
        String s1 = "findAll{it.firstName == '";
        String s2 = "'}.get(0)";
        return SerenityRest.given()
                .when()
                .get(EndPoints.CREATE_USER)
                .then().statusCode(200)
                .extract()
                .path(s1 + Name + s2);
    }

    @Step("Getting the user information with email : {0}")
    public HashMap<String, Object> getuserInfoByEmail(String email){
        String s1 = "findAll{it.email == '";
        String s2 = "'}.get(0)";
        return SerenityRest.given()
                .when()
                .get(EndPoints.GET_ALL_USERS)
                .then().statusCode(200)
                .extract()
                .path(s1 + email + s2);
    }

    @Step("Updating user information with studentId: {0}, firstName: {1}, lastName: {2}, email: {3}, programme: {4} and courses: {5}")
    public ValidatableResponse updateStudent( int userId, String name, String email,String gender, String status
                                             ){
        UserPojo userPojo = UserPojo.getUserPojo( name, email, gender, status);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("userId", userId)
                .body(userPojo)
                .when()
                .put(EndPoints.UPDATE_USER_BY_ID)
                .then();
    }
    @Step("Deleting user information with studentId: {0}")
    public ValidatableResponse deleteStudent(int studentId){
        return SerenityRest.given().log().all()
                .pathParam("userId", studentId)
                .when()
                .delete(EndPoints.DELETE_USER_BY_ID)
                .then();
    }

    @Step("Getting user information with studentId: {0}")
    public ValidatableResponse getStudentById(int userId){
        return SerenityRest.given().log().all()
                .pathParam("userId", userId)
                .when()
                .get(EndPoints.GET_SINGLE_USER_BY_ID)
                .then();
    }




}


