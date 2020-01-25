<%@page import="utilities.Status"%>
<%@page import="java.util.List"%>
<%@page import="list.SearchBookList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="utilities.Resources"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Book</title>
    </head>
    <body>
        <!DOCTYPE html>
    <html>
        <head>
            <title>Book Search and Availability</title>
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
                if (request.getAttribute(Resources.BOOK_SEARCH_JSP_REQ) != null) {                                                                                                                        
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
                            <li class="active"><a href="#">Search</a></li>
                            <li><a href="GenerateLoan">Loan</a></li>
                            <li><a href="FineTrack">Fines</a></li>
                            <li><a href="Borrowers">Borrowers</a></li>
                        </ul>
                    </div>
                </div>
            </nav>


            <br><br><br>



            <div class="panel panel-default">
                <div class="panel-heading" style="font-weight:bold">Book Search </div>
                <div class="panel-body">
                    <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/Searchbook" method="POST">
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="<%=Resources.BOOK_SEARCH%>">Search:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="<%= Resources.BOOK_SEARCH%>" placeholder="Enter ISBN or Book Title or Author(s)" >
                                  <button type="submit" class="btn btn-primary" style="margin:10px">Submit</button>
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
                                <th>ISBN13</th>
                                <th>ISBN10</th>
                                <th>Title</th>
                                <th>Author</th>
                                
                                <th>No of copies</th>
                                
                                <th>Action</th>
                            </tr>
                            <%!
                                ArrayList<SearchBookList> data = new ArrayList<SearchBookList>();
                            %>
                            <%
                                data = (ArrayList<SearchBookList>) request.getAttribute(Resources.BOOK_SEARCH_JSP_REQ);
                                for (SearchBookList bean : data) {
                            %>
                            <tr>
                                <td><%= bean.getISBN13()%>
                                </td>
                                <td><%= bean.getISBN10()%>
                                </td>
                                <td><%= bean.getTitle()%>
                                </td>
                                <td><%= bean.getAuthors()%>
                                </td>
                                
                                <td><%= bean.getTOTAL_COPIES()%>
                                </td>
                               
                                <td>
                                    <%
                                        if (Integer.parseInt(bean.getTOTAL_COPIES()) > 0) {
                                    %>
                                    <form method='POST' action='<%= request.getContextPath()%>/GenerateLoan'>
                                        <input type='submit' class='btn btn-sm btn-primary' value='Check Out'/>
                                        <input type='hidden' name='<%= Resources.BOOK_LOAN_REQ_ISBN13%>' value='<%=bean.getISBN13()%>'/> 
                                        
                                    </form>

                                    <%
                                    } else {
                                    %>
                                    <%= "Loaning not available"%>
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
</body>
</html>