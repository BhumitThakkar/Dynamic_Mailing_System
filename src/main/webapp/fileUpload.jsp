<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
%>

<%
String fileUploadErrorMsg = (String) request.getAttribute("message");
if(fileUploadErrorMsg != null){%>
<%= fileUploadErrorMsg %><%
request.removeAttribute("message");}
%>

	<div class="white-bg block-blue-design">
		<div class="container-fluid">
			<center><h1>Dynamic Mailing System Excel File Upload</h1></center>
			<div class="row">
				<div class="col-md-12">
					<form action="UploadServlet" method="post" enctype="multipart/form-data">		<!-- enctype="multipart/form-data when we send files on server encoding type(enctype) is necessary -->
						<input type="file" id="txtFile" name="txtFile" multiple/>
						<input type="submit"/>
					</form>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>