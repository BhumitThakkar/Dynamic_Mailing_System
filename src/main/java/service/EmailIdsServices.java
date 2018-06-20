package service;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.EmailIdLists;
import model.EmailIds;
import model.User;

public class EmailIdsServices {

	public static long insertEmailIds(EmailIds emailId) {
		long flag=0;
		Session s = HibernateUtil.getConnection().openSession();
		Transaction tr = null;
		try {
			tr = s.beginTransaction();
			flag = (Long) s.save(emailId);
			tr.commit();
		}
		catch (Exception e) {
			if (tr!=null)
				tr.rollback();
			e.printStackTrace();
		}
		finally {
			s.close();
		}
			return flag;
	}
	
	public static Set<EmailIds> getToEmailIdList(String toEmailIdListName, User user) {
		Set<EmailIdLists> emailIdLists = user.getEmailIdLists();
		for(EmailIdLists emailIdList : emailIdLists) {
			if(toEmailIdListName.equals(emailIdList.getListName())) {
				return emailIdList.getEmailIds();
			}
			else {
				continue;
			}
		}
		return null;
	}
	
}
