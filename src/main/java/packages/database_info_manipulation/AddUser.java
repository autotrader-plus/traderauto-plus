package packages.database_info_manipulation;

import packages.backend_logic.UserFactory;
import packages.connect_api_db.AutoTraderDBInterface;
import packages.connect_api_db.ConnectAutoTraderDB;
import packages.backend_logic.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Represents adding a User Object to the database.
 */
public class AddUser {

    /**
     * Gets the currently stored max user ID plus one
     * @return A String representing the currently stored max user ID plus one
     * @throws SQLException If there was a database access error
     */
    private String getMaxIDAddOne() throws SQLException {
        // Writing a SQL query
        String query = "SELECT MAX(user_id) FROM cars.Users;";

        // Establish a connection and create a set of results from that query
        AutoTraderDBInterface connection = new ConnectAutoTraderDB();
        ResultSet myResultSet = connection.writeQuery(query);

        int returnInt = 0;

        if(myResultSet.next()) {
            returnInt = myResultSet.getInt("MAX(user_id)");
        }
        return Integer.toString(returnInt + 1);
    }

    /**
     * Add a user to the database. Does not return anything.
     * @param user the user who we are recording data for.
     * @param User
     */
    public String addUser(HashMap<String, String> User) throws SQLException {
        UserFactory userfactory = new UserFactory();
        User user = userfactory.createUser(User);
        // Determine what userId to assign this individual
        String userID = getMaxIDAddOne();

        // Write the SQL query
        String query = "INSERT INTO cars.Users" +
                "( `user_id`, `name`, `credit_score`, `location`, `max_downpayment`, `max_monthly_payment`, " +
                "`monthly_income`, `employment_status`, `homeowner`, `monthly_debt_obligations`, `password`)" +
                "VALUES " +
                "('" + userID +
                "', '" + user.getName() +
                "', '" + user.getCreditScore() +
                "', '" + user.getLocation() +
                "', '" + user.getDownPayment() +
                "', '" + user.getMonthlyBudget() +
                "', '" + user.getMonthlyIncome() +
                "', '" + user.isEmployed() +
                "', '" + user.isHomeowner() +
                "', '" + user.getMonthlyDebt() +
                "', '" + user.getPassword() + "');";

        // Establish a connection and create a set of results from that query
        AutoTraderDBInterface connection = new ConnectAutoTraderDB();
        connection.executeQuery(query);

        return "Successful";

    }

}
