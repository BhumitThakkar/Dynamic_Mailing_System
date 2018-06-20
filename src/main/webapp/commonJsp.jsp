<%@page import="java.util.Set"%>
<%@page import="model.EmailIdLists"%>
<%@page import="model.User"%>

<%
/* If not logged in:*/
User user = (User) session.getAttribute("Current User");
if(user == null){
	response.sendRedirect("welcome.jsp");
}

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