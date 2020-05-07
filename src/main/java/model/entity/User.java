package model.entity;

public class User {
    private Integer id;
    private String username;
    private String password;
    public enum ROLE {
        USER, ADMIN, UNKNOWN
    }

    private ROLE role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = ROLE.USER;
    }

    public User() {
    }

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
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
}
