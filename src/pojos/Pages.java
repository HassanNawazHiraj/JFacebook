package pojos;
// Generated Jan 3, 2018 12:33:19 AM by Hibernate Tools 4.3.1

/**
 * Pages generated by hbm2java
 */
public class Pages implements java.io.Serializable {

    private Integer id;
    private int createrId;
    private String pageName;
    private String pageDetail;

    private Users createrUser;

    public String getPageString() {
        return pageName + " By: "+ createrUser.getFirstName();
    }
    public Users getCreaterUser() {
        return createrUser;
    }

    public void setCreaterUser(Users createrUser) {
        this.createrUser = createrUser;
    }

    public Pages() {
    }

    public Pages(int createrId, String pageName, String pageDetail) {
        this.createrId = createrId;
        this.pageName = pageName;
        this.pageDetail = pageDetail;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCreaterId() {
        return this.createrId;
    }

    public void setCreaterId(int createrId) {
        this.createrId = createrId;
    }

    public String getPageName() {
        return this.pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageDetail() {
        return this.pageDetail;
    }

    public void setPageDetail(String pageDetail) {
        this.pageDetail = pageDetail;
    }

}