import java.util.*;


public class Runner{
    public List<User> users = new ArrayList<User>();
    public List<Restaurant> restaurants = new ArrayList<>();

    public void createRestaurants(){
        Restaurant restaurants1 = new Restaurant(1, "Elements - Global Dining at InterContinental Dhaka", "1 Minto Road, Dhaka City 1000 Bangladeshh");
        Restaurant restaurants2 = new Restaurant(1, "Domino's Pizza", "Dhanmondhi, Dhaka, Bangladesh");
        Restaurant restaurants3 = new Restaurant(1, "Aqua Deck at InterContinental Dhaka", "1 Minto Road, Dhaka City 1000 Bangladesh");
        Restaurant restaurants4 = new Restaurant(1, "Le Méridien Dhaka", "79/A Commercial Area, Airport Road Ninkunja 2, Dhaka City 1229 Bangladesh");
        Restaurant restaurants5 = new Restaurant(1, "Olea Turkish Restaurant at Le Méridien Dhaka", "Airport Road 79/A Commercial Area, Le Méridien Dhaka, Dhaka City 1229 Bangladesh");
        Restaurant restaurants6 = new Restaurant(1, "The Garden Kitchen at Sheraton Dhaka", "44 Kemal Ataturk Avenue Level 14, Sheraton, Dhaka City 1213 Bangladesh");
        Restaurant restaurants7 = new Restaurant(1, "Risotto by Sarina", "17 Plot #27 Road Banani C/A, Dhaka City 1213 Bangladesh");
        Restaurant restaurants8 = new Restaurant(1, "Summer fields by Sarina", "# 17 Plot #27 Road Hotel Sarina, Banani C/A, Dhaka City 1213 Bangladesh");
        Restaurant restaurants9 = new Restaurant(1, "The Amber Room at InterContinental Dhaka", "1 Minto Road InterContinental Dhaka, Dhaka City 1000 Bangladesh");
        Restaurant restaurants10 = new Restaurant(1, "Cafe Social at InterContinental Dhaka", "1 Minto Road InterContinental Dhaka, An IHG Hotel, Dhaka City 1000 Bangladesh");


        restaurants.add(restaurants1);
        restaurants.add(restaurants2);
        restaurants.add(restaurants3);
        restaurants.add(restaurants4);
        restaurants.add(restaurants5);
        restaurants.add(restaurants6);
        restaurants.add(restaurants7);
        restaurants.add(restaurants8);
        restaurants.add(restaurants9);
        restaurants.add(restaurants10);

    }

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