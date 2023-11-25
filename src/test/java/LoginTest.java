
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.metods.CreateAccount;
import org.example.model.Credentials;
import org.example.Data;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.apache.http.HttpStatus.*;

public class LoginTest {
    private String token;
    private CreateAccount createAccount;
    private Credentials credentials;
    @Before
    public void setUp(){
        credentials = new Credentials(Data.LOGIN,Data.PASSWORD);
        createAccount = new CreateAccount();
    }
    @Test
    @DisplayName("логин под существующим пользователем")
    public void loginCorrectTest() {
        ValidatableResponse response = createAccount.login(credentials);
        token = response.extract().path("accessToken");
        int statusCode1 = response.extract().statusCode();
        assertEquals(SC_OK, statusCode1);
        assertNotNull("Something wrong", token);
    }
    @Test
    @DisplayName("логин с неверным логином и паролем")
    public void loginNotCorrectDataTest(){
        ValidatableResponse response = createAccount.login(new Credentials("yandex",null));
        response.assertThat().body("success", is(false)).and().body("message", equalTo("email or password are incorrect"));
        int statusCode = response.extract().statusCode();
        assertEquals("Fail", SC_UNAUTHORIZED, statusCode);
    }
}