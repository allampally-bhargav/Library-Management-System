package Parameters;


import list.LoanList;
import utilities.Resources;
import utilities.DatabaseConnection;
import utilities.Status;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GenerateLoan")
@MultipartConfig(maxFileSize = 16177215) 
public class BookLoan extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        

        if (request.getParameter(Resources.BOOK_LOAN_REQ_TYPE) == null || request.getParameter(Resources.BOOK_LOAN_REQ_TYPE).equals(Resources.BOOK_LOAN_REQ_CHECKOUT_TYPE)) {

            if ((request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13) == null  &&request.getParameter(Resources.BOOK_LOAN_REQ_SSN) == null && request.getParameter(Resources.BOOK_LOAN_REQ_CARD_ID) == null)) {
                //First time. No data present
                RequestDispatcher rd = request.getRequestDispatcher("GenerateLoan.jsp");
                request.setAttribute(Resources.BOOK_LOAN_REQ_TYPE, Resources.BOOK_LOAN_REQ_CHECKOUT_TYPE);
                request.setAttribute(Resources.HAS_STATUS, false);
                rd.forward(request, response);
                return;
            } else if ((request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13).equals("") && request.getParameter(Resources.BOOK_LOAN_REQ_SSN).equals("") && request.getParameter(Resources.BOOK_LOAN_REQ_CARD_ID).equals(""))) {
                  RequestDispatcher rd = request.getRequestDispatcher("GenerateLoan.jsp");
                request.setAttribute(Resources.BOOK_LOAN_REQ_TYPE, Resources.BOOK_LOAN_REQ_CHECKOUT_TYPE);
                request.setAttribute(Resources.HAS_STATUS, true);
                request.setAttribute(Resources.STATUS_TYPE, Status.WARNING);
                request.setAttribute(Resources.STATUS_HEADER, "Empty search");
                request.setAttribute(Resources.STATUS_BODY, "Please enter something to search");
                rd.forward(request, response);
                return;
            } else if (request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13) != null  && request.getParameter(Resources.BOOK_LOAN_REQ_CARD_ID) == null) {
                
                RequestDispatcher rd = request.getRequestDispatcher("GenerateLoan.jsp");
                request.setAttribute(Resources.BOOK_LOAN_REQ_ISBN13, request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13));
                request.setAttribute(Resources.BOOK_LOAN_REQ_TYPE, Resources.BOOK_LOAN_REQ_CHECKOUT_TYPE);
                request.setAttribute(Resources.HAS_STATUS, true);
                request.setAttribute(Resources.STATUS_TYPE, Status.WARNING);
                request.setAttribute(Resources.STATUS_HEADER, "Complete checkout");
                request.setAttribute(Resources.STATUS_BODY, "Enter the card number of the borrower to complete the checkout");
                rd.forward(request, response);
                return;
            } else {
            	DatabaseConnection dbConnection = new DatabaseConnection();
                try {
                    

                    String ISBN13 = request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13);
                    String CARD_ID = request.getParameter(Resources.BOOK_LOAN_REQ_CARD_ID);

                    if (ISBN13.equals("")  || CARD_ID.equals("")) {
                        RequestDispatcher rd = request.getRequestDispatcher("GenerateLoan.jsp");
                        request.setAttribute(Resources.BOOK_LOAN_REQ_ISBN13, request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13));
                        request.setAttribute(Resources.BOOK_LOAN_REQ_CARD_ID, request.getParameter(Resources.BOOK_LOAN_REQ_CARD_ID));
                        request.setAttribute(Resources.BOOK_LOAN_REQ_TYPE, Resources.BOOK_LOAN_REQ_CHECKOUT_TYPE);
                        request.setAttribute(Resources.HAS_STATUS, true);
                        request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
                        request.setAttribute(Resources.STATUS_HEADER, "Error occured");
                        request.setAttribute(Resources.STATUS_BODY, "Please fill all the fields");
                        rd.forward(request, response);
                        return;
                    }

                    
                    dbConnection.openConnection();
                    StringBuffer sqlString = new StringBuffer();

                    sqlString.append("SELECT COUNT(*) from books where isbn13 = ?");
                    dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                    dbConnection.preparedStatement.setString(1, ISBN13);
                    dbConnection.resultSet = dbConnection.preparedStatement.executeQuery();
                    dbConnection.resultSet.next();
                    if (dbConnection.resultSet.getInt(1) == 0) {
                        
                        RequestDispatcher rd = request.getRequestDispatcher("GenerateLoan.jsp");
                        request.setAttribute(Resources.BOOK_LOAN_REQ_ISBN13, request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13));
                        request.setAttribute(Resources.BOOK_LOAN_REQ_CARD_ID, request.getParameter(Resources.BOOK_LOAN_REQ_CARD_ID));
                        request.setAttribute(Resources.BOOK_LOAN_REQ_TYPE, Resources.BOOK_LOAN_REQ_CHECKOUT_TYPE);
                        request.setAttribute(Resources.HAS_STATUS, true);
                        request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
                        request.setAttribute(Resources.STATUS_HEADER, "Error occured");
                        request.setAttribute(Resources.STATUS_BODY, "The book id that you have entered does not exist. Please check the id and try again");
                        rd.forward(request, response);
                        return;
                    }

                    

                    sqlString = new StringBuffer();
                    sqlString.append("SELECT COUNT(*) from borrower where card_id = ?");
                    dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                    dbConnection.preparedStatement.setString(1, CARD_ID);
                    dbConnection.resultSet = dbConnection.preparedStatement.executeQuery();
                    dbConnection.resultSet.next();
                    if (dbConnection.resultSet.getInt(1) == 0) {
                   
                        RequestDispatcher rd = request.getRequestDispatcher("GenerateLoan.jsp");
                        request.setAttribute(Resources.BOOK_LOAN_REQ_ISBN13, request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13));
                        request.setAttribute(Resources.BOOK_LOAN_REQ_CARD_ID, request.getParameter(Resources.BOOK_LOAN_REQ_CARD_ID));
                        request.setAttribute(Resources.BOOK_LOAN_REQ_TYPE, Resources.BOOK_LOAN_REQ_CHECKOUT_TYPE);
                        request.setAttribute(Resources.HAS_STATUS, true);
                        request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
                        request.setAttribute(Resources.STATUS_HEADER, "Error occured");
                        request.setAttribute(Resources.STATUS_BODY, "The card number that you have entered does not exist. Please check the id and try again");
                        rd.forward(request, response);
                        return;
                    }

                    
                    sqlString = new StringBuffer();
                    sqlString.append("SELECT ifnull((b.TOTAL_COPIES - b.COPIES_BORROWED),b.TOTAL_COPIES) as available  FROM BOOKS b WHERE ISBN13 = ?;");
                    dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                    dbConnection.preparedStatement.setString(1, ISBN13);
                    dbConnection.resultSet = dbConnection.preparedStatement.executeQuery();
                    dbConnection.resultSet.next();
                    if (dbConnection.resultSet.getInt(1) == 0) {
                        
                        RequestDispatcher rd = request.getRequestDispatcher("GenerateLoan.jsp");
                        request.setAttribute(Resources.BOOK_LOAN_REQ_ISBN13, request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13));
                        request.setAttribute(Resources.BOOK_LOAN_REQ_TYPE, Resources.BOOK_LOAN_REQ_CHECKOUT_TYPE);
                        request.setAttribute(Resources.HAS_STATUS, true);
                        request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
                        request.setAttribute(Resources.STATUS_HEADER, "Error occured");
                        request.setAttribute(Resources.STATUS_BODY, "No more copies of this book are available ");
                        rd.forward(request, response);
                        return;
                    }

                    
                    sqlString = new StringBuffer();
                    sqlString.append("SELECT COUNT(*) from book_loans where date_in is null and isbn13 = ?  and card_id = ?;");
                    dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                    dbConnection.preparedStatement.setString(1, ISBN13);
                    dbConnection.preparedStatement.setString(2, CARD_ID);
                    dbConnection.resultSet = dbConnection.preparedStatement.executeQuery();
                    dbConnection.resultSet.next();
                    if (dbConnection.resultSet.getInt(1) > 0) {
                        RequestDispatcher rd = request.getRequestDispatcher("GenerateLoan.jsp");
                        request.setAttribute(Resources.BOOK_LOAN_REQ_ISBN13, request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13));
                        request.setAttribute(Resources.BOOK_LOAN_REQ_CARD_ID, request.getParameter(Resources.BOOK_LOAN_REQ_CARD_ID));
                        request.setAttribute(Resources.BOOK_LOAN_REQ_TYPE, Resources.BOOK_LOAN_REQ_CHECKOUT_TYPE);
                        request.setAttribute(Resources.HAS_STATUS, true);
                        request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
                        request.setAttribute(Resources.STATUS_HEADER, "Error occured");
                        request.setAttribute(Resources.STATUS_BODY, "This book has already been given out to the same user. Please check details entered.");
                        rd.forward(request, response);
                        return;
                    }

                    
                    sqlString = new StringBuffer();
                    sqlString.append("SELECT COUNT(*) from book_loans where card_id = ? and date_in IS NULL;");
                    dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                    dbConnection.preparedStatement.setString(1, CARD_ID);
                    dbConnection.resultSet = dbConnection.preparedStatement.executeQuery();
                    dbConnection.resultSet.next();
                    if (dbConnection.resultSet.getInt(1) >= 3) {
                        RequestDispatcher rd = request.getRequestDispatcher("GenerateLoan.jsp");
                        request.setAttribute(Resources.BOOK_LOAN_REQ_ISBN13, request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13));
                        request.setAttribute(Resources.BOOK_LOAN_REQ_CARD_ID, request.getParameter(Resources.BOOK_LOAN_REQ_CARD_ID));
                        request.setAttribute(Resources.BOOK_LOAN_REQ_TYPE, Resources.BOOK_LOAN_REQ_CHECKOUT_TYPE);
                        request.setAttribute(Resources.HAS_STATUS, true);
                        request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
                        request.setAttribute(Resources.STATUS_HEADER, "Error occured");
                        request.setAttribute(Resources.STATUS_BODY, "3 books have already been assigned to the borrower. Cannot assign this book to the user. Please try any other book.");
                        rd.forward(request, response);
                        return;
                    }

                    
                    sqlString = new StringBuffer();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date currentDate = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(currentDate); 
                    c.add(Calendar.DATE, 14); 
                    String output = sdf.format(c.getTime());
                    sqlString.append("insert into book_loans (isbn13,card_id,date_out,due_date) values (?,?,?,?);");
                    dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                    dbConnection.preparedStatement.setString(1, ISBN13);
                    dbConnection.preparedStatement.setString(2, CARD_ID);
                    dbConnection.preparedStatement.setString(3, sdf.format(currentDate));
                    dbConnection.preparedStatement.setString(4, output);
                    dbConnection.preparedStatement.executeUpdate();
 
                   sqlString = new StringBuffer();
                    sqlString.append("update books set copies_borrowed = copies_borrowed + 1 where isbn13 = ?;");
                    dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                    dbConnection.preparedStatement.setString(1,ISBN13);
                    dbConnection.preparedStatement.executeUpdate();
                    
                    
                    dbConnection.closeConnection();

                    RequestDispatcher rd = request.getRequestDispatcher("GenerateLoan.jsp");
                    request.setAttribute(Resources.BOOK_LOAN_REQ_ISBN13, "");
                    request.setAttribute(Resources.BOOK_LOAN_REQ_CARD_ID, "");
                    request.setAttribute(Resources.BOOK_LOAN_REQ_LOAN_ID, "");
                    request.setAttribute(Resources.BOOK_LOAN_REQ_TYPE, Resources.BOOK_LOAN_REQ_CHECKOUT_TYPE);
                    request.setAttribute(Resources.HAS_STATUS, true);
                    request.setAttribute(Resources.STATUS_TYPE, Status.SUCCESS);
                    request.setAttribute(Resources.STATUS_HEADER, "Book checked out");
                    request.setAttribute(Resources.STATUS_BODY, "Book has been successfully checked out. Book has been assigned. Book has to be returned in 14 days.");
                    rd.forward(request, response);
                    return;
                    

                } catch (Exception e) {
                    dbConnection.closeConnection();
                    RequestDispatcher rd = request.getRequestDispatcher("GenerateLoan.jsp");
                    request.setAttribute(Resources.BOOK_LOAN_REQ_ISBN13, request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13));
                    request.setAttribute(Resources.BOOK_LOAN_REQ_CARD_ID, request.getParameter(Resources.BOOK_LOAN_REQ_CARD_ID));
                    request.setAttribute(Resources.BOOK_LOAN_REQ_LOAN_ID, request.getParameter(Resources.BOOK_LOAN_REQ_LOAN_ID));
                    request.setAttribute(Resources.BOOK_LOAN_REQ_TYPE, Resources.BOOK_LOAN_REQ_CHECKOUT_TYPE);
                    request.setAttribute(Resources.HAS_STATUS, true);
                    request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
                    request.setAttribute(Resources.STATUS_HEADER, "Error occured");
                    request.setAttribute(Resources.STATUS_BODY, "SQL Exception caught. Exception is " + e.toString());
                    rd.forward(request, response);
                } finally {
                    dbConnection.closeConnection();
                }

            }
        } else {

            if ((request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13) == null &&request.getParameter(Resources.BOOK_LOAN_REQ_LOAN_ID) == null  && request.getParameter(Resources.BOOK_LOAN_REQ_CARD_ID) == null && request.getParameter(Resources.BOOK_LOAN_REQ_BORROWER_NAME) == null)) {
                RequestDispatcher rd = request.getRequestDispatcher("GenerateLoan.jsp");
                request.setAttribute(Resources.BOOK_LOAN_REQ_TYPE, Resources.BOOK_LOAN_REQ_CHECKIN_TYPE);
                request.setAttribute(Resources.HAS_STATUS, false);
                rd.forward(request, response);
            } else if ((request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13).equals("") &&  request.getParameter(Resources.BOOK_LOAN_REQ_LOAN_ID).equals("") && request.getParameter(Resources.BOOK_LOAN_REQ_CARD_ID).equals("") && request.getParameter(Resources.BOOK_LOAN_REQ_BORROWER_NAME).equals(""))) {
                RequestDispatcher rd = request.getRequestDispatcher("GenerateLoan.jsp");
                request.setAttribute(Resources.BOOK_LOAN_REQ_TYPE, Resources.BOOK_LOAN_REQ_CHECKIN_TYPE);
                request.setAttribute(Resources.HAS_STATUS, true);
                request.setAttribute(Resources.STATUS_TYPE, Status.WARNING);
                request.setAttribute(Resources.STATUS_HEADER, "Empty search");
                request.setAttribute(Resources.STATUS_BODY, "Please enter something to search");
                rd.forward(request, response);
            } else {
            	DatabaseConnection dbConnection = new DatabaseConnection();
                try {
                    String ISBN13 = request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13);
                    String CARD_ID = request.getParameter(Resources.BOOK_LOAN_REQ_CARD_ID);
                    String borrowerName = request.getParameter(Resources.BOOK_LOAN_REQ_BORROWER_NAME);

                    StringBuffer sqlString = new StringBuffer();
                    sqlString.append("SELECT borrower.fname, borrower.lname, book_loans.card_id, book_loans.isbn13, book_loans.loan_id, book_loans.due_date "
                            + "FROM borrower join book_loans on borrower.card_id = book_loans.card_id "
                            + "where book_loans.date_in IS NULL");
                    if (!ISBN13.equals("")) {
                        sqlString.append(" and book_loans.isbn13 = ?");
                    }
                    if (!CARD_ID.equals("")) {
                        sqlString.append(" and book_loans.card_id = ?");
                    }
                    if (!borrowerName.equals("")) {
                        sqlString.append(" and borrower.fname like ? or borrower.lname like ?");
                    }
                    sqlString.append(";");
                    dbConnection.openConnection();
                    dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                    int count = 1;
                    if (!ISBN13.equals("")) {
                        dbConnection.preparedStatement.setString(count++, ISBN13);
                    }
                    if (!CARD_ID.equals("")) {
                        dbConnection.preparedStatement.setString(count++, CARD_ID);
                    }
                    if (!borrowerName.equals("")) {
                        dbConnection.preparedStatement.setString(count++, "%" + borrowerName.trim() + "%");
                        dbConnection.preparedStatement.setString(count++, "%" + borrowerName.trim() + "%");
                    }

                    dbConnection.resultSet = dbConnection.preparedStatement.executeQuery();
                    ArrayList<LoanList> list = new ArrayList<>();
                    while (dbConnection.resultSet.next()) {
                        list.add(new LoanList(dbConnection.resultSet.getString("ISBN13"),dbConnection.resultSet.getString("LOAN_ID"), dbConnection.resultSet.getString("CARD_ID"),dbConnection.resultSet.getString("DUE_DATE")));
                    }

                    dbConnection.closeConnection();

                    RequestDispatcher rd = request.getRequestDispatcher("CheckIn.jsp");
                    request.setAttribute(Resources.BOOK_LOAN_REQ_TYPE, Resources.BOOK_LOAN_REQ_CHECKIN_TYPE);
                    request.setAttribute(Resources.HAS_STATUS, true);
                    request.setAttribute(Resources.STATUS_TYPE, Status.SUCCESS);
                    request.setAttribute(Resources.STATUS_HEADER, "Searching success");
                    request.setAttribute(Resources.STATUS_BODY, "Searching success");
                    request.setAttribute(Resources.BOOK_LOAN_JSP_REQ, list);
                    rd.forward(request, response);
                    return;
                } catch (Exception e) {
                    dbConnection.closeConnection();
                    RequestDispatcher rd = request.getRequestDispatcher("CheckIn.jsp");
                    request.setAttribute(Resources.BOOK_LOAN_REQ_TYPE, request.getAttribute(Resources.BOOK_LOAN_REQ_TYPE));
                    request.setAttribute(Resources.HAS_STATUS, true);
                    request.setAttribute(Resources.STATUS_TYPE, Status.ERROR);
                    request.setAttribute(Resources.STATUS_HEADER, "MySQL Error caught");
                    request.setAttribute(Resources.STATUS_BODY, "MySQL Exception caught. The error is  " + e.toString());
                    rd.forward(request, response);
                    return;
                }
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
