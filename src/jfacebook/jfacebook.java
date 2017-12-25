/*
 * Made By : Hassan Nawaz
 * All Rights Reserved
 */
package jfacebook;
import connection.dbtest;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import connection.Controller;
import java.util.List;
import qk.qout;
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
       //qout.l(models.PostsModel.getPost(1,1).getUsers().getFirstName());
      //qout.l(models.PostsModel.getPosts(1, 1));
      //List l = models.PostsModel.getPosts(1, 1);
      // List l = models.FriendsModel.getFriends(1);
    //  for(int i=0; i<l.size(); i++) {
     //     qout.l(l.get(i).toString());
     // }
    gui.loginWindow loginWindow1 = new gui.loginWindow();
    loginWindow1.pack();
     
    loginWindow1.setVisible(true);
    
      
       // StandardServiceRegistryBuilder.destroy(Controller.getSessionFactory().getSessionFactoryOptions().getServiceRegistry());
    }
    
}

/*
0=wall
1=group
2=page
*/
