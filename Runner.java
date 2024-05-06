import java.util.*;


public class Runner{
    public List<User> users = new ArrayList<User>();
    public List<Restaurant> restaurants = new ArrayList<>();
    public List<Food> foods = new ArrayList<>();


    public void createFoods(){
        foods.add(new Food(1, "Food 1", 9.59, "images/f1.png"));
        foods.add(new Food(2, "Food 2", 13.00, "images/f2.png"));
        foods.add(new Food(3, "Food 3", 12.55, "images/f3.png"));
        foods.add(new Food(4, "Food 4", 11.09, "images/f4.png"));
        foods.add(new Food(5, "Food 5", 12.33, "images/f5.png"));
        foods.add(new Food(6, "Food 6", 5.99, "images/f6.png"));
        foods.add(new Food(7, "Food 7", 3.99, "images/f7.png"));
        foods.add(new Food(8, "Food 8", 50.00, "images/f8.png"));
    }


    public void createRestaurants(){

        if (foods.isEmpty()){
            createFoods();
        }

        Restaurant restaurants1 = new Restaurant(1, "Global Dining at InterContinental Dhaka", "1 Minto Road, Dhaka City 1000 Bangladeshh");
        Restaurant restaurants2 = new Restaurant(4, "Le Méridien Dhaka", "79/A Commercial Area, Airport Road Ninkunja 2, Dhaka City 1229 Bangladesh");
        Restaurant restaurants3 = new Restaurant(3, "Aqua Deck at InterContinental Dhaka", "1 Minto Road, Dhaka City 1000 Bangladesh");

        restaurants1.addFood(foods.getFirst());
        restaurants1.addFood(foods.get(1));
        restaurants1.addFood(foods.get(2));

        restaurants2.addFood(foods.get(3));
        restaurants2.addFood(foods.get(4));
        restaurants2.addFood(foods.get(5));

        restaurants3.addFood(foods.get(6));
        restaurants3.addFood(foods.get(7));

        restaurants.add(restaurants1);
        restaurants.add(restaurants2);
        restaurants.add(restaurants3);
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

        SessionManager.user = newUser;

        return "User created successfully.";
    }

    public User loginUser(String email, String password) {
        for (User usr : users) {
            if (usr.getEmail().equals(email) && usr.getPassword().equals(password)) {
                return usr;
            }
        }
        return null;
    }
}