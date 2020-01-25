package Parameters;

import utilities.Resources;
import utilities.DatabaseConnection;
import utilities.Status;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import java.text.SimpleDateFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/CheckIn")
@MultipartConfig(maxFileSize = 16177215) 
public class BookCheckIn extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        if (request.getParameter(Resources.CHECK_IN_REQ_DATE) == null) {
            RequestDispatcher rd = request.getRequestDispatcher("CheckIn.jsp");
            request.setAttribute(Resources.HAS_STATUS, true);
            request.setAttribute(Resources.STATUS_TYPE, Status.WARNING);
            request.setAttribute(Resources.STATUS_HEADER, "Enter date");
            request.setAttribute(Resources.STATUS_BODY, "Please enter the check-in date in the format yyyy-MM-dd to complete the check in");
            rd.forward(request, response);
        } else {
            
        	DatabaseConnection dbConnection = new DatabaseConnection();
            try {
                String sqlString = new String();
                String DATE_IN = request.getParameter(Resources.CHECK_IN_REQ_DATE);
                String ISBN13 = request.getParameter(Resources.CHECK_IN_REQ_ISBN13);
                String CARD_ID = request.getParameter(Resources.CHECK_IN_REQ_CARD_ID);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.parse(DATE_IN);

                dbConnection.openConnection();

                sqlString = "update book_loans set date_in = ? where isbn13 = ? and card_id = ?;";
                dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString);
                dbConnection.preparedStatement.setString(1, DATE_IN);
                dbConnection.preparedStatement.setString(2, ISBN13);
                dbConnection.preparedStatement.setString(3, CARD_ID);
                dbConnection.preparedStatement.executeUpdate();
                

                sqlString = "update books set copies_borrowed = copies_borrowed - 1 where isbn13 = ?;";
                dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString);
                dbConnection.preparedStatement.setString(1,ISBN13);
                dbConnection.preparedStatement.executeUpdate();
                
                
                dbConnection.closeConnection();

                RequestDispatcher rd = request.getRequestDispatcher("CheckIn.jsp");
                request.setAttribute(Resources.BOOK_LOAN_REQ_TYPE, Resources.BOOK_LOAN_REQ_CHECKIN_TYPE);
                request.setAttribute(Resources.HAS_STATUS, true);
                request.setAttribute(Resources.STATUS_TYPE, Status.SUCCESS);
                request.setAttribute(Resources.STATUS_HEADER, "Success");
                request.setAttribute(Resources.STATUS_BODY, "Book successfully checked in");
                rd.forward(request, response);

            } catch (ParseException e) {
                RequestDispatcher rd = request.getRequestDispatcher("CheckIn.jsp");
                request.setAttribute(Resources.HAS_STATUS, true);
                request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
                request.setAttribute(Resources.STATUS_HEADER, "Date incorrect");
                request.setAttribute(Resources.STATUS_BODY, "Please enter the date in correct yyyy-MM-dd format");
                rd.forward(request, response);
            } catch (SQLException e) {
                RequestDispatcher rd = request.getRequestDispatcher("CheckIn.jsp");
                request.setAttribute(Resources.HAS_STATUS, true);
                request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
                request.setAttribute(Resources.STATUS_HEADER, "SQL Exception caught");
                request.setAttribute(Resources.STATUS_BODY, "Sql exception caught. Try again. Exception is: " + e.toString());
                rd.forward(request, response);
            } catch (ClassNotFoundException e) {
                RequestDispatcher rd = request.getRequestDispatcher("CheckIn.jsp");
                request.setAttribute(Resources.HAS_STATUS, true);
                request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
                request.setAttribute(Resources.STATUS_HEADER, "Class not found Exception caught");
                request.setAttribute(Resources.STATUS_BODY, "Class not found exception caught. Try again. Exception is: " + e.toString());
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
    }

}
