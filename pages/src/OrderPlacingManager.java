import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderPlacingManager {
    public static Order placeOrder(String deliveryLoc){
        List<Food> foods = SessionManager.cart;

        //generate OTP.
        String otp = generateOTP();

        int orderNumber = Filereader.readFileLine("orders.txt").size();

        //randomly pick a driver. Read user.txt and file all the drivers. Then iteratively check the the user is driver, then if the driver id is in the orders.txt with order being held?
        List<String> userInformations = Filereader.readFileLine("user.txt");

        List<String> driverIDs = new ArrayList<>();
        for(String uInfo: userInformations){
            String ul[] = uInfo.split("--");

            if(ul[4].equals("driver")){
                driverIDs.add(ul[2]);
            }
        }

        //check if driver id belong to any driver currently delivering.
        //Orders.txt structure->  order_id--user_id--driver_id--order_Status--otp--price--deliveryLoc--foods comma seperated: Food 1, Food 2, etc.
        List<String> currOrdersList = Filereader.readFileLine("orders.txt");
        for(String orderLine : currOrdersList){
            String orderInfo[] = orderLine.split("--");
            driverIDs.remove(driverIDs.indexOf(orderInfo[1]));
        }

        double price = 0;
        String foodNames = "";
        for(Food food: foods){
            price += food.price;
            foodNames += food.name  + ", ";
        }

        int orderStatus = 1; //active.
        Order order = new Order(orderNumber, SessionManager.user.getId() ,Integer.parseInt(driverIDs.getFirst()), 1, price, 1, otp);

        //write file.
        Filereader.fileWrite("orders.txt", String.valueOf(orderNumber) + "--" + SessionManager.user.getId() + "--" + driverIDs.getFirst() + "--" +  String.valueOf(orderStatus) + "--" + otp + "--"  + String.valueOf(price) + "--" + deliveryLoc + "--" + foodNames );

        return order;
    }

    public static boolean paymentCollection(){

        return false;
    }

    public static String generateOTP() {
        // Define characters to be used in OTP
        int OTP_LENGTH = 6;
        String numbers = "0123456789";
        StringBuilder sb = new StringBuilder(OTP_LENGTH);
        Random random = new Random();

        // Generate OTP
        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = random.nextInt(numbers.length());
            sb.append(numbers.charAt(index));
        }

        return sb.toString();
    }
}
