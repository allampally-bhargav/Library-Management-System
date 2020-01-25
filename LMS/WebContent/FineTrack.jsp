<%@page import="list.FineList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="utilities.Status"%>
<%@page import="utilities.Resources"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fine Track</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>
    <body>
        <%!
            boolean hasData = false;
            boolean hasStatus = false;
        %>

        <%
            if (request.getAttribute(Resources.FINE_JSP_REQ) != null) {
                hasData = true;
            } else {
                hasData = false;
            }
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
                    <a class="navbar-brand" href="index.html">Library Management System</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="Home.html">Home</a></li>
                        <li><a href="Searchbook">Search</a></li>
                        <li><a href="GenerateLoan">Loan</a></li>
                        <li class="active"><a href="#">Fines</a></li>
                        <li><a href="Borrowers">Borrowers</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <br><br><br>

        <div class="panel panel-default">
            <div class="panel-heading" style="font-weight:bold">Refresh</div>
            <div class="panel-body">
                <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/RefreshFines" method="POST">
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">Press here to refresh the data</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <br>

        <div class="panel panel-default">
            <div class="panel-heading" style="font-weight:bold">Search</div>
            <div class="panel-body">
                <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/FineTrack" method="POST">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Resources.FINE_REQ_BORROWER_NAME%>">Borrower Name:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Resources.FINE_REQ_BORROWER_NAME%>" placeholder="Enter part or full first name / last name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%= Resources.FINE_REQ_CARD_ID%>">Card number:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Resources.FINE_REQ_CARD_ID%>" placeholder="Enter card no">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </div>
                    </div>
                </form>

                <% if (hasStatus) {%>
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

        <div class="panel panel-default">
            <div class="panel-heading" style="font-weight:bold">Search Results</div>
            <div class="panel-body">
                <%
                    if (hasData) {
                %>
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <tr>
                            <th>Card ID</th>
                            <th>Fine</th>
                            <th>Paid / Unpaid</th>
                            <th>Action</th>
                        </tr>
                        <%!
                            ArrayList<FineList> data = new ArrayList<FineList>();
                        %>
                        <%
                            data = (ArrayList<FineList>) request.getAttribute(Resources.FINE_JSP_REQ);
                            for (FineList bean : data) {
                        %>
                        <tr>
                            <td><%= bean.getCARD_ID()%>
                            </td>
                            <td><%= bean.getFINE_AMOUNT()%>
                            </td>
                            <td><% if (bean.isPAID()) {%>
                                <span class="label label-success">Paid</span> 
                                <% } else { %> 
                                <span class="label label-danger">Unpaid</span> 
                                <% }%>
                            </td>
                            <td>
                                <%
                                    if (!bean.isPAID()) {
                                %>
                                <form method='POST' action='<%=request.getContextPath()%>/FinePayment'>
                                    <input type='submit' class='btn btn-sm btn-primary' value='Pay Now'/>
                                    <input type='hidden' name='<%= Resources.FINE_PAYMENT_REQ_CARD_ID%>' value='<%=bean.getCARD_ID()%>'/> 
                                    <input type='hidden' name='<%= Resources.FINE_PAYMENT_REQ_AMT%>' value='<%= bean.getFINE_AMOUNT()%>'/>
                                    <input type='hidden' name='<%= Resources.FINE_PAYMENT_REQ_PAID%>' value='<%= bean.isPAID()%>'/>
                                </form>
                                <%}%>
                            </td>
                        </tr>
                        <%}%>
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
    </body>
</html>