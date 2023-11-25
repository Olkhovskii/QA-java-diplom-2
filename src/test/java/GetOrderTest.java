import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.metods.CreateAccount;
import org.example.metods.CreateOrder;
import org.example.model.Credentials;
import org.example.Data;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.apache.http.HttpStatus.*;

public class GetOrderTest {
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
    @DisplayName("Получение заказа авторизованного пользователя")
    public void CreateOrderSuccessTest(){
        ValidatableResponse response = createAccount.login(credentials);
        token = response.extract().path("accessToken");
        ValidatableResponse response1 = createOrder.getOrder(token);
        int statusCode = response1.extract().statusCode();
        response1.assertThat().body("success", is(true));
        assertEquals("Order don't create",SC_OK,statusCode);
    }
    @Test
    @DisplayName("Получение заказа неавторизованного пользователя")
    public void CreateOrderNotSuccessTest(){
        ValidatableResponse response = createOrder.getOrder("");
        int statusCode = response.extract().statusCode();
        response.assertThat().body("success", is(false)).and().body("message",equalTo("You should be authorised"));
        assertEquals("Order don't create",SC_UNAUTHORIZED,statusCode);
    }
}
