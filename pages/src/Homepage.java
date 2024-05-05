import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class Homepage {
    public java.util.List<JComponent> getHomepage(){
        java.util.List<JComponent> homepage_components = new ArrayList<>();
        User user = SessionManager.user;


        JLabel n = new JLabel("HelloNigga");
        n.setBounds(100, 406, 100, 50);

        homepage_components.add(n);

        homepage_components.add(getTitleBar(user));

        return homepage_components;
    }


    private JPanel getTitleBar(User user){
        JPanel titleBar = new JPanel();
        titleBar.setLayout(new GridLayout(1,7));

        String fullName = "";
        if (user !=null){
            fullName = user.getName();
        }

        JLabel fullNameLabel = new JLabel("Hi, " + fullName);

        double credit_balance = 0.00;

        //retrieve balance for the user.
        if (user instanceof Customer){
            credit_balance = ((Customer) user).credit;
        }

        JLabel balanceLabel = new JLabel(String.valueOf(credit_balance));

        JButton viewCart = new JButton("View cart");
        viewCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //open cart view pane.
            }
        });


        JButton viewOrders = new JButton("View Orders");
        viewOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //open orders view pane.
            }
        });

        JButton changeToDriver = new JButton("Change To Drivers Dashboard");

        titleBar.add(fullNameLabel);
        titleBar.add(viewCart);
        titleBar.add(viewOrders);
        titleBar.add(changeToDriver);
        titleBar.add(balanceLabel);

        titleBar.setBounds(30, 0, GUIConfig.WINDOW_WIDTH, 60);

        return titleBar;
    }
}
