package Parameters;

import list.FineList;
import utilities.Resources;
import utilities.DatabaseConnection;
import utilities.Status;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FineTrack")
public class FineTrackControl extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        if (request.getParameter(Resources.FINE_REQ_BORROWER_NAME) == null && request.getParameter(Resources.FINE_REQ_CARD_ID) == null) {
            RequestDispatcher rd = request.getRequestDispatcher("FineTrack.jsp");
            request.setAttribute(Resources.HAS_STATUS, false);
            rd.forward(request, response);

        } else if (request.getParameter(Resources.FINE_REQ_BORROWER_NAME).equals("") && request.getParameter(Resources.FINE_REQ_CARD_ID).equals("")) {
            //nothing entered
            RequestDispatcher rd = request.getRequestDispatcher("FineTrack.jsp");
            request.setAttribute(Resources.HAS_STATUS, true);
            request.setAttribute(Resources.STATUS_TYPE, Status.WARNING);
            request.setAttribute(Resources.STATUS_HEADER, "Empty search");
            request.setAttribute(Resources.STATUS_BODY, "Please enter into the boxes to search for fines.");
            rd.forward(request, response);
        } else {
        	DatabaseConnection dbConnection = new DatabaseConnection();
            try {
                String borrowerName = request.getParameter(Resources.FINE_REQ_BORROWER_NAME);
                String card_id = request.getParameter(Resources.FINE_REQ_CARD_ID);

                StringBuilder sqlString = new StringBuilder();
                sqlString.append("select b.fname,b.lname,bl.card_id,SUM(f.fine_amount) as amount,f.paid "
                        + "from fines as f join book_loans bl on f.loan_id = bl.loan_id join borrower as b on b.card_id = bl.card_id where ");
                if (!borrowerName.equalsIgnoreCase("")) {
                    sqlString.append(" b.fname like ? or b.lname like ?");
                }

                if (!card_id.equalsIgnoreCase("")) {
                    if (!borrowerName.equals("")) {
                        sqlString.append(" and ");
                    }

                    sqlString.append(" bl.card_id = ?");
                }
                sqlString.append(" group by bl.card_id,f.paid;");

                dbConnection.openConnection();
                dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                int count = 1;
                if (!borrowerName.equalsIgnoreCase("")) {
                    dbConnection.preparedStatement.setString(count++, "%" + borrowerName + "%");
                    dbConnection.preparedStatement.setString(count++, "%" + borrowerName + "%");
                }
                if (!card_id.equalsIgnoreCase("")) {
                    dbConnection.preparedStatement.setString(count++, card_id);
                }

                dbConnection.resultSet = dbConnection.preparedStatement.executeQuery();
                ArrayList<FineList> list = new ArrayList<>();
                while (dbConnection.resultSet.next()) {
                    list.add(new FineList( dbConnection.resultSet.getString("card_id"), dbConnection.resultSet.getString("amount"), dbConnection.resultSet.getBoolean("paid")));
                }

                dbConnection.closeConnection();

                RequestDispatcher rd = request.getRequestDispatcher("FineTrack.jsp");
                request.setAttribute(Resources.HAS_STATUS, true);
                request.setAttribute(Resources.STATUS_TYPE, Status.SUCCESS);
                request.setAttribute(Resources.STATUS_HEADER, "Searching success");
                request.setAttribute(Resources.STATUS_BODY, "Search success.");
                request.setAttribute(Resources.FINE_JSP_REQ, list);
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

