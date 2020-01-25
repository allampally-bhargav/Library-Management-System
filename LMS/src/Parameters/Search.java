package Parameters;

import list.SearchBookList;
import utilities.Resources;
import utilities.DatabaseConnection;
import utilities.Status;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import java.sql.*;

@WebServlet("/Searchbook")
public class Search extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String search = request.getParameter(Resources.BOOK_SEARCH);

        
        if (request.getParameter(Resources.BOOK_SEARCH) == null || search.contentEquals("")) {
            RequestDispatcher rd = request.getRequestDispatcher("SearchAvailability.jsp");
            request.setAttribute(Resources.HAS_STATUS, false);
            rd.forward(request, response);
            return;
        } 
        else {
        

     
        	DatabaseConnection dbConnection = new DatabaseConnection();
            try {
             search = request.getParameter(Resources.BOOK_SEARCH);
				StringBuilder sqlString = new StringBuilder();
					
				sqlString.append("call Searchbook ('"+search+"'); ");
				dbConnection.openConnection();
				
				dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
				dbConnection.preparedStatement.executeUpdate();
				dbConnection.resultSet = dbConnection.preparedStatement.executeQuery();
            	             
                ArrayList<SearchBookList> list = new ArrayList<>();
                while (dbConnection.resultSet.next()) {
                    list.add(new SearchBookList(dbConnection.resultSet.getString("isbn13"), dbConnection.resultSet.getString("isbn10"), dbConnection.resultSet.getString("title"), dbConnection.resultSet.getString("author"), dbConnection.resultSet.getString("no_of_copies")));
                }
                if(list.size()>2000)
                    list.subList(2000, list.size()).clear();
                
                dbConnection.closeConnection();
                
                RequestDispatcher rd = request.getRequestDispatcher("SearchAvailability.jsp");
                request.setAttribute(Resources.HAS_STATUS, true);
                request.setAttribute(Resources.STATUS_TYPE, Status.SUCCESS);
                request.setAttribute(Resources.STATUS_HEADER, "Searching success");
                if(list.size()<2000)
                {
                    request.setAttribute(Resources.STATUS_BODY, "Search success.");
                } else {
                    request.setAttribute(Resources.STATUS_BODY, "Search success. But search results limited to 5000 rows. Please change search constraints for better results.");
                }
                
                request.setAttribute(Resources.BOOK_SEARCH_JSP_REQ, list);
                rd.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                RequestDispatcher rd = request.getRequestDispatcher("SearchAvailability.jsp");
                request.setAttribute(Resources.HAS_STATUS, true);
                request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
                request.setAttribute(Resources.STATUS_HEADER, "SQL Exception");
                request.setAttribute(Resources.STATUS_BODY, "SQL Exception caught. \n " + e.toString());
                rd.forward(request, response);
                dbConnection.closeConnection();
            }
            finally{
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