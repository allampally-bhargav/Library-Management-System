package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	 public Connection connect = null;
	    private Statement statement = null;
	    public PreparedStatement preparedStatement = null;
	    public ResultSet resultSet = null;

	    public DatabaseConnection() {
	    }

	    public void openConnection() throws SQLException, ClassNotFoundException {
	        Class.forName("com.mysql.jdbc.Driver");
	        connect = DriverManager
	                .getConnection("jdbc:mysql://localhost/lms?"
	                        + "user=root&password=Bhargav1@");
	    }

	    public void closeConnection() {
	        try {
	            if (resultSet != null) {
	                resultSet.close();
	            }

	            if (statement != null) {
	                statement.close();
	            }

	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }

	            if (connect != null) {
	                connect.close();
	            }
	        } catch (Exception exp) {

	        }
	    }
}



