import java.util.*;


public class Runner{
    public List<User> users = new ArrayList<User>();


    public String createUser(){
        //create an user. Default type is customer <- as per specified in the assigment 3 document.
        User newUser = new Customer();

        String name = "Cus1";
        String email = "cus1@vituser.com";
        String password = "cus1pass";

        //find the current maximum id.
        int currMaxId = 0;
        
        for (User usr : users) {
            if (currMaxId < usr.getId()){
                currMaxId = usr.getId();
            }
        } 

        //check name, email, password null or not.
        if (name.equals("") || email.equals("") || password.equals("")){
            return "Make sure to enter name, email and password.";
        }

        newUser.register(currMaxId + 1, name, email, password);
        this.users.add(newUser);

        return "User created successfully.";
    }
}