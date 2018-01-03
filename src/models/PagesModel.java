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
public class PagesModel {

    public static List getPages() {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        //  int id = 0;
        List list = null;
        try {
            tx = session.beginTransaction();
            //get post data
            Query q = session.createQuery("From Pages");
            //q.setMaxResults(2);
            //q.setFirstResult(page_number * 2);
            //q.setParameter("fetch_id", fetch_id);
            //q.setParameter("post_type", post_type);
            // q.setParameter("offset", page_number*2);
            list = q.list();

            if (list.size() > 0) {
                // add user data
                for (int i = 0; i < list.size(); i++) {
                    //filling user object
                    Query qu = session.createQuery("From Users where id=:id");
                    qu.setParameter("id", ((pojos.Pages) list.get(i)).getCreaterId());
                    List list2 = qu.list();
                    pojos.Pages p = (pojos.Pages) list.get(i);
                    p.setCreaterUser((pojos.Users) list2.get(0));

                    //filling likes user object
                    //List<pojos.Users> luo = LikesModel.getLikes(post_type, ((pojos.Posts) list.get(i)).getId()  );
                    //p.setLikes(luo);
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

    public static boolean deletePage(int id) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        // int id = 0;
        try {
            tx = session.beginTransaction();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            //id = (int) session.save(user);
            pojos.Pages p;
            p = (pojos.Pages) session.get(pojos.Pages.class, id);
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

    public static boolean createPage(pojos.Pages page) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            id = (int) session.save(page);
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
