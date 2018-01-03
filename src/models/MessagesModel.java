package models;

import connection.Controller;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author hassan
 */
public class MessagesModel {

    public static List getMessages(int id) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        //  int id = 0;
        List list = null;
        try {
            tx = session.beginTransaction();
            //get post data
            Query q = session.createQuery("From Messages where to_id=:id");
         
            q.setParameter("id", id);
            // q.setParameter("offset", page_number*2);
            list = q.list();

            if (list.size() > 0) {
                // add user data
                for (int i = 0; i < list.size(); i++) {
                    //filling user object
                    Query qu = session.createQuery("From Users where id=:id");
                    qu.setParameter("id", ((pojos.Messages) list.get(i)).getFromId());
                    List list2 = qu.list();
                    pojos.Messages m = (pojos.Messages) list.get(i);
                    m.setFromUser((pojos.Users) list2.get(0));

//                    //filling likes user object
//                    List<pojos.Users> luo = LikesModel.getLikes(post_type, ((pojos.Posts) list.get(i)).getId());
//                    p.setLikes(luo);

                    list.set(i, m);
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

    public static boolean deleteMessage(int id) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        // int id = 0;
        try {
            tx = session.beginTransaction();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            //id = (int) session.save(user);
            pojos.Messages m;
            m = (pojos.Messages) session.get(pojos.Messages.class, id);
            if (!(m == null)) {
                session.delete(m);
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
    
     public static boolean sendMessage(pojos.Messages message) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            id = (int) session.save(message);
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

}
