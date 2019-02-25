package m.project.test.User;

public class User {

    private static User instance = null;

    private String username;
    private int id;

    private User(){
        this.username = "";
        this.id = 0;
    }

    public static User getInstance(){
        if(instance==null)
            instance = new User();
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
