/*
 * Made By : Hassan Nawaz
 * All Rights Reserved
 */
package models;

import connection.Controller;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
//import pojos.Users;

/**
 *
 * @author hassan
 */
public class PostsModel {

    public static pojos.Posts getPost(int id) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        //  int id = 0;
        List list = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("From Posts where id=:id");
            q.setParameter("id", id);
            list = q.list();
            if (list.size() > 0) {
                return ((pojos.Posts) list.get(0));
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

    public static boolean deletePost(int id) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        // int id = 0;
        try {
            tx = session.beginTransaction();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            //id = (int) session.save(user);
            pojos.Posts p;
            p = (pojos.Posts) session.get(pojos.Posts.class, id);
            if (!(p == null)) {
                session.delete(p);
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

    public static boolean addPost(pojos.Posts post) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            id = (int) session.save(post);
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
}
