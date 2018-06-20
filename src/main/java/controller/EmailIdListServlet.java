package controller;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.EmailIdLists;
import model.EmailIds;
import model.User;
import service.EmailIdListsService;
import service.EmailIdsServices;

public class EmailIdListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("Current User");
		EmailIdLists emailIdList = null;
  		EmailIds emailId = null;
		Set<EmailIdLists> emailIdLists = user.getEmailIdLists();
		Set<EmailIds> emailIds = null;
		int emailIdCount = 1;
  		
		String listName = request.getParameter("txtListName").trim();
		if(emailIdLists != null && emailIdLists.size() > 0) {
	  		for(EmailIdLists item : emailIdLists) {
	  			if(listName.equals(item.getListName())){
	  				emailIdList = item;
	  				break;
  				}
  			}
  		}
  		if(emailIdList == null) {
  	  		emailIdList = new EmailIdLists();
  	  		emailIdList.setListName(listName);
  			emailIdList.setUser(user);
//  			emailIdList.setEmailIds(emailIds);
  			long successfullyInserted1 = EmailIdListsService.insertEmailIdList(emailIdList);
  			if(successfullyInserted1 <= 0) {
  				response.sendRedirect("emailIdList.jsp?InsertionErrorInListName=true");
  				return;
  			}
  		}
  		
  		
  		
  		OUTER: while(request.getParameter("txtEmailId"+emailIdCount) != null) {
  			String txtEmailId = request.getParameter("txtEmailId"+emailIdCount).trim();
  			emailIds = emailIdList.getEmailIds();
  			if(emailIds != null && emailIds.size() > 0) {
  	  			for(EmailIds item : emailIds) {
  	  				if(txtEmailId.equals(item.getEmailId())){
  	  					emailIdCount++;
  	  					continue OUTER;
  	  				}
  	  			}
  			}
  			emailId = new EmailIds();
  			emailId.setEmailId(txtEmailId);
  			emailId.setEmailIdLists(emailIdList);
//	  		emailIds.add(emailId);
  			long successfullyInsertedTemp = EmailIdsServices.insertEmailIds(emailId);
  			emailId = null;
  			if(successfullyInsertedTemp <= 0) {
  				response.sendRedirect("emailIdList.jsp?InsertionErrorInEmail=true&Number="+emailIdCount);
  				return;
  			}
	  		emailIdCount++;
		}
	response.sendRedirect("emailIdList.jsp?EmailIdAndListInsertionSuccessful=true");  		
  	}
}
