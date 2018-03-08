package myapp;


import io.restassured.http.ContentType;
import myapp.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(value = SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {

    private static final String ENDPOINT = "http://localhost:8080/user/";

    @Test
    public void givenRequestForUsers_expectThreeItems() {
        given().get(ENDPOINT + "all").then().assertThat().body("content.size()", is(3));
    }

    @Test(expected = java.lang.AssertionError.class)
    public void givenRequestForUsers_expectError() {
        given().get(ENDPOINT + "all").then().assertThat().body("content.size()", is(1));
    }

    @Test
    public void givenRequestForUsers_whenResourcesAreRetrievedPaged_thenExpect200() {
        given().get(ENDPOINT + "all").then().statusCode(200);
    }

    @Test
    public void givenRequestForUsers_expectNames() {
        given().get(ENDPOINT + "all").then().assertThat().body("firstName", hasItems("Serhii", "Alex", "Petro"));
    }

    @Test
    public void givenRequestForUser_expectLastName() {
        given().get(ENDPOINT + "2").then().assertThat().body("lastName", equalTo("Ivanov"));
    }

    @Test
    public void givenRequestForUser_changeStatus() {
        given().get(ENDPOINT + "2").then().assertThat().body("status", equalTo("New"));
        given().post(ENDPOINT + "2/Vocation").then().assertThat().body(equalTo("Ok"));
        given().get(ENDPOINT + "2").then().assertThat().body("status", equalTo("Vocation"));
    }

    @Test
    public void givenRequestForUser_addedAndDeleteNewUser() {
        given().get(ENDPOINT + "all").then().assertThat().body("content.size()", is(3));
        User user = new User("TestFirstName", "TestLastName", 100, "000011111");
        given().body(user).when()
                .contentType(ContentType.JSON).post(ENDPOINT).then().assertThat().body(equalTo("User create Ok"));
        given().get(ENDPOINT + "all").then().assertThat().body("content.size()", is(4));
        given().get(ENDPOINT + "4").then().assertThat().body("lastName", equalTo("TestLastName"));
        given().delete(ENDPOINT + "4");
        given().get(ENDPOINT + "all").then().assertThat().body("content.size()", is(3));
    }

    @Test
    public void givenRequestForUsers_expectException() {
        given().get(ENDPOINT + "6").then().assertThat().body(equalTo("This should be application specific"));
        given().get(ENDPOINT + "8").then().statusCode(409);
    }
}
