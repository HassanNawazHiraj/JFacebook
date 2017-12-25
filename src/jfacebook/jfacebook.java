/*
 * Made By : Hassan Nawaz
 * All Rights Reserved
 */
package jfacebook;
import connection.dbtest;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import connection.Controller;
//import models.UsersModel;
/**
 *
 * @author hassan
 */
public class jfacebook {

    public static void main(String[] args) {
        dbtest test = new dbtest();
        //test.get_user();
        //UsersModel hassan = new UsersModel();
        //System.out.println(UsersModel.verifyUser("hassan", "1213"));
       // System.out.println(UsersModel.getUsername(1));
       //models.PostsModel.addPost(new pojos.Posts(0, "test", new java.util.Date(0, 1, 1)));
        StandardServiceRegistryBuilder.destroy(Controller.getSessionFactory().getSessionFactoryOptions().getServiceRegistry());
    }
    
}
