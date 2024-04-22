package User;

abstract class User {
    protected String name;
    protected int user_id;
    protected static int user_id_count = 0;

    public User(String _username) {
        this.name = _username;
        this.user_id = ++user_id_count;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return user_id;
    }

    public void setName(String new_username) {
        this.name = new_username;
    }
}
