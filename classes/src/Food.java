import  java.util.*;

public class Food{
    public int ID;
    public String name;
    double price;
    String imagePath;

    public Food(int id, String name, double price, String imagePath){
        this.ID = id;
        this.name = name;
        this.price =price;
        this.imagePath = imagePath;
    }
}