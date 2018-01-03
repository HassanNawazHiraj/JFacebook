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

/**
 *
 * @author hassan
 */
public class FriendsModel {

    public static List getFriends(int user_id) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        //  int id = 0;
        List list = null;
        try {
            tx = session.beginTransaction();
            //get post data
            Query q = session.createQuery("From Friends where user_id=:user_id");
            q.setParameter("user_id", user_id);
            list = q.list();

            if (list.size() > 0) {
                // add user data
                for (int i = 0; i < list.size(); i++) {
                    Query qu = session.createQuery("From Users where id=:id");
                    qu.setParameter("id", ((pojos.Friends) list.get(i)).getFriendId());
                    List list2 = qu.list();
                    pojos.Friends f = (pojos.Friends) list.get(i);
                    //p.setUsers((pojos.Users) list2.get(0));
                    f.setFriendData((pojos.Users) list2.get(0));
                    list.set(i, f);
                }
                tx.commit();
                session.close();
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

    public static boolean sendFriendRequest(int user_id, int send_to_user_id) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            pojos.Friendrequests f = new pojos.Friendrequests(user_id, send_to_user_id);
            id = (int) session.save(f);
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

    public static boolean friendExists(int user_id, int send_to_user_id) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        //  int id = 0;
        List list = null;
        try {
            tx = session.beginTransaction();
            //get post data
            Query q = session.createQuery("From Friends where user_id=:user_id and friend_id=:friend_id");
            q.setParameter("user_id", user_id);
            q.setParameter("friend_id", send_to_user_id);
            list = q.list();

            if (list.size() > 0) {
                // add user data
                tx.commit();
                session.close();
                return true;
            } else {
                tx.commit();
                session.close();
                return false;
            }

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            // id = (int) session.save(u);
           // tx.commit();
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
    
    public static boolean friendRequestExists(int user_id, int send_to_user_id) {
        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        //  int id = 0;
        List list = null;
        try {
            tx = session.beginTransaction();
            //get post data
            Query q = session.createQuery("From Friendrequests where user_id=:user_id and friend_id=:friend_id");
            q.setParameter("user_id", user_id);
            q.setParameter("friend_id", send_to_user_id);
            list = q.list();

            if (list.size() > 0) {
                // add user data
                tx.commit();
                session.close();
                return true;
            } else {
                tx.commit();
                session.close();
                return false;
            }

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            // id = (int) session.save(u);
           // tx.commit();
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
    
    public static boolean addFriend(int user_id, int send_to_user_id) {
        if(friendExists(user_id, send_to_user_id)) {
            return false;
        }
          Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        int id = 0;
        try {
            tx = session.beginTransaction();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            pojos.Friends f = new pojos.Friends(user_id, send_to_user_id);
            pojos.Friends f2 = new pojos.Friends(send_to_user_id, user_id);
            session.save(f2);
            id = (int) session.save(f);
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
    
    public static boolean rejectFriendRequest(int user_id, int send_to_user_id) {
         Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        // int id = 0;
        List list = null;
        try {
            tx = session.beginTransaction();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            //id = (int) session.save(user);
           Query q = session.createQuery("From Friendrequests where user_id=:user_id and friend_id=:friend_id");
            q.setParameter("user_id", user_id);
            q.setParameter("friend_id", send_to_user_id);
            list = q.list();

            if (list.size() > 0) {
                // add user data
                session.delete((pojos.Friendrequests) list.get(0));
                tx.commit();
                session.close();
                return true;
            } else {
                tx.commit();
                session.close();
                return false;
            }
            
            
          //  tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
            //  e.printStackTrace();

        } finally {
            session.close();

        }
     //   return true;
    }
    public static boolean removeFriend(int user_id, int send_to_user_id) {
         Session session = Controller.getSessionFactory().openSession();
        Transaction tx = null;
        // int id = 0;
        List list = null;
        try {
            tx = session.beginTransaction();

            //  pojos.Users user = new pojos.Users("Ali", "Nawaz", "alinawazsolid", "128", "alinawazsolid@gmail.com", new Date(1980-1900, 4, 3), new Date(1960-1900, 2, 3, 12, 0, 0));
            //id = (int) session.save(user);
           Query q = session.createQuery("From Friends where user_id=:user_id and friend_id=:friend_id");
            q.setParameter("user_id", user_id);
            q.setParameter("friend_id", send_to_user_id);
            list = q.list();

            if (list.size() > 0) {
                // add user data
                session.delete((pojos.Friends) list.get(0));
                tx.commit();
                session.close();
                return true;
            } else {
                tx.commit();
                session.close();
                return false;
            }
            
            
          //  tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            return false;
            //  e.printStackTrace();

        } finally {
            session.close();

        }
     //   return true;
    }
}
