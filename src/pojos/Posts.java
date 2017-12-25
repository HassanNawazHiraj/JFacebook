package pojos;
// Generated Dec 25, 2017 12:32:00 PM by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * Posts generated by hbm2java
 */
public class Posts implements java.io.Serializable {

    private Integer id;
    private int userId;
    private String body;
    private Date postDate;
    private int postType;
    private pojos.Users users;

    public Posts() {
    }

    public Posts(int userId, String body, Date postDate, int postType) {
        this.userId = userId;
        this.body = body;
        this.postDate = postDate;
        this.postType = postType;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getPostDate() {
        return this.postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public int getPostType() {
        return this.postType;
    }

    public void setPostType(int postType) {
        this.postType = postType;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Posts{" + "id=" + id + ", userId=" + userId + ", body=" + body + ", postDate=" + postDate + ", postType=" + postType + ", users=" + users + '}';
    }

}
