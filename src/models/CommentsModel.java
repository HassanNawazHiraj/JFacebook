package models;

import connection.Controller;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pojos.Comments;

/**
 *
 * @author hassan
 */
public class CommentsModel {

    public static List getComments(int post_id, int page_number) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        //  int id = 0;
        List list = null;
        try {
            tx = session.beginTransaction();
            //get post data
            Query q = session.createQuery("From Comments where post_id=:post_id order by post_date desc");
            q.setMaxResults(3);
            q.setFirstResult(page_number * 3);
            q.setParameter("post_id", post_id);
            // q.setParameter("offset", page_number*2);
            list = q.list();

            if (list.size() > 0) {
                // add user data
                for (int i = 0; i < list.size(); i++) {
                    //filling user object
                    Query qu = session.createQuery("From Users where id=:id");
                    qu.setParameter("id", ((pojos.Comments) list.get(i)).getUserId());
                    List list2 = qu.list();
                    pojos.Comments c = (pojos.Comments) list.get(i);
                    c.setUsers((pojos.Users) list2.get(0));

                    //filling likes user object
                    List<pojos.Users> luo = LikesModel.getLikes(4, ((pojos.Comments) list.get(i)).getId());
                    c.setLikes(luo);

                    list.set(i, c);
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

    public static boolean addComment(Comments comment) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            id = (int) session.save(comment);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("---" + e.getMessage());
            return false;
            // e.printStackTrace();
        } finally {
            session.close();
        }
        // System.out.println("saved  " + id);
        return true;
    }
    
      public static boolean deleteComment(int id) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        // int id = 0;
        try {
            tx = session.beginTransaction();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            //id = (int) session.save(user);
            pojos.Comments c;
            c = (pojos.Comments) session.get(pojos.Comments.class, id);
            if (!(c == null)) {
                session.delete(c);
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

}
