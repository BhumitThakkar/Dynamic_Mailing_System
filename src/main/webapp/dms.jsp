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
boolean isDynamicMailsSent = "true".equals(request.getParameter("DynamicMailsSent"));		// From DMSServlet
Set<EmailIdLists> emailIdLists = user.getEmailIdLists();

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
				<div class="col-md-12">
					<%
					if(isDynamicMailsSent){%>
						<span class="help-block" style="color: green;">Mails Sent Successfully</span>
					<%}%>
					<form action="DMSServlet" method="post" enctype="multipart/form-data">
						<div class="form-group">
						    <label for="txtFromEmailId">From</label>
						    <input type="text" class="form-control" id="txtFromEmailId" name="txtFromEmailId" value="<%= user.getEmailId() %>" readonly="readonly" required/>
					    </div>
					    <div class="form-group">
						    <label for="txtFromEmailIdPassword">Enter Password of the above email</label>
						    <input type="password" class="form-control" id="txtFromEmailIdPassword" name="txtFromEmailIdPassword"/ required>
						    <span class="help-block" style="color: red;">We don't remember your personal email password</span>
					    </div>
					    <div class="form-group">
						    <label for="txtToEmailIdListName">To</label>
						    <select class="form-control" id="txtToEmailIdListName" name="txtToEmailIdListName" required>
						    	<%
		        	if(emailIdLists.size() != 0){
						for(EmailIdLists emailIdList : emailIdLists){%>
							<option value="<%= emailIdList.getListName() %>"><%= emailIdList.getListName() %></option>
						<%}
					}
		        	%>
						    </select>
					    </div>
   						<div class="form-group">
						    <label for="txtEmailSubject">Subject</label>
						    <input type="text" class="form-control" id="txtEmailSubject" name="txtEmailSubject" required/>
					    </div>
					    
					    <div class="form-group">
					        <label for="txtEmailIdBody">Body</label>
						    <textarea rows="15" class="form-control" id="txtEmailIdBody" name="txtEmailIdBody" required>Hello <excel_B>,

Yours Faithful,
<%=user.getFirstName()%> <%= user.getLastName()%></textarea>
						</div>
					    <div class="form-group">
						    <label for="txtExcelFile">Upload Excel File</label>
					    	<input type="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" onchange="checkIfExcelFile(this)" id="txtExcelFile" name="txtExcelFile"/>
					    </div>

					    <div class="form-group">
						    <label for="txtAttachmentFile">Attachment</label>
					    <input type="file" id="txtAttachmentFile" name="txtAttachmentFile" multiple/>
					    </div>
					    
					    <button type="submit" class="btn btn-primary">Send</button>
				   	</form>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>