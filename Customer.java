public class Customer extends User{
    double credit;
    // List<Order> orders;


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