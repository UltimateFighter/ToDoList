package com.nahberger.todolist.web.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;

@QuarkusTest
class ToDoResourceIT {

    private static final String BASE_PATH = "/api/v1/todos";

    @AfterEach
    void finalizeAfter() {
        delete("2025-08-02");
    }

    @Test
    public void testCreateAndGetAll() {
        createItem().then()
                .statusCode(201)
                .body("description", is("Test Item"));
        getAllItemsAndValidate();
    }

    @Test
    public void testDeleteNotFound() {
        delete("Not-Found")
                .then()
                .statusCode(404);
    }

    private Response delete(final String id) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .when().delete(BASE_PATH + "/" + id);
    }

    @Test
    public void testConflictOnCreate() {
        createItem().then()
                .statusCode(201)
                .body("description", is("Test Item"));
        createItem().then()
                .statusCode(409);
    }

    private void getAllItemsAndValidate() {
        RestAssured.given()
                .when().get(BASE_PATH)
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1))
                .body("[0].description", is("Test Item"));
    }

    private Response createItem() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"description\":\"Test Item\",\"dueDate\":\"02.08.2025\"}")
                .when().post(BASE_PATH);
    }
}
