package Parameters;

import utilities.Resources;
import utilities.DatabaseConnection;
import utilities.Status;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RefreshFines")
public class Refresh extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	DatabaseConnection dbConnection = new DatabaseConnection();
        try {
            response.setContentType("text/html;charset=UTF-8");

            dbConnection.openConnection();

            StringBuilder sqlString = new StringBuilder();
            sqlString.append("insert into fines (loan_id, fine_amount) "
                    + "select T.loan_id,T.fine*0.25 from (select loan_id, if(date_in IS NULL,datediff(curdate(),due_date),IF(date_in>due_date,datediff(date_in,due_date),0)) as fine from book_loans having fine>0) as T "
                    + "where T.loan_id NOT IN (select f.loan_id from fines as f);");
            dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
            dbConnection.preparedStatement.executeUpdate();

            sqlString = new StringBuilder();
            sqlString.append("update fines join (select loan_id, if(date_in IS NULL,datediff(curdate(),due_date),IF(date_in>due_date,datediff(date_in,due_date),0)) as fine from book_loans having fine>0) as T on fines.loan_id = T.loan_id set fines.fine_amount = T.fine*0.25 where fines.paid = 0; ");
            dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
            dbConnection.preparedStatement.executeUpdate();

            dbConnection.closeConnection();

            RequestDispatcher rd = request.getRequestDispatcher("FineTrack.jsp");
            request.setAttribute(Resources.HAS_STATUS, true);
            request.setAttribute(Resources.STATUS_TYPE, Status.SUCCESS);
            request.setAttribute(Resources.STATUS_HEADER, "Refresh ");
            request.setAttribute(Resources.STATUS_BODY, "Refresh Successful");
            rd.forward(request, response);

        } catch (SQLException ex) {
            dbConnection.closeConnection();
            RequestDispatcher rd = request.getRequestDispatcher("FineTrack.jsp");
            request.setAttribute(Resources.HAS_STATUS, true);
            request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
            request.setAttribute(Resources.STATUS_HEADER, "Exception caught");
            request.setAttribute(Resources.STATUS_BODY, "MySql exception caught. Please try again. Exception is " + ex.toString());
            rd.forward(request, response);

        } catch (ClassNotFoundException ex) {
            dbConnection.closeConnection();
            RequestDispatcher rd = request.getRequestDispatcher("FineTrack.jsp");
            request.setAttribute(Resources.HAS_STATUS, true);
            request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
            request.setAttribute(Resources.STATUS_HEADER, "Exception caught");
            request.setAttribute(Resources.STATUS_BODY, "ClassNotFound exception caught. Please try again. Exception is " + ex.toString());
            rd.forward(request, response);
        } finally {
            dbConnection.closeConnection();
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
