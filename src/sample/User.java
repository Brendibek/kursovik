package sample;

/**
 * Created by ilya on 27.05.17.
 */
public class User {
    private int id = -1;
    private String email;
    private boolean admin = false;
    private String password;

    public User(int id, String email, boolean admin, String password){
        this.id = id;
        this.email = email;
        this.admin = admin;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean getAdmin(){
        return admin;
    }
}
