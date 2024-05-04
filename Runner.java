import java.util.*;


public class Runner{
    public List<User> users = new ArrayList<User>();


    public String createUser(String name, String email, String password){
        //create a user. Default type is customer <- as per specified in the assigment 3 document.
        User newUser = new Customer();

        //find the current maximum id.
        int currMaxId = 0;
        
        for (User usr : users) {
            if (currMaxId < usr.getId()){
                currMaxId = usr.getId();
            }
        } 

        //check name, email, password null or not.
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
            return "Make sure to enter name, email and password.";
        }

        newUser.register(currMaxId + 1, name, email, password);
        this.users.add(newUser);

        return "User created successfully.";
    }

    public boolean loginUser(String email, String password) {
        for (User usr : users) {
            if (usr.getEmail().equals(email) && usr.getPassword().equals(password)) {
                return true;
            }
        }
        return false ;
    }
}