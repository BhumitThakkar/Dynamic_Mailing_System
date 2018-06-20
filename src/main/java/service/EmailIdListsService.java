package service;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.EmailIdLists;

public class EmailIdListsService {
	public static long insertEmailIdList(EmailIdLists emailIdList) {		
		long flag=0;
		Session s = HibernateUtil.getConnection().openSession();
		Transaction tr = null;
		try {
			tr = s.beginTransaction();
			flag = (Long) s.save(emailIdList);
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
}
