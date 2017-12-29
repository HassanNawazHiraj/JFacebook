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

    public static pojos.Posts getPost(int id, int post_type) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        //  int id = 0;
        List list = null;
        try {
            tx = session.beginTransaction();
            //get post data
            Query q = session.createQuery("From Posts where id=:id and post_type=:post_type");
            q.setParameter("id", id);
            q.setParameter("post_type", post_type);
            list = q.list();

            if (list.size() > 0) {
                // add user data
                Query qu = session.createQuery("From Users where id=:id");
                qu.setParameter("id", ((pojos.Posts) list.get(0)).getUserId());
                List list2 = qu.list();
                pojos.Posts p = (pojos.Posts) list.get(0);
                p.setUsers((pojos.Users) list2.get(0));
                return (p);
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
    /**
     * 
     * @param user_id posted by
     * @param post_type 1=wall, 2=group, 3=page
     * @param page_number OFFSET page_number*2
     * @return 
     */
    public static List getPosts(int fetch_id, int post_type, int page_number) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        //  int id = 0;
        List list = null;
        try {
            tx = session.beginTransaction();
            //get post data
            Query q = session.createQuery("From Posts where fetch_id=:fetch_id and post_type=:post_type order by post_date desc");
            q.setMaxResults(2);
            q.setFirstResult(page_number*2);
            q.setParameter("fetch_id", fetch_id);
            q.setParameter("post_type", post_type);
           // q.setParameter("offset", page_number*2);
            list = q.list();

            if (list.size() > 0) {
                // add user data
                for (int i = 0; i < list.size(); i++) {
                    Query qu = session.createQuery("From Users where id=:id");
                    qu.setParameter("id", ((pojos.Posts) list.get(i)).getUserId());
                    List list2 = qu.list();
                    pojos.Posts p = (pojos.Posts) list.get(i);
                    p.setUsers((pojos.Users) list2.get(0));
                    list.set(i, p);
                }

                return (list);
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
            System.out.println("---"+e.getMessage());
            return false;
            // e.printStackTrace();
        } finally {
            session.close();
        }
        // System.out.println("saved  " + id);
        return true;
    }
}
