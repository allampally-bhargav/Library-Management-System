package Parameters;

import utilities.Resources;
import utilities.DatabaseConnection;
import utilities.Status;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Borrowers")
@MultipartConfig(maxFileSize = 16177215)    
public class BorrowersManagement extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        if (request.getParameter(Resources.NEW_BORROWER_REQ_SSN) == null && request.getParameter(Resources.NEW_BORROWER_REQ_FNAME) == null && request.getParameter(Resources.NEW_BORROWER_REQ_LNAME) == null && request.getParameter(Resources.NEW_BORROWER_REQ_EMAIL) == null && request.getParameter(Resources.NEW_BORROWER_REQ_ADDRESS) == null && request.getParameter(Resources.NEW_BORROWER_REQ_CITY) == null && request.getParameter(Resources.NEW_BORROWER_REQ_STATE) == null && request.getParameter(Resources.NEW_BORROWER_REQ_PHONE) == null) {
            
            RequestDispatcher rd = request.getRequestDispatcher("Borrowers.jsp");
            request.setAttribute(Resources.HAS_STATUS, false);
            rd.forward(request, response);
        } else if (request.getParameter(Resources.NEW_BORROWER_REQ_SSN).equalsIgnoreCase("") && request.getParameter(Resources.NEW_BORROWER_REQ_FNAME).equalsIgnoreCase("") && request.getParameter(Resources.NEW_BORROWER_REQ_LNAME).equalsIgnoreCase("") && request.getParameter(Resources.NEW_BORROWER_REQ_EMAIL).equalsIgnoreCase("") && request.getParameter(Resources.NEW_BORROWER_REQ_ADDRESS).equalsIgnoreCase("") && request.getParameter(Resources.NEW_BORROWER_REQ_CITY).equalsIgnoreCase("") && request.getParameter(Resources.NEW_BORROWER_REQ_STATE).equalsIgnoreCase("") && request.getParameter(Resources.NEW_BORROWER_REQ_PHONE).equalsIgnoreCase("")) {
            
            RequestDispatcher rd = request.getRequestDispatcher("Borrowers.jsp");
            request.setAttribute(Resources.HAS_STATUS, true);
            request.setAttribute(Resources.STATUS_TYPE, Status.WARNING);
            request.setAttribute(Resources.STATUS_HEADER, "Empty search");
            request.setAttribute(Resources.STATUS_BODY, "Please enter something to search");
            rd.forward(request, response);
        } else {
            if (request.getParameter(Resources.NEW_BORROWER_REQ_SSN).equalsIgnoreCase("") || request.getParameter(Resources.NEW_BORROWER_REQ_FNAME).equalsIgnoreCase("") || request.getParameter(Resources.NEW_BORROWER_REQ_LNAME).equalsIgnoreCase("") || request.getParameter(Resources.NEW_BORROWER_REQ_EMAIL).equalsIgnoreCase("") || request.getParameter(Resources.NEW_BORROWER_REQ_ADDRESS).equalsIgnoreCase("") || request.getParameter(Resources.NEW_BORROWER_REQ_CITY).equalsIgnoreCase("") || request.getParameter(Resources.NEW_BORROWER_REQ_STATE).equalsIgnoreCase("") || request.getParameter(Resources.NEW_BORROWER_REQ_PHONE).equalsIgnoreCase("")) {
                RequestDispatcher rd = request.getRequestDispatcher("Borrowers.jsp");
                request.setAttribute(Resources.HAS_STATUS, true);
                request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
                request.setAttribute(Resources.STATUS_HEADER, "Error occured");
                request.setAttribute(Resources.STATUS_BODY, "One of the fields left blank. Please fill it and try again.");
                rd.forward(request, response);
                return;
            }
            String ssn = request.getParameter(Resources.NEW_BORROWER_REQ_SSN);
            String fname = request.getParameter(Resources.NEW_BORROWER_REQ_FNAME);
            String lname = request.getParameter(Resources.NEW_BORROWER_REQ_LNAME);
            String email = request.getParameter(Resources.NEW_BORROWER_REQ_EMAIL);
            String address = request.getParameter(Resources.NEW_BORROWER_REQ_ADDRESS);
            String city = request.getParameter(Resources.NEW_BORROWER_REQ_CITY);
            String state = request.getParameter(Resources.NEW_BORROWER_REQ_STATE);
            String phone = request.getParameter(Resources.NEW_BORROWER_REQ_PHONE);

            StringBuffer sqlString = new StringBuffer();
            sqlString.append("select COUNT(*) from borrower where SSN = ?;");

            DatabaseConnection dbConnection = new DatabaseConnection();
            try {
                dbConnection.openConnection();
                dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                dbConnection.preparedStatement.setString(1, ssn);
                dbConnection.resultSet = dbConnection.preparedStatement.executeQuery();
                dbConnection.resultSet.next();
                if (dbConnection.resultSet.getInt(1) > 0) {
                    
                    RequestDispatcher rd = request.getRequestDispatcher("Borrowers.jsp");
                    request.setAttribute(Resources.HAS_STATUS, true);
                    request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
                    request.setAttribute(Resources.STATUS_HEADER, "Error");
                    request.setAttribute(Resources.STATUS_BODY, "Borrower already exists. Please check the details again.");
                    rd.forward(request, response);
                    return;
                }

                sqlString = new StringBuffer();
                sqlString.append("CALL InsertIntoBorrower (?,?,?,?,?,?,?,?); ");
                dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                dbConnection.preparedStatement.setString(1, ssn);
                dbConnection.preparedStatement.setString(2, fname);
                dbConnection.preparedStatement.setString(3, lname);
                dbConnection.preparedStatement.setString(4, email);
                dbConnection.preparedStatement.setString(5, address);
                dbConnection.preparedStatement.setString(6, city);
                dbConnection.preparedStatement.setString(7, state);
                dbConnection.preparedStatement.setString(8, phone);
                dbConnection.preparedStatement.executeUpdate();

                
                sqlString = new StringBuffer();
                sqlString.append("select CARD_ID from borrower where SSN = ?");
                dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                dbConnection.preparedStatement.setString(1, ssn);
                dbConnection.resultSet = dbConnection.preparedStatement.executeQuery();
                dbConnection.resultSet.next();
                int cardNo = dbConnection.resultSet.getInt(1);
                
                dbConnection.closeConnection();
                
                RequestDispatcher rd = request.getRequestDispatcher("Borrowers.jsp");
                request.setAttribute(Resources.HAS_STATUS, true);
                request.setAttribute(Resources.STATUS_TYPE, Status.SUCCESS);
                request.setAttribute(Resources.STATUS_HEADER, "Success");
                request.setAttribute(Resources.STATUS_BODY, "New borrower has been added to the database. The card number assigned is: "+ cardNo);
                rd.forward(request, response);

            } catch (SQLException e) {
                dbConnection.closeConnection();
                RequestDispatcher rd = request.getRequestDispatcher("Borrowers.jsp");
                request.setAttribute(Resources.HAS_STATUS, true);
                request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
                request.setAttribute(Resources.STATUS_HEADER, "SQL Exception caught");
                request.setAttribute(Resources.STATUS_BODY, "Sql exception caught. Try again. Exception is: " + e.toString());
                rd.forward(request, response);
            } catch (Exception e) {
                dbConnection.closeConnection();
                RequestDispatcher rd = request.getRequestDispatcher("Borrowers.jsp");
                request.setAttribute(Resources.HAS_STATUS, true);
                request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
                request.setAttribute(Resources.STATUS_HEADER, "Exception caught");
                request.setAttribute(Resources.STATUS_BODY, "Exception caught. PLease try again. Exception is " + e.toString());
                rd.forward(request, response);
            } finally {
                dbConnection.closeConnection();
            }
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
