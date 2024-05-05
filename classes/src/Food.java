import  java.util.*;

public class Food{
    public int ID;
    public String name;
    double price;
    List<String> ingredients = new ArrayList<String>();

    public Food(int id, String name, double price, List<String> ingredients){
        this.ID = id;
        this.name = name;
        this.price =price;
        this.ingredients = ingredients;
    }
}