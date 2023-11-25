import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.metods.CreateAccount;
import org.example.model.User;
import org.example.model.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.apache.http.HttpStatus.*;

public class CreateAccountTest {
    private CreateAccount createAccount;
    private User user;
    private String token;

    @Before
    public void setUp(){
        createAccount = new CreateAccount();
        user = UserGenerator.getDefault();
    }
    @Test
    @DisplayName("Клиент может успешно зарегестрироваться")
    public void accountCanBeCreateTest(){
    ValidatableResponse response = createAccount.create(user);
    token = response.extract().path("accessToken");
    int statusCode = response.extract().statusCode();
    assertEquals(SC_OK, statusCode);
    }
    @After
    public void delete(){
        createAccount.delete(token);
    }
}
