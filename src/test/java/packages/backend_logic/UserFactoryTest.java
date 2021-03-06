package packages.backend_logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Objects;

class UserFactoryTest {

    HashMap<String, String> user;
    HashMap<String, String> userAdvanced;
    UserFactory factory;

    @BeforeEach
    void setUp(){
        HashMap<String, String> user_info = new HashMap<>();
        user_info.put("credit-score", "770");
        user_info.put("monthlybudget", "1000");
        user_info.put("downpayment", "1000");
        user_info.put("zip-code", "M5S 1S5");
        user_info.put("name", "Paul");
        user_info.put("password", "123");

        user = user_info;

        HashMap<String, String> user_info2 = new HashMap<>();
        user_info2.put("credit-score", "770");
        user_info2.put("monthlybudget", "1000");
        user_info2.put("downpayment", "1000");
        user_info2.put("zip-code", "M5S 1S5");
        user_info2.put("name", "Paul");
        user_info2.put("monthlyincome", "1300");
        user_info2.put("employed", "employed");
        user_info2.put("homeowner", "homeowner");
        user_info2.put("monthlydebt", "500");
        user_info.put("password", "123");

        userAdvanced = user_info2;

        factory = new UserFactory();
    }

    @Test
    void createUser() {
        User user_object = factory.createUser(user);
        assert(Objects.equals(user_object.getCreditScore(), "770"));
    }

    @Test
    void createUser2() {
        User user_object = factory.createUser(userAdvanced);
        assert(Objects.equals(user_object.getMonthlyDebt(), "500"));
        assert(Objects.equals(user_object.isEmployed(), "Employed"));
    }
}