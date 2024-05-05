public abstract class User{
    private int userID;
    private String name;
    private String email;
    private String password;

    public int getUserType() {
        return userType;
    }

    private int userType; //1 -> customer, 2-> driver, 3->administrator.


    public void convertToCustomer(){
        this.userType = 1;
    }
    public void convertToDriver(){
        this.userType = 2;
    }

    public int getId(){
        return this.userID;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    void register(int UID, String name, String email, String password){
        this.userID = UID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = 1;
    }

    boolean login(String email, String password){
        return !this.email.equals(email) || !this.password.equals(password);
    }

    void deleteUser(String email, String password){

    }
}