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
boolean isInsertionErrorTrue = "true".equals(request.getParameter("InsertionError"));		// From SignUpServlet
boolean isSignUpSuccessful = "true".equals(request.getParameter("SignUpSuccessful"));		// From SignUpServlet
boolean doesUserExist = "true".equals(request.getParameter("UserExist"));					// From SignUpServlet

boolean didEmailIdNotMatch = "true".equals(request.getParameter("EmailIdNotMatch"));		// From LogInServlet
boolean didPasswordNotMatch = "true".equals(request.getParameter("PasswordNotMatch"));		// From LogInServlet
String EmailIdForTxtLogIn = request.getParameter("EmailId");								// From LogInServlet & MailServlet
boolean isPasswordSent = "true".equals(request.getParameter("PasswordSent"));				// From MailServlet
boolean isLogOutSuccessful = "true".equals(request.getParameter("LogOutSuccessful"));		// From LogOutServlet

%>
	<div class="white-bg block-blue-design">
		<div class="container-fluid">
			<center><h1>Welcome To Dynamic Mailing System</h1></center>
				<%
					if(isLogOutSuccessful){%>
						<span class="help-block" style="color: black;">Successfully Logged Out</span>
				<%}%>
						
				<div class="row">
					<div class="col-md-6">
					<form action="SignUpServlet" method="Post">
						<h3 class="heading">Sign Up</h3>
						
						<%
						if(isInsertionErrorTrue){%>
							<span class="help-block" style="color: red;">There is some error in insertion, Please enter again.</span>
						<%}%>
						
						<%
						if(didEmailIdNotMatch){%>
							<span class="help-block" style="color: red;">Email Id Didn't Match. Please SignUp First.</span>
						<%}%>
						
						<%
						if(doesUserExist){%>
							<span class="help-block" style="color: red;">User Already Exist. SignUp with new EmailId</span>
						<%}%>
						
						<div class="form-group">
						    <label for="txtSignUpFirstName">First Name</label>
						    <input type="text" class="form-control" id="txtSignUpFirstName" name="txtSignUpFirstName" placeholder="Enter first name" required/>
					    </div>
					    <div class="form-group">
					    	<label for="txtSignUpLastName">Last Name</label>
					    	<input type="text" class="form-control" id="txtSignUpLastName" name="txtSignUpLastName" placeholder="Enter last name" required/>
					    </div>
					    <div class="form-group">
					    	<label for="txtSignUpEmailId">Email Address</label>
					    	<input type="email" class="form-control" id="txtSignUpEmailId" name="txtSignUpEmailId" onblur="validateEmailIdById('txtSignUpEmailId');" placeholder="Enter email" required/>
					    </div>
					    <div class="form-group">
						    <label for="txtSignUpPassword">Password</label>
						    <input type="password" class="form-control" id="txtSignUpPassword" name="txtSignUpPassword" placeholder="Enter password" required/>
					    </div>
					    <button type="submit" class="btn btn-primary">Sign Up</button>
					</form>
				</div>
				<div class="col-md-6">
					<form action="LogInServlet" method="Post">
						<h3 class="heading">Log In</h3>
						<%
						if(isSignUpSuccessful){%>
							<span class="help-block" style="color: green;">SignUp Successful, You can LogIn now.</span>
						<%}%>
						<%
						if(isPasswordSent){%>
							<span class="help-block" style="color: green;">Password Sent to: <%= EmailIdForTxtLogIn %>, You can LogIn now.</span>
						<%}%>
						<div class="form-group">
					    	<label for="txtLogInEmailId">Email Address</label>
					    	<input type="email" class="form-control" id="txtLogInEmailId" name="txtLogInEmailId" onblur="checkIfPasswordRemembered();" value="<%if(EmailIdForTxtLogIn != null){%><%=EmailIdForTxtLogIn%><%}%>" placeholder="Enter email" required/>
					    </div>
					    <%
						if(didPasswordNotMatch){%>
							<span class="help-block" style="color: red;">Password Did't Match. If forgot password click <a id="forgetPassword" onclick="controlToMailService();">here</a> or Please Try Again</span>
						<%}%>
					    <div class="form-group">
						    <label for="txtLogInPassword">Password</label>
						    <input type="password" class="form-control" id="txtLogInPassword" name="txtLogInPassword" placeholder="Enter password" required/>
					    </div>
					    <div class="form-check">
						    <input type="checkbox" class="filled-in form-check-input" id="txtLogInRememberPassword" name="txtLogInRememberPassword" checked="checked">
    						<label class="form-check-label" for="txtLogInRememberPassword">Remember Password</label>
					  	</div>
					    <button type="submit" class="btn btn-primary">Log In</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>

</html>