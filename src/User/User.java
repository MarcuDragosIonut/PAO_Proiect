package User;

public abstract class User {
    protected String name;
    protected int user_id;
    protected static int user_id_count = 0;

    public User(String _username) {
        this.name = _username;
        this.user_id = ++user_id_count;
    }

    public User(int _id, String _username){
        this.user_id = _id;
        this.name = _username;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return user_id;
    }

    public static void setUser_id_count(int new_count){
        user_id_count = new_count;
    }

    public void setName(String new_username) {
        this.name = new_username;
    }
}
