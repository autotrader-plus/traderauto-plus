package packages.server_setup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.*;

class ServerMainEndpointHandlerTest {


    @Test
    void httpResponseSenso() {
        HashMap<String, String> mapping = new HashMap<String, String>();
        mapping.put("car-preference", "SUV");
        mapping.put("zip-code", "M11 1S6");
        mapping.put("downpayment", "200");
        mapping.put("name", "Paul");
        mapping.put("credit-score", "770");
        mapping.put("monthlybudget", "5000");
        mapping.put("password", "123");

        Gson gsonObj = new Gson();
        String request = gsonObj.toJson(mapping);

        ServerMainEndpointHandler test = new ServerMainEndpointHandler();

        Gson gsonObj2 = new Gson();
        Type mapType = new TypeToken<Map<Object, Object>>(){}.getType();
        HashMap<String, Object> response = gsonObj2.fromJson(test.httpResponseSenso(request), mapType);
        ArrayList<HashMap<String, Object>> entry = (ArrayList<HashMap<String, Object>>) response.get("12");
        assert(entry.size() == 1);
    }

    @Test
    // SImilar test to the first test, except now it calls all cars in the database since no car preference is indicated.
    void httpResponseSensoNoPreference() {
        HashMap<String, String> mapping = new HashMap<String, String>();
        mapping.put("car-preference", "");
        mapping.put("zip-code", "M11 1S6");
        mapping.put("downpayment", "200");
        mapping.put("name", "Paul");
        mapping.put("credit-score", "770");
        mapping.put("monthlybudget", "5000");
        mapping.put("password", "123");

        Gson gsonObj = new Gson();
        String request = gsonObj.toJson(mapping);

        ServerMainEndpointHandler test = new ServerMainEndpointHandler();

        Gson gsonObj2 = new Gson();
        Type mapType = new TypeToken<Map<Object, Object>>(){}.getType();
        HashMap<String, Object> response = gsonObj2.fromJson(test.httpResponseSenso(request), mapType);
        ArrayList<HashMap<String, Object>> entry = (ArrayList<HashMap<String, Object>>) response.get("11");
        assert(entry.size() == 1);

    }

}