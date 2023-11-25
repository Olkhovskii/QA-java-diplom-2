package org.example.metods;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.model.Credentials;
import org.example.Data;
import org.example.model.User;
import static io.restassured.RestAssured.given;
import static org.example.Data.ApiCreate;
import static org.example.Data.ApiDelete;

public class CreateAccount extends Specification {
    @Step("Создание нового аккаунта")
    public ValidatableResponse create(User user){
        return given()
                .spec(requestSpec())
                .body(user)
                .when()
                .post( Data.URL + ApiCreate)
                .then().log().all();
    }
        @Step("Удаление пользовательских данных из системы")
        public ValidatableResponse delete(String token){
            return given()
                    .spec(requestSpec())
                    .auth().oauth2(token.replace("Bearer ", ""))
                    .when()
                    .delete(ApiDelete)
                    .then().log().all();
    }
        @Step("Авторизация в системе")
        public ValidatableResponse login(Credentials credentials){
            return given()
                    .spec(requestSpec())
                    .body(credentials)
                    .when()
                    .post(Data.URL + Data.ApiLogin)
                    .then().log().all();
    }
        @Step("Клиент может поменять данные")
        public ValidatableResponse change(String token, Credentials credentials){
            return given()
                    .spec(requestSpec())
                    .header("Authorization",token)
                    .body(credentials)
                    .when()
                    .patch(Data.URL+Data.ApiPatch)
                    .then().log().all();
        }
}
