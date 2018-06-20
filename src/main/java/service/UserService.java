package service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.User;


public class UserService {
	
	public static long insertUser(User newUser) {		
		long flag=0;
		Session s = HibernateUtil.getConnection().openSession();
		Transaction tr = null;
		try {
			tr = s.beginTransaction();
			flag = (Long) s.save(newUser);
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
	
	public static User selectUserByEmailId(String emailId){
		User user = null;
		Session session = HibernateUtil.getConnection().openSession();
		Transaction tr = null;
		try {
			tr = session.beginTransaction();
			user = (User) session.createQuery("FROM User where emailId ='"+emailId+"'").uniqueResult();		//Create Criteria instead of create Query is easy
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return user;
	}

	public static List<User> selectAllUser() {
		List<User> userList = null;
		Session s = HibernateUtil.getConnection().openSession();
		Transaction tr = null;
		try {
			tr = s.beginTransaction();
			userList = (ArrayList<User>) s.createQuery("from User").list();
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			s.close();
		}
		return userList;
	}
	
}
