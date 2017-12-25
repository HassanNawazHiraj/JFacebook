/*
 * Made By : Hassan Nawaz
 * All Rights Reserved
 */
package connection;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Date;
import org.hibernate.HibernateException;
import pojos.Users;

/**
 *
 * @author hassan
 */
public class dbtest {

    public void get_user() {

        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        int id=0;
        try {
            tx = session.beginTransaction();
            
            Users user = new Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            id = (int) session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        System.out.println("saved  " + id);

    }

}
