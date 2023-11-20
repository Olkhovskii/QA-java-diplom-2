import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.metods.CreateAccount;
import org.example.metods.CreateOrder;
import org.example.model.Credentials;
import org.example.Data;
import org.example.model.Orders;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.apache.http.HttpStatus.*;

public class CreateOrdersTest {
    private CreateAccount createAccount;
    private String token;
    private CreateOrder createOrder;
    private Credentials credentials;

    @Before
    public void setUp() {
        credentials = new Credentials(Data.LOGIN, Data.PASSWORD);
        createAccount = new CreateAccount();
        createOrder = new CreateOrder();
    }
    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void CreateOrderSuccessTest(){
        ValidatableResponse response = createAccount.login(credentials);
        token = response.extract().path("accessToken");
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
        Orders order = new Orders(ingredients);
        ValidatableResponse response1 = createOrder.createOrder(token,order);
        int statusCode = response1.extract().statusCode();
        response1.assertThat().body("success", is(true));
        assertEquals("Order don't create",SC_OK,statusCode);
    }
    @Test
    @DisplayName("Создание зааза без авторизации")
    public void CresteOrderWithotTokenTest(){
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
        Orders order = new Orders(ingredients);
        ValidatableResponse response = createOrder.createOrderWithoutToken(order);
        int statusCode = response.extract().statusCode();
        response.assertThat().body("success", is(true));
        assertEquals("Order don't create",SC_OK,statusCode);
    }
    @Test
    @DisplayName("Создание заказа без ингридиентов")
    public void CreateOrderWithoutIngredientsTest(){
        ValidatableResponse response = createAccount.login(credentials);
        token = response.extract().path("accessToken");
        Orders order = new Orders(null);
        ValidatableResponse response1 = createOrder.createOrder(token,order);
        int statusCode = response1.extract().statusCode();
        response1.assertThat().body("success", is(false)).and().body("message",equalTo("Ingredient ids must be provided"));
        assertEquals("Order don't create",SC_BAD_REQUEST,statusCode);
    }
    @Test
    @DisplayName("Создание заказа без авторизацией и с неверным хешем ингредиентов")
    public void CreateOrderWithWrongIngredientsTest(){
        List<String> ingredients = List.of("61c0c5a71d1f82001bd", "61c0c5a71d1f82a6f");
        Orders order = new Orders(ingredients);
        ValidatableResponse response1 = createOrder.createOrder("",order);
        int statusCode = response1.extract().statusCode();
        assertEquals("Order don't create",SC_INTERNAL_SERVER_ERROR,statusCode);
    }
}
