package myapp;

import io.restassured.http.ContentType;
import myapp.model.Task;

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
public class TaskControllerTest {

    private static final String ENDPOINT = "http://localhost:8080/task/";

    @Test
    public void givenRequestForTasks_expectThreeItems() {
        given().get(ENDPOINT + "all").then().assertThat().body("content.size()", is(3));
    }

    @Test(expected = java.lang.AssertionError.class)
    public void givenRequestForTasks_expectError() {
        given().get(ENDPOINT + "all").then().assertThat().body("content.size()", is(1));
    }

    @Test
    public void givenRequestForTasks_whenResourcesAreRetrievedPaged_thenExpect200() {
        given().get(ENDPOINT + "all").then().statusCode(200);
    }

    @Test
    public void givenRequestForTasks_expectDepartmentNames() {
        given().get(ENDPOINT + "all").then().assertThat().body("description", hasItems("Fix Bug", "Prepare report", "Hard work"));
    }

    @Test
    public void givenRequestForTasks_expectDepartmentsName() {
        given().get(ENDPOINT + "2").then().assertThat().body("description", equalTo("Prepare report"));
    }

    @Test
    public void givenRequestForTasks_addedAndDeleteNewUser() {
        given().get(ENDPOINT + "all").then().assertThat().body("content.size()", is(3));
        Task task = new Task("TestTask");
        given().body(task).when()
                .contentType(ContentType.JSON).post(ENDPOINT).then().assertThat().body(equalTo("Task create Ok"));
        given().get(ENDPOINT + "all").then().assertThat().body("content.size()", is(4));
        given().get(ENDPOINT + "4").then().assertThat().body("description", equalTo("TestTask"));
        given().delete(ENDPOINT + "4");
        given().get(ENDPOINT + "all").then().assertThat().body("content.size()", is(3));
    }

    @Test
    public void givenRequestForTasks_expectException() {
        given().get(ENDPOINT + "6").then().assertThat().body(equalTo("This should be application specific"));
        given().get(ENDPOINT + "8").then().statusCode(409);
    }
}
