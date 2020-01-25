package Parameters;

import utilities.Resources;
import utilities.DatabaseConnection;
import utilities.Status;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/FinePayment")
public class FinePay extends HttpServlet {
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DatabaseConnection dbConnection = new DatabaseConnection();
        try {
            if (request.getParameter(Resources.FINE_PAYMENT_REQ_CARD_ID) == null && request.getParameter(Resources.FINE_PAYMENT_REQ_AMT) == null && request.getParameter(Resources.FINE_PAYMENT_REQ_LOAN_ID).equals("") && request.getParameter(Resources.FINE_PAYMENT_REQ_AMT).equals("")) {
                throw new SQLException();
            }
            String card_id = request.getParameter(Resources.FINE_PAYMENT_REQ_CARD_ID);
            String amount = request.getParameter(Resources.FINE_PAYMENT_REQ_AMT);

            dbConnection.openConnection();
            String sqlString = "update fines join book_loans on fines.loan_id = book_loans.loan_id set fines.paid = 1 where fines.paid = 0 and book_loans.card_id = ?;";
            dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString);
            dbConnection.preparedStatement.setString(1, card_id);
            dbConnection.preparedStatement.executeUpdate();

            RequestDispatcher rd = request.getRequestDispatcher("FineTrack.jsp");
            request.setAttribute(Resources.HAS_STATUS, true);
            request.setAttribute(Resources.STATUS_TYPE, Status.SUCCESS);
            request.setAttribute(Resources.STATUS_HEADER, "Payment");
            request.setAttribute(Resources.STATUS_BODY, "Payment of " + amount + " was successfully recorded.");
            rd.forward(request, response);

        } catch (SQLException e) {
            dbConnection.closeConnection();
            RequestDispatcher rd = request.getRequestDispatcher("FineTrack.jsp");
            request.setAttribute(Resources.HAS_STATUS, true);
            request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
            request.setAttribute(Resources.STATUS_HEADER, "Exception caught");
            request.setAttribute(Resources.STATUS_BODY, "MySql exception caught. Please try again. Exception is " + e.toString());
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
