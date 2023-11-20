package org.example.metods;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.Data;
import org.example.model.Orders;

import static io.restassured.RestAssured.given;

public class CreateOrder extends Specification {
    @Step("клиент может создать заказ с авторизацией")
    public ValidatableResponse createOrder(String token, Orders orders){
        return given()
                .spec(requestSpec())
                .header("Authorization",token)
                .body(orders)
                .when()
                .post(Data.URL + Data.ApiOrders)
                .then().log().all();
    }
    @Step("клиент может создать заказ без авторизацией")
    public ValidatableResponse createOrderWithoutToken( Orders orders){
        return given()
                .spec(requestSpec())
                .body(orders)
                .when()
                .post(Data.URL + Data.ApiOrders)
                .then().log().all();
    }
        @Step("Получение заказа конкретного пользователя")
        public ValidatableResponse getOrder(String token){
            return given()
                    .spec(requestSpec())
                    .header("Authorization",token)
                    .when()
                    .get(Data.URL + Data.ApiOrders)
                    .then().log().all();
        }
}
