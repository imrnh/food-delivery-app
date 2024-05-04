import java.util.*;

public class Restaurant{
    private int id;
    public String name;

    List<Food> foods = new ArrayList<Food>();

    public String location;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public String getLocation() {
        return location;
    }


    public Restaurant(int id, String name, String location){
        this.id = id;
        this.name = name;
        this.location = location;
    }

    void addFood(Food food){
        this.foods.add(food);
    }

    void removeFood(int foodIdx){
        this.foods.remove(foodIdx);
    }

    public void changeLocation(String location) {
        this.location = location;
    }
}