<%@page import="utilities.Status"%>
<%@page import="utilities.Resources"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Check in page</title>
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
                    <a class="navbar-brand" href="index.html">Library Management System</a>
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

        <br><br><br>


        <div class="panel panel-default">
            <div class="panel-heading">Check in</div>
            <div class="panel-body">
                <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/CheckIn" method="POST">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Resources.CHECK_IN_REQ_DATE%>">Date:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Resources.CHECK_IN_REQ_DATE%>" placeholder="Enter date for check-in in yyyy-MM-dd format only"/>
                        </div>
                    </div>
                    <input type="hidden" name="<%= Resources.CHECK_IN_REQ_ISBN13%>" value="<%= request.getParameter(Resources.CHECK_IN_REQ_ISBN13)%>">
                    <input type="hidden" name="<%= Resources.CHECK_IN_REQ_CARD_ID%>" value="<%= request.getParameter(Resources.CHECK_IN_REQ_CARD_ID)%>">
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