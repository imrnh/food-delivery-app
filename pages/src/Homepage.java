import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Homepage {

    private int visiblePane = 1; //1 = home pane, 2= search pane, 3 = restaurant view pane, 4 = food view pane
    private java.util.List<JComponent> homepage_components = new ArrayList<>();


    public java.util.List<JComponent> getHomepage(){
         homepage_components = new ArrayList<>();
        User user = SessionManager.user;


        //Adding the top bar. It is global to all the pane in customer page.
        JPanel title_panel = new JPanel();
        title_panel.setBounds(0, 0, GUIConfig.WINDOW_WIDTH, 60);
        title_panel.setBackground(Color.white);
        java.util.List<JComponent> titleComponents = getTitleComponents(user);
        homepage_components.addAll(titleComponents);
        homepage_components.add(title_panel);

        switchPane();

        return homepage_components;
    }


    private void switchPane(){
        if (visiblePane == 1){
            homepage_components.addAll(homeViewPane());
        }
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
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                //open cart view pane's code here.
            }
        });

        //Open Orders view pane.
        viewOrders.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                //open orders view pane code here.
            }

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


    private java.util.List<JComponent> homeViewPane(){
        List<JComponent> homeViewPane = new ArrayList<>();

        //Search bar
        JPanel serachIconPanel = new JPanel(); //just to add white box below the search icon. JLabel doesn't have background.
        serachIconPanel.setBounds(GUIConfig.WINDOW_WIDTH/2-300, 100, 50, 55);
        serachIconPanel.setBackground(Color.white);
        serachIconPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout

        JLabel searchIcon = new JLabel();
        searchIcon.setIcon(new ImageIcon("icons/search.png"));
        searchIcon.setBounds(GUIConfig.WINDOW_WIDTH/2-290, 100, 50,55);

        JTextField searchField = new JTextField();
        searchField.setBounds(GUIConfig.WINDOW_WIDTH/2 - 250, 100, 500, 55);
        searchField.setBorder(null);

        JPanel searchButtonPanel = new JPanel(); //just to add white box below the search icon. JLabel doesn't have background.
        searchButtonPanel.setBounds(GUIConfig.WINDOW_WIDTH/2+250, 100, 50, 55);
        searchButtonPanel.setBackground(Color.white);
        searchButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout

        JLabel searchButton = new JLabel();
        searchButton.setIcon(new ImageIcon("icons/paper-plane_orange.png"));
        searchButton.setBounds(GUIConfig.WINDOW_WIDTH/2+270, 100, 50, 55);
        searchButton.setBackground(Color.white);


        searchButtonPanel.setBorder(new RoundedBorder(15));
        serachIconPanel.setBorder(new RoundedBorder(15));

        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("Clicked to search");
            }
        });

        homeViewPane.add(searchIcon);
        homeViewPane.add(serachIconPanel);
        homeViewPane.add(searchField);
        homeViewPane.add(searchButton);
        homeViewPane.add(searchButtonPanel);


        homeViewPane.addAll(HomepaneComponents.getFoodCard(new Food(1, "Speghatti", 20.5, null, "images/spg.jpg"), 200, 300));


        return homeViewPane;
    }



}
