import java.util.ArrayList;
import java.util.List;

public class Customer extends User{
    double credit;
     List<Order> orders = new ArrayList<>();

     public Customer(){

     }

     public Customer(int id, String name, String email, String password){
        this.userID = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = 1;
     }

    void addCredit(double newCredit){
        this.credit += newCredit;
    }

    void removeCredit(double newCredit){
        this.credit -= newCredit;
        
        //Assuming credit can't be less than zero.
        if (this.credit < 0){
            this.credit = 0;
        }
    }
}