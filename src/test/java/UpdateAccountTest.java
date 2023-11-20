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
import static org.apache.http.HttpStatus.*;

public class UpdateAccountTest {
    private String token;
    private Credentials credentials;
    private CreateAccount createAccount;

    @Before
    public void setUp() {
        credentials = new Credentials(Data.LOGIN, Data.PASSWORD);
        createAccount = new CreateAccount();
    }
    @Test
    @DisplayName("клиент может изменить данные с авторизацией")
    public void updateAccount() {
        ValidatableResponse response = createAccount.login(credentials);
        token = response.extract().path("accessToken");
        ValidatableResponse response1 = createAccount.change(token, new Credentials(Data.LOGIN, Data.PASSWORD));
        response1.assertThat().body("success", is(true));
    }
    @Test
    @DisplayName("клиент не может изменить данные без авторизацией")
    public void NotUpdateAccount() {

        ValidatableResponse response1 = createAccount.change("", new Credentials(Data.LOGIN, Data.PASSWORD));
        int statusCode = response1.extract().statusCode();
        response1.assertThat().body("success", is(false)).and().body("message", equalTo("You should be authorised"));
        assertEquals("Fail", SC_UNAUTHORIZED, statusCode);
    }
}
