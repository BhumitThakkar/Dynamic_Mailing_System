<%@page import="model.EmailIds"%>
<%@page import="model.EmailIdLists"%>
<%@page import="java.util.Set"%>
<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="icon" href="img/favicon.png" sizes="16x16" type="image/png"/>
	<title>DMS</title>
	<%@include file="CommonPanel/IncludeCSS.jsp" %>
	<%@include file="CommonPanel/IncludeJS.jsp" %>
</head>

<body>
<%
/* If not logged in:*/
User user = (User) session.getAttribute("Current User");
if(user == null){
	response.sendRedirect("welcome.jsp");
}

Set<EmailIdLists> emailIdLists = user.getEmailIdLists();

boolean isEmailIdAndListInsertionSuccessful = "true".equals(request.getParameter("EmailIdAndListInsertionSuccessful"));						// From EmailIdListsServlet
boolean isInsertionErrorInListName = "true".equals(request.getParameter("InsertionErrorInListName"));										// From EmailIdListsServlet
boolean isInsertionErrorInEmail = "true".equals(request.getParameter("InsertionErrorInEmail"));												// From EmailIdListsServlet
int EmailIdNotInsertedNumber = 0;
if(request.getParameter("Number") != null)
{
	EmailIdNotInsertedNumber = Integer.parseInt(request.getParameter("Number"));															// From EmailIdListsServlet
}
%>
	<!-- Header -->
	<div id="navigation">
		<nav class="navbar navbar-inverse">
		  <div class="container-fluid">
		    <div class="navbar-header">
		      <a class="navbar-brand" style="font-weight:900;">Dynamic Mailing System</a>
		    </div>
		    <ul class="nav navbar-nav">
		      <li class="active" style="font-weight:700;"><a href="dms.jsp">Home</a></li>
		      <li class="dropdown" style="font-weight:700;">
		        <a class="dropdown-toggle" data-toggle="dropdown" href="#">To Email Lists
		        <span class="caret"></span></a>
		        <ul class="dropdown-menu">
		        	<%
		        	if(emailIdLists.size() != 0){
						for(EmailIdLists emailIdList : emailIdLists){%>
							<li><a href="#"><%= emailIdList.getListName() %></a></li>
						<%}
					}
		        	%>
		          <li><a href="emailIdList.jsp">Add a New "To Email List"</a></li>
		        </ul>
		      </li>
	        </ul>
		    <ul class="nav navbar-nav navbar-right">
		      <li><a><span class="glyphicon glyphicon-user"></span> <%= user.getFirstName() %> <%= user.getLastName() %></a></li>
		      <li><a id="logOut"><span class="glyphicon glyphicon-log-in"></span> Logout </a></li>		<!-- href defined in myJQuery.js -->
		    </ul>
		  </div>
		</nav>
	</div>
    <!-- Header -->
	<div class="white-bg block-blue-design">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-4">
					
					<form action="EmailIdListServlet" method="post">
					
						<div class="form-group">
						    <label for="txtListName">List Name</label>
						    <input type="text" class="form-control" id="txtListName" name="txtListName" placeholder="Enter List name" required/>
					    </div>

				    	<label for="txtEmailId">Add Email to List</label>
					    <div class="form-group" id="addTxtEmailId">
					    	<input type="email" class="form-control" id="txtEmailId1" name="txtEmailId1" onblur="validateEmailIdById('txtEmailId1');" placeholder="Enter email" required/>
					    </div>
					    <button type="button" id="addEmailBtn" onclick="addTextbox('addTxtEmailId');" class="btn btn-primary">Add</button>
					    <button type="button" id="removeEmailBtn" onclick="removeTextbox('addTxtEmailId');" class="btn btn-danger" style="display:none;">Remove</button>

						<br/><br/>
						<button type="submit" class="btn btn-primary">Save</button>

					</form>

				</div>
				<div class="col-md-8">
						<%
						if(isEmailIdAndListInsertionSuccessful){%>
							<span class="help-block" style="color: green;">Email List Successfully Inserted</span>
						<%}%>
						<%
						if(isInsertionErrorInListName){%>
							<span class="help-block" style="color: red;">Email List Insertion Unsuccessful</span>
						<%}%>
						<%
						if(isInsertionErrorInEmail){%>
							<span class="help-block" style="color: red;">Email Id Number: <%=EmailIdNotInsertedNumber%> Created Problem in Insertion</span>
						<%}%>

					<%
					if(emailIdLists.size() != 0){
					for(EmailIdLists emailIdList : emailIdLists){
					%>
						<%! int collapseId = 1; %>
						<button class="btn btn-primary dropdown-toggle" data-toggle="collapse" data-target="#EmailIdList<%=collapseId%>"><%= emailIdList.getListName() %> <span class="caret"></span></button>
						<div id="EmailIdList<%=collapseId%>" class="collapse">
							<%
							Set<EmailIds> emailIds= emailIdList.getEmailIds();
							if(emailIds.size() != 0){%>
								<ul class="list-group">
									<% for(EmailIds emailId : emailIds){ %>
									<li class="list-group-item"><%= emailId.getEmailId() %></li>
									<%}
 								collapseId++;
								%>
								</ul>
							<%}
							else{%>
								<span class="help-block" style="color: red;">No Emails Added in this List</span>
							<%}%>
						</div><br/><br/>
					<%}
					}else{%>
						<span class="help-block" style="color: red;">No Email Lists Added for this User</span>
					<%}%>
				</div>
			</div>
		</div>
	</div>

</body>
</html>