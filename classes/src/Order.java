import  java.util.*;

public class Order {
    public int userID;
    public int driverID;
    public int orderStatus;
    public List<List<Integer>> orderedFoods; //(resturantId, foodId)
    public double orderCost;
    public int orderType; // 1. delivery, 2.pickup, 3.dine-in

    public Order(int uid, int did, int ostat, int ordcost, int otype){
        this.userID = uid;
        this.driverID = did;
        this.orderStatus = ostat;
        this.orderCost = ordcost;
        this.orderType = otype;
    }

    public void addFood(List<Integer> food){
        this.orderedFoods.add(food);
    }
}
