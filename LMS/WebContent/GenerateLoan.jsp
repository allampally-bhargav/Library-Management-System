<%@page import="utilities.Status"%>
<%@page import="java.util.ArrayList"%>
<%@page import="list.LoanList"%>
<%@page import="Parameters.BookLoan"%>
<%@page import="utilities.Resources"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Loan</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>
    <body>

        <%!
            boolean hasStatus = false;
        %>
        <%
            if (request.getAttribute(Resources.HAS_STATUS) != null) {
                if ((Boolean) request.getAttribute(Resources.HAS_STATUS) == true) {
                    hasStatus = true;
                }
            } else {
                hasStatus = false;
            }
        %>
        
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="Home.html">Library Management System</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="Home.html">Home</a></li>
                        <li><a href="Searchbook">Search</a></li>
                        <li class="active"><a href="#">Loan</a></li>
                        <li><a href="FineTrack">Fines</a></li>
                        <li><a href="Borrowers">Borrowers</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        
        <br><br><br><br>

        <div class="container">
            
            <ul class="nav nav-tabs">
                <li <% if ((request.getAttribute(Resources.BOOK_LOAN_REQ_TYPE) == null) || request.getAttribute(Resources.BOOK_LOAN_REQ_TYPE).equals(Resources.BOOK_LOAN_REQ_CHECKOUT_TYPE)) {
                        out.println("class=\"active\"");
                    }%>><a data-toggle="tab" href="#checkOut" style="font-weight:bold">CheckOut</a></li>
                <li <% if (request.getAttribute(Resources.BOOK_LOAN_REQ_TYPE) != null && request.getAttribute(Resources.BOOK_LOAN_REQ_TYPE).equals(Resources.BOOK_LOAN_REQ_CHECKIN_TYPE)) {
                        out.println("class=\"active\"");
                    }%>><a data-toggle="tab" href="#checkIn" style="font-weight:bold">CheckIn</a></li>
            </ul>

            <br>

            <div class="tab-content">

                <div id="checkOut" class="tab-pane fade in <% if ((request.getAttribute(Resources.BOOK_LOAN_REQ_TYPE) == null && request.getAttribute(Resources.BOOK_LOAN_REQ_TYPE) == null) || request.getAttribute(Resources.BOOK_LOAN_REQ_TYPE).equals(Resources.BOOK_LOAN_REQ_CHECKOUT_TYPE)) {
                        out.println("active");
                    }%>">
                    <div class="panel panel-default">
                        <div class="panel-heading" style="font-weight:bold">CheckOut Book</div>
                        <div class="panel-body">
                            <form class="form-horizontal" role="form" action="GenerateLoan" method="POST">
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="<%=Resources.BOOK_LOAN_REQ_ISBN13%>">ISBN13:</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" style="margin:10px" name="<%= Resources.BOOK_LOAN_REQ_ISBN13%>" 
                                               placeholder="Enter ISBN13"
                                               value="<% if (request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13) != null) {
                                                       out.println(request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13));
                                                   }%>">
                                    </div>
                             

                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="<%= Resources.BOOK_LOAN_REQ_CARD_ID%>">Card Number:</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="<%= Resources.BOOK_LOAN_REQ_CARD_ID%>" 
                                               placeholder="Enter borrower card number" 
                                               value="<%if (request.getParameter(Resources.BOOK_LOAN_REQ_CARD_ID) != null) {
                                                       out.println(request.getParameter(Resources.BOOK_LOAN_REQ_CARD_ID));
                                                   }%>">
                                    </div>
                                </div>
                                
                                <input type ="hidden" name ="<%=Resources.BOOK_LOAN_REQ_TYPE%>" value="<%=Resources.BOOK_LOAN_REQ_CHECKOUT_TYPE%>">
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-primary">Submit</button>
                                    </div>
                                </div>
                            </form>
                            <% if (hasStatus && request.getAttribute(Resources.BOOK_LOAN_REQ_TYPE).equals(Resources.BOOK_LOAN_REQ_CHECKOUT_TYPE)) {%>
                            <h5>
                                <% if (request.getAttribute(Resources.STATUS_TYPE) == Status.ERROR) {
                                %>
                                <div class="alert alert-danger" role="alert">
                                    <strong>
                                        <%= request.getAttribute(Resources.STATUS_BODY)%>
                                    </strong>
                                </div>
                                <% } else if (request.getAttribute(Resources.STATUS_TYPE) == Status.WARNING) {
                                %>
                                <div class="alert alert-info" role="alert">
                                    <strong>
                                        <%= request.getAttribute(Resources.STATUS_BODY)%>
                                    </strong>
                                </div>
                                <% } else if (request.getAttribute(Resources.STATUS_TYPE) == Status.SUCCESS) {
                                %>
                                <div class="alert alert-success" role="alert">
                                    <strong>
                                        <%= request.getAttribute(Resources.STATUS_BODY)%>
                                    </strong>
                                </div>
                                <% }%>
                            </h5>
                            <%}%>
                        </div>
                    </div>
                </div>

                <div id="checkIn" class="tab-pane fade in <% if (request.getAttribute(Resources.BOOK_LOAN_REQ_TYPE) != null && request.getAttribute(Resources.BOOK_LOAN_REQ_TYPE).equals(Resources.BOOK_LOAN_REQ_CHECKIN_TYPE)) {
                        out.println("active");
                    }%>">
                    <div class="panel panel-default">
                        <div class="panel-heading" style="font-weight:bold">CheckIn Book</div>
                        <div class="panel-body">
                            <form class="form-horizontal" role="form" action="GenerateLoan" method="POST">
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="<%=Resources.BOOK_LOAN_REQ_ISBN13%>">ISBN13:</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="<%= Resources.BOOK_LOAN_REQ_ISBN13%>" 
                                               placeholder="Enter ISBN13" 
                                               value="<% if (request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13) != null) {
                                                       out.println(request.getParameter(Resources.BOOK_LOAN_REQ_ISBN13));
                                                   }%>">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="<%= Resources.BOOK_LOAN_REQ_CARD_ID%>">Card Number:</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="<%= Resources.BOOK_LOAN_REQ_CARD_ID%>" 
                                               placeholder="Enter borrower card number" 
                                               value="<%if (request.getParameter(Resources.BOOK_LOAN_REQ_CARD_ID) != null) {
                                                       out.println(request.getParameter(Resources.BOOK_LOAN_REQ_CARD_ID));
                                                   }%>">
                                    </div>
                                </div>
                                 
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="<%= Resources.BOOK_LOAN_REQ_BORROWER_NAME%>">Borrower Name:</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="<%= Resources.BOOK_LOAN_REQ_BORROWER_NAME%>" 
                                               placeholder="Enter the borrower first name or last name" 
                                               value="<%if (request.getParameter(Resources.BOOK_LOAN_REQ_BORROWER_NAME) != null) {
                                                       out.println(request.getParameter(Resources.BOOK_LOAN_REQ_BORROWER_NAME));
                                                   }%>">
                                    </div>
                                </div>
                                <input type ="hidden" name ="<%=Resources.BOOK_LOAN_REQ_TYPE%>" value="<%=Resources.BOOK_LOAN_REQ_CHECKIN_TYPE%>">
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-primary">Submit</button>
                                    </div>
                                </div>
                            </form>



                            <% if (hasStatus && request.getAttribute(Resources.BOOK_LOAN_REQ_TYPE).equals(Resources.BOOK_LOAN_REQ_CHECKIN_TYPE)) {%>
                            <h5>
                                <% if (request.getAttribute(Resources.STATUS_TYPE) == Status.ERROR) {
                                %>
                                <div class="alert alert-danger" role="alert">
                                    <strong>
                                        <%= request.getAttribute(Resources.STATUS_BODY)%>
                                    </strong>
                                </div>
                                <% } else if (request.getAttribute(Resources.STATUS_TYPE) == Status.WARNING) {
                                %>
                                <div class="alert alert-info" role="alert">
                                    <strong>
                                        <%= request.getAttribute(Resources.STATUS_BODY)%>
                                    </strong>
                                </div>
                                <% } else if (request.getAttribute(Resources.STATUS_TYPE) == Status.SUCCESS) {
                                %>
                                <div class="alert alert-success" role="alert">
                                    <strong>
                                        <%= request.getAttribute(Resources.STATUS_BODY)%>
                                    </strong>
                                </div>
                                <% }%>
                            </h5>
                            <%}%>
                        </div>
                    </div>
                    

                    <%!
                        boolean hasData = false;
                    %>

                    <%
                        if (request.getAttribute(Resources.BOOK_LOAN_JSP_REQ) != null) {
                            hasData = true;
                        } else {
                            hasData = false;
                        }
                    %>
                    <div class="panel panel-default">
                        <div class="panel-heading">Search Results</div>
                        <div class="panel-body">
                            <%
                                if (hasData) {
                            %>
                            <div class="table-responsive">
                                <table class="table table-bordered">
                                    <tr>
                                        <th>First Name</th>
                                        <th>Last Name</th>
                                        <th>Card ID</th>
                                        <th>ISBN13</th>
                                        <th>Due Date</th>
                                        <th>Action</th>
                                    </tr>
                                    <%!
                                        ArrayList<LoanList> data = new ArrayList<LoanList>();
                                    %>
                                    <%
                                        data = (ArrayList<LoanList>) request.getAttribute(Resources.BOOK_LOAN_JSP_REQ);
                                        for (LoanList bean : data) {
                                            out.println("<tr>");
                                    %>
                                    <%= "<td>" + bean.getISBN13()
                                            + "</td>"%>
                                    <%= "<td>" + bean.getCARD_ID()
                                            + "</td>"%>
                                    <%= "<td>" + bean.getLOAN_ID()
                                            + "</td>"%>
                                    <%= "<td>" + bean.getDUE_DATE()
                                            + "</td>"%>
                                    <td>
                                        <%="<form method=\"POST\" action=\"BookCheckIn\">"
                                                + "<input type='submit' class='btn btn-sm btn-primary' value='Check In'>"
                                                + "<input type='hidden' name='" + Resources.CHECK_IN_REQ_ISBN13 + "' value='" + bean.getISBN13() + "'> "
                                                + "<input type='hidden' name='" + Resources.CHECK_IN_REQ_LOAN_ID + "' value='" + bean.getLOAN_ID() + "'> "
                                                + "<input type='hidden' name='" + Resources.CHECK_IN_REQ_CARD_ID + "' value='" + bean.getCARD_ID() + "'> "
                                                + "</form>"%>
                                    </td>

                                    <%
                                            out.println("</tr>");
                                        }
                                    %>
                                    <% if (data.size() == 0) { %> 
                                    <h4><span class="label label-warning">No results found</span> </h4>       
                                    <%}%>
                                </table>
                            </div>
                            <%                    } else {
                            %>
                            <h4><span class="label label-warning">No results found</span> </h4>       
                            <% }%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>