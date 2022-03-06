package info.schuwan.models;

public class User {
    private int id;
    private String username;
    private String password;
    private String roles;
    private int rolesid;
    private int creditScore;
    private String email;

    public User() {
    }

    public User(String username, String password, int rolesid, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rolesid = rolesid;
        this.email = email;
    }
    public User(int id, String username, String password, String roles, int rolesid, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.creditScore = creditScore;
        this.email = email;

    }

    public  int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRolesid() {
        return rolesid;
    }

    public void setRolesid(int rolesid) {
        this.rolesid = rolesid;
    }


    @Override
    public String toString(){ // to be able to display the content of the list of User objects
        return  getId() + "," + getUsername() + "," + getPassword() + "," + getRolesid()+ "," + getCreditScore()+ "," + getEmail();
    }
}
