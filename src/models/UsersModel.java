/*
 * Made By : Hassan Nawaz
 * All Rights Reserved
 */
package models;

import connection.Controller;
//import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojos.Users;

/**
 *
 * @author hassan
 */
public class UsersModel {

    /**
     *
     * @param user Object of Users pojos class
     * @return boolean true/false for success
     */
    public static boolean addUser(Users user) {

        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            id = (int) session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
            // e.printStackTrace();
        } finally {
            session.close();
        }
        // System.out.println("saved  " + id);
        return true;
    }

    /**
     *
     * @param username username of user
     * @param password password of user
     * @return boolean true/false
     */
    public static boolean verifyUser(String username, String password) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        //  int id = 0;
        List list = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("From Users where username=:username AND password=:password");
            q.setParameter("username", username);
            q.setParameter("password", password);
            list = q.list();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            // id = (int) session.save(u);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
            //e.printStackTrace();
        } finally {
            session.close();
        }
        if (list == null) {
            return false;
        } else {
            return list.size() > 0;
        }
        //System.out.println("saved  " + id);

    }

    /**
     *
     * @param id id column of users table
     * @return boolean true/false for success
     */
    public static boolean deleteUser(int id) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        // int id = 0;
        try {
            tx = session.beginTransaction();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            //id = (int) session.save(user);
            Users u;
            u = (Users) session.get(Users.class, id);
            if (!(u == null)) {
                session.delete(u);
            } else {
                return false;
            }
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
            //  e.printStackTrace();

        } finally {
            session.close();

        }
        return true;
    }
    
    public static pojos.Users getUser(int id) {
          Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        //  int id = 0;
        List list = null;
        try {
            tx = session.beginTransaction();
            
            Query q = session.createQuery("From Users where id=:id");
            q.setParameter("id", id);
            list = q.list();
            
            
            if(list.size() > 0) {
                return ((Users)list.get(0));
            } 
            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            // id = (int) session.save(u);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
          
            //e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

}
