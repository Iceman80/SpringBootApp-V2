package myapp;

import io.restassured.http.ContentType;
import myapp.model.Department;

import myapp.repository.DepartmentRepository;
import myapp.service.Helper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(value = SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DepartmentControllerTest {

    private static final String ENDPOINT = "http://localhost:8080/department/";

    @Autowired
    private DepartmentRepository repository;

    @Before
    public void initDb() {
        if (!repository.findAll().iterator().hasNext()) {
            List<Department> departments = Helper.getDepartment();
            repository.saveAll(departments);
        }
    }

    @Test
    public void givenRequestForDepartments_expectThreeItems() {
        given().get(ENDPOINT + "all").then().assertThat().body("content.size()", is(3));
    }

    @Test(expected = AssertionError.class)
    public void givenRequestForDepartments_expectError() {
        given().get(ENDPOINT + "all").then().assertThat().body("content.size()", is(1));
    }

    @Test
    public void givenRequestForDepartments_whenResourcesAreRetrievedPaged_thenExpect200() {
        given().get(ENDPOINT + "all").then().statusCode(200);
    }

    @Test
    public void givenRequestForDepartments_expectDepartmentNames() {
        given().get(ENDPOINT + "all").then().assertThat().body("departmentName", hasItems("It", "Security", "Financial"));
    }

    @Test
    public void givenRequestForDepartments_expectDepartmentsName() {
        given().get(ENDPOINT + "2").then().assertThat().body("departmentName", equalTo("Security"));
    }

    @Test
    public void givenRequestForDepartment_findByDepartmentsName() {
        given().get(ENDPOINT + "dep/It").then().assertThat().body("id", equalTo(1));
    }

    @Test
    public void givenRequestForDepartment_addedAndDeleteNewDepartment() {
        given().get(ENDPOINT + "all").then().assertThat().body("content.size()", is(3));
        Department department = new Department("TestDepartment");
        given().body(department).when()
                .contentType(ContentType.JSON).post(ENDPOINT).then().assertThat().body(equalTo("Create Ok"));
        given().get(ENDPOINT + "all").then().assertThat().body("content.size()", is(4));
        given().get(ENDPOINT + "4").then().assertThat().body("departmentName", equalTo("TestDepartment"));
        given().delete(ENDPOINT + "4");
        given().get(ENDPOINT + "all").then().assertThat().body("content.size()", is(3));
    }

    @Test
    public void givenRequestForDepartment_expectException() {
        given().get(ENDPOINT + "dep/98").then().assertThat().body(equalTo("This should be application specific"));
        given().get(ENDPOINT + "dep/98").then().statusCode(409);
    }
}
