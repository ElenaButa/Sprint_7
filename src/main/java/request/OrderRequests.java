package request;

import dto.DtoCourier;
import dto.DtoOrder;
import groovyjarjarantlr4.v4.runtime.atn.SemanticContext;
import io.restassured.response.Response;

import static data.DataOrders.ORDER_URL;
import static io.restassured.RestAssured.given;

public class OrderRequests {
    public static Response createOrder(DtoOrder dtoOrder) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(dtoOrder)
                .when()
                .post(ORDER_URL);
        return response;
    }

    public static Response getOrderByNumber(DtoOrder dtoOrder) {
        Response response = given()
                .header("Content-type", "application/json")
                .params("t", dtoOrder.getTrack())
                .when()
                .get(ORDER_URL + "/track");
        return response;
    }

    public static Response acceptOrder(DtoOrder dtoOrder, DtoCourier dtoCourier) {
        Response response = given()
                .header("Content-type", "application/json")
                .params("courierId", dtoCourier.getId())
                .when()
                .put(String.format(ORDER_URL + "/accept/%d", dtoOrder.getId()));
        return response;
    }

    public static Response getOrdersList(DtoCourier dtoCourier) {
        Response response = given()
                .header("Content-type", "application/json")
                .params("courierId", dtoCourier.getId())
                .when()
                .get(ORDER_URL);
        return response;
    }

    public static Response cancelOrder(DtoOrder dtoOrder) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(dtoOrder)
                .when()
                .put(ORDER_URL + "/cancel");
        return response;
    }

    public static Response completeOrder(DtoOrder dtoOrder) {
        Response response = given()
                .header("Content-type", "application/json")
                .pathParams("id", dtoOrder.getId())
                .when()
                .put(ORDER_URL + "/finish/{id}");
        return response;
    }

}