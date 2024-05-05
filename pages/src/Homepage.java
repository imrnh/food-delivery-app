import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import javax.swing.*;

public class Homepage {
    public java.util.List<JComponent> getHomepage(){
        java.util.List<JComponent> homepage_components = new ArrayList<>();
        User user = SessionManager.user;

        JPanel title_panel = new JPanel();
        title_panel.setBounds(0, 0, GUIConfig.WINDOW_WIDTH, 60);
        title_panel.setBackground(Color.white);



        java.util.List<JComponent> titleComponents = getTitleComponents(user);

        homepage_components.addAll(titleComponents);
        homepage_components.add(title_panel);


        return homepage_components;
    }


    private java.util.List<JComponent> getTitleComponents(User user){

        java.util.List<JComponent> titleComponents = new ArrayList<>();

        String fullName = "";
        if (user !=null){
            fullName = user.getName();
        }

        double credit_balance = 12.00;

        //retrieve balance for the user.
        if (user instanceof Customer){
            credit_balance = ((Customer) user).credit;
        }

        String ordersBox = "icons/box.png";
        String cartBox = "icons/basket.png";

        JLabel fullNameLabel = new JLabel("Hi, " + fullName);
        JLabel balanceLabel = new JLabel("Balance: " + String.valueOf(credit_balance));



        JLabel viewCart = new JLabel();
        JLabel viewOrders = new JLabel();
        JButton changeToDriver = new JButton("Change To Drivers");

        viewCart.setIcon(new ImageIcon(cartBox));
        viewOrders.setIcon(new ImageIcon(ordersBox));

        //Open Cart View
        viewCart.addMouseListener(new MouseAdapter() {
            //open cart view pane's code here.
        });

        //Open Orders view pane.
        viewOrders.addMouseListener(new MouseAdapter() {
            //open orders view pane code here.
        });


        fullNameLabel.setFont(fullNameLabel.getFont().deriveFont(23f));
        fullNameLabel.setForeground(Color.black);

        balanceLabel.setFont(balanceLabel.getFont().deriveFont(15f));
        balanceLabel.setForeground(Color.orange);

        viewCart.setBackground(Color.white);
        viewOrders.setBackground(Color.white);
        changeToDriver.setBackground(Color.white);

        viewCart.setBorder(null);
        viewOrders.setBorder(null);
        changeToDriver.setBorder(null);

        fullNameLabel.setBounds(100, 0, 250, 50);
        balanceLabel.setBackground(Color.darkGray);
        balanceLabel.setBounds(GUIConfig.WINDOW_WIDTH-420, 10, 150, 30);

        viewCart.setBounds(GUIConfig.WINDOW_WIDTH - 270, 10, 40, 30);
        viewOrders.setBounds(GUIConfig.WINDOW_WIDTH - 210, 10, 40, 30);
        changeToDriver.setBounds(GUIConfig.WINDOW_WIDTH - 170, 10, 170, 30);


        titleComponents.add(fullNameLabel);
        titleComponents.add(viewCart);
        titleComponents.add(viewOrders);
        titleComponents.add(changeToDriver);
        titleComponents.add(balanceLabel);

        return titleComponents;
    }
}
