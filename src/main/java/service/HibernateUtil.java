package service;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	public static SessionFactory getConnection() {
		return new Configuration().configure().buildSessionFactory();
	}
	
/*Services*/
/*	public static AddProductQuantityService getAddProductQuantityService(){
		return new AddProductQuantityService();
	}
*/
}