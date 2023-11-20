package org.example.model;
import java.util.Random;

public class UserGenerator {
    public static User getDefault(){
        String[] login = new String[]{"3455@yandex.com", "3335@mail.com", "7774@google.com"};
        String[] password = new String[]{"3323", "5234", "f444"};
        String[] firstName = new String[]{"Anton2", "2323", "333"};
        int randomLogin = new Random().nextInt(login.length);
        int randomPassword = new Random().nextInt(password.length);
        int randomFirstName = new Random().nextInt(firstName.length);
        return new User( login[randomLogin], password[randomPassword], firstName[randomFirstName]);
    }
}
