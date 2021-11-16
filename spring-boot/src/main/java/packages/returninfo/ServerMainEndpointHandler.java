package packages.returninfo;

import packages.backendlogic.LoanInfoInterface;
import packages.backendlogic.Loans;
import packages.informationmanipulation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/** This class handles HTTP request to the "/senso" endpoint. **/
@RestController
@CrossOrigin(origins ="*")
public class ServerMainEndpointHandler {

    private final AtomicLong counter = new AtomicLong();

    /**
     * This method handles HTTP request coming into the "/traderauto-plus" endpoint. It processes information from the request
     * body and returns information back to the client.
     *
     * @param req_body - the body of the http request from the client
     * @return A HTTP Response back to the client.
     */
    @CrossOrigin(origins = "http://ec2-18-118-163-255.us-east-2.compute.amazonaws.com:8080")
    @PostMapping("/traderauto-plus")
    public String httpResponseSenso(@RequestBody() String req_body) throws IOException, SQLException, InterruptedException {
        System.out.println("==== POST Request Received ====");
        HashMap<String, String> body = parseRequestBody(req_body);

        //get car list based on user's preference, otherwise get all cars from database
        ArrayList<HashMap<String, String>> filtered_cars;
        if (!Objects.equals(body.get("car-preference"), "")) {
            filtered_cars = getFilteredCars(body.get("car-preference"));
        } else {
            filtered_cars = getAllCars();
        }

        body.remove("car-preference");

        // calculate loans for all cars that are filtered and create a response to send back to the client
        LoanInfoInterface loans = new Loans();
        HashMap<String, Object> loans_list = loans.calculateLoans(body, filtered_cars);
        String response = createLoanResponse(loans_list);

        System.out.println("==== POST Response Sent ====");
        return response;
    }

    /**
     * Create a json string representation of the loan information for the cars to be sent back as the POST response
     * @param loans - a list of loans
     * @return - a json string representation of all the loan information
     */
    static String createLoanResponse(HashMap<String, Object> loans) {
        Gson gsonObj = new Gson();
        return gsonObj.toJson(loans);
    }


    /**
     * Parse the request body
     * @param req_body - a string representation of the request json
     * @return a hashmap representation of the request json
     * @throws JsonProcessingException - error thrown when json cannot be processed
     */
    static HashMap<String, String> parseRequestBody(String req_body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, String> userInfoHash = objectMapper.readValue(req_body, HashMap.class);

        return userInfoHash;
    }


    /**
     * Get filtered car list based on car_type specified in the http request body
     * @param car_type - the car_type to return from the database
     * @return an array list of cars that fit the car_type
     * @throws SQLException - error thrown if encounter problem when connecting to database
     */
    static ArrayList<HashMap<String, String>> getFilteredCars(String car_type) throws SQLException {
        return ReturnMultipleCars.returnFilteredCars(car_type);
    }

    /**
     * A helper methods to return all cars in the database
     * @return an array list containg the information for all cars
     * @throws SQLException - error thrown if encounter problem when connecting to database
     */
    private ArrayList<HashMap<String, String>> getAllCars() throws SQLException {
        return ReturnMultipleCars.returnAllCars();
    }
}