import  java.util.*;

public class Food{
    public int ID;
    public String name;
    double price;
    List<String> ingredients = new ArrayList<String>();
    String imagePath;

    public Food(int id, String name, double price, List<String> ingredients, String imagePath){
        this.ID = id;
        this.name = name;
        this.price =price;
        this.ingredients = ingredients;
        this.imagePath = imagePath;
    }
}