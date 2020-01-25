<%@page import="utilities.Status"%>
<%@page import="utilities.Resources"%>
<%@page contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Borrowers</title>
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
                        <li><a href="GenerateLoan">Loan</a></li>
                        <li><a href="FineTrack">Fines</a></li>
                        <li class="active"><a href="#">Borrowers</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <br>
        <br>
        <br>

        <div class="panel panel-default">
            <div class="panel-heading" style="font-weight:bold">New Borrower</div>
            <div class="panel-body">
                <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/Borrowers" method="POST">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Resources.NEW_BORROWER_REQ_SSN%>">SSN:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Resources.NEW_BORROWER_REQ_SSN%>" placeholder="Enter SSN" 
                                   value="<% if (request.getParameter(Resources.NEW_BORROWER_REQ_SSN) != null) {
                                           out.println(request.getParameter(Resources.NEW_BORROWER_REQ_SSN));
                                       }%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Resources.NEW_BORROWER_REQ_FNAME%>">First Name:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Resources.NEW_BORROWER_REQ_FNAME%>" placeholder="Enter first name" 
                                   value="<% if (request.getParameter(Resources.NEW_BORROWER_REQ_FNAME) != null) {
                                           out.println(request.getParameter(Resources.NEW_BORROWER_REQ_FNAME));
                                       }%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Resources.NEW_BORROWER_REQ_LNAME%>">Last Name:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Resources.NEW_BORROWER_REQ_LNAME%>" placeholder="Enter last name"
                                   value="<% if (request.getParameter(Resources.NEW_BORROWER_REQ_LNAME) != null) {
                                           out.println(request.getParameter(Resources.NEW_BORROWER_REQ_LNAME));
                                       }%>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Resources.NEW_BORROWER_REQ_EMAIL%>">Email:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Resources.NEW_BORROWER_REQ_EMAIL%>" placeholder="Enter email"
                                   value="<% if (request.getParameter(Resources.NEW_BORROWER_REQ_EMAIL) != null) {
                                           out.println(request.getParameter(Resources.NEW_BORROWER_REQ_EMAIL));
                                       }%>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Resources.NEW_BORROWER_REQ_ADDRESS%>">Address:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Resources.NEW_BORROWER_REQ_ADDRESS%>" placeholder="Enter address"
                                   value="<% if (request.getParameter(Resources.NEW_BORROWER_REQ_ADDRESS) != null) {
                                           out.println(request.getParameter(Resources.NEW_BORROWER_REQ_ADDRESS));
                                       }%>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Resources.NEW_BORROWER_REQ_CITY%>">City:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Resources.NEW_BORROWER_REQ_CITY%>" placeholder="Enter city"
                                   value="<% if (request.getParameter(Resources.NEW_BORROWER_REQ_CITY) != null) {
                                           out.println(request.getParameter(Resources.NEW_BORROWER_REQ_CITY));
                                       }%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Resources.NEW_BORROWER_REQ_STATE%>">State:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Resources.NEW_BORROWER_REQ_STATE%>" placeholder="Enter state"
                                   value="<% if (request.getParameter(Resources.NEW_BORROWER_REQ_STATE) != null) {
                                           out.println(request.getParameter(Resources.NEW_BORROWER_REQ_STATE));
                                       }%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Resources.NEW_BORROWER_REQ_PHONE%>">Phone no:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Resources.NEW_BORROWER_REQ_PHONE%>" placeholder="Enter phone number"
                                   value="<% if (request.getParameter(Resources.NEW_BORROWER_REQ_PHONE) != null) {
                                           out.println(request.getParameter(Resources.NEW_BORROWER_REQ_PHONE));
                                       }%>">
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
</body>
</html>