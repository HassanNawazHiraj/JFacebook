package models;

import connection.Controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author hassan
 */
public class LikesModel {

    public static List<pojos.Users> getLikes(int post_type, int fetch_id) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        //  int id = 0;
        List<Object[]> list = null;
        List<pojos.Users> returnList = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            //get post data
            //Query q = session.createQuery("From Likes as l LEFT JOIN Users as u where u.id = l.user_id and l.fetch_id=:fetch_id and l.post_type=:post_type");
            Query q = session.createQuery("SELECT u.id, u.firstName, u.lastName, u.username, u.password, u.email,"
                    + "u.dateOfBirth, u.lastOnline from Users u,Likes l WHERE u.id = l.userId "
                    + "AND l.fetchId=:fetch_id and l.postType=:post_type");
            //q.setMaxResults(2);
            //q.setFirstResult(page_number*2);
            q.setParameter("fetch_id", fetch_id);
            q.setParameter("post_type", post_type);
            // q.setParameter("offset", page_number*2);
            list = q.list();

            if (list.size() > 0) {
                // add user data
                ///    for (int i = 0; i < list.size(); i++) {
                //       Query qu = session.createQuery("From Users where id=:id");
                //      qu.setParameter("id", ((pojos.Posts) list.get(i)).getUserId());
                //       List list2 = qu.list();
                //       pojos.Posts p = (pojos.Posts) list.get(i);
                //       p.setUsers((pojos.Users) list2.get(0));
                //       list.set(i, p);
                //   }
                for (Object[] o : list) {
                    returnList.add(new pojos.Users(Integer.parseInt(o[0].toString()), o[1].toString(), o[2].toString(), o[3].toString(), o[4].toString(), o[5].toString(), new Date(), new Date()));
                }
                return returnList;
                // return (list);
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

    public static boolean addLike(pojos.Likes like) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            id = (int) session.save(like);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            //  System.out.println("---" + e.getMessage());
            return false;
            // e.printStackTrace();
        } finally {
            session.close();
        }
        // System.out.println("saved  " + id);
        return true;
    }

    public static boolean removeLike(int id) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        // int id = 0;
        try {
            tx = session.beginTransaction();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            //id = (int) session.save(user);
            pojos.Likes l;
            l = (pojos.Likes) session.get(pojos.Likes.class, id);
            if (!(l == null)) {
                session.delete(l);
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

    public static boolean removeLike(pojos.Likes l) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        // int id = 0;
        try {
            tx = session.beginTransaction();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            //id = (int) session.save(user);
            Query q = session.createQuery("from Likes where user_id=:user_id and post_type=:post_type and fetch_id=:fetch_id");
            q.setParameter("user_id", l.getUserId());
            q.setParameter("post_type", l.getPostType());
            q.setParameter("fetch_id", l.getFetchId());

            if (removeLike(((pojos.Likes) q.list().get(0)).getId())) {
                tx.commit();
                return true;
            } else {
                tx.commit();
                return false;
            }

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
            //  e.printStackTrace();

        } finally {
            session.close();

        }
      //  return true;
    }

    public static boolean hasUserLikedPost(int user_id, int fetch_id, int post_type) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        //  int id = 0;
        List list = null;
        try {
            tx = session.beginTransaction();
            //get post data
            Query q = session.createQuery("From Likes where user_id=:user_id and post_type=:post_type and fetch_id=:fetch_id");
            q.setParameter("user_id", user_id);
            q.setParameter("post_type", post_type);
            q.setParameter("fetch_id", fetch_id);

            if (q.list().isEmpty()) {
                tx.commit();
                return false;
            } else {
                tx.commit();
                return true;
            }

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            // id = (int) session.save(u);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }

            //e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }
}
