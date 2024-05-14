import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Payment {
    public int paymentId;
    public int userId;
    public double amount;

    public Payment(int paymentId, int userId, double amount) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.amount = amount;
    }


    public void paymentProcessor() throws StripeException {
        Stripe.apiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc";

        Map<String, Object> params = new HashMap<>();
        params.put("payment_method_types", Arrays.asList("card"));
        params.put("line_items", Arrays.asList(
                Map.of("price_data", Map.of("currency", "usd", "product_data", Map.of("name", "Food"), "unit_amount", this.amount),
                        "quantity", 1)
        ));
        params.put("mode", "payment");

        Session session = Session.create(params);
    }

}
