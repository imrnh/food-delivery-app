import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Homepage {

    private JFrame windowFrame;
    private int visiblePane = 1; //1 = home pane, 2= search pane, 3 = restaurant view pane, 4 = food view pane
    private java.util.List<JComponent> homepage_components = new ArrayList<>();

    List<Food> foods = new ArrayList<>();
    List<Restaurant> restaurants = new ArrayList<>();
    private List<List<JComponent>> foodsComponents = new ArrayList<>();
    private List<List<JComponent>> restaurantComponents = new ArrayList<>();

    private int perRowFoodCardLimit = 8;

    String searchFilteringText = "";

    public Homepage(JFrame frame){
        this.windowFrame = frame;
    }

    public java.util.List<JComponent> getHomepage(List<Food> foods, List<Restaurant> restaurants){
        homepage_components = new ArrayList<>();
        User user = SessionManager.user;

        this.foods = foods;
        this.restaurants = restaurants;

        // Iterate over all the foods
        for (int i = 0; i < this.foods.size(); i++) {
            List<JComponent> retrievedFoodComp = HomepaneComponents.getFoodCard(foods.get(i), this.foodCardXPointCalc(i), this.foodCardYPointCalc(i));
            foodsComponents.add(retrievedFoodComp);
        }

        // Iterate over all the foods
        for (int i = 0; i < this.restaurants.size(); i++) {
            List<JComponent> retrievedRest = HomepaneComponents.getRestaurantCard(restaurants.get(i), this.restaurantCardXPointCalc(i), this.restaurantCardYPointCalc(i));
            restaurantComponents.add(retrievedRest);
        }

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


        homeViewPane.add(searchIcon);
        homeViewPane.add(serachIconPanel);
        homeViewPane.add(searchField);
        homeViewPane.add(searchButton);
        homeViewPane.add(searchButtonPanel);


        /*
        * Foods View
        * */


        JLabel foodTitleLabel = new JLabel("Food");
        foodTitleLabel.setFont(foodTitleLabel.getFont().deriveFont(20f));
        foodTitleLabel.setForeground(Color.black);
        foodTitleLabel.setBounds(150, 230, 200, 40);
        homeViewPane.add(foodTitleLabel);
        addFoodToHomepane(homeViewPane);


        /*
        * Restaurant view
        * */
        JLabel restaurantTitle = new JLabel("Restaurant");
        restaurantTitle.setFont(restaurantTitle.getFont().deriveFont(20f));
        restaurantTitle.setForeground(Color.black);
        restaurantTitle.setBounds(150, 550, 200, 40);
        homeViewPane.add(restaurantTitle);
        addFoodToHomepane(homeViewPane);

        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                searchFilteringText = searchField.getText();

                removeAllFoodFromHomepane();
                addFoodToHomepane(homeViewPane);

                windowFrame.revalidate();
                windowFrame.repaint();
            }
        });

        return homeViewPane;
    }

    private void removeAllFoodFromHomepane(){
        for(List<JComponent> foodComp : foodsComponents){
            for(JComponent foodC : foodComp){
                windowFrame.remove(foodC);
            }
        }

        for(List<JComponent> restCamp : restaurantComponents){
            for(JComponent restC : restCamp){
                windowFrame.remove(restC);
            }
        }
    }
    private void addFoodToHomepane(List<JComponent> homeViewPane) {
        if (searchFilteringText.isEmpty()) {
            // If no search text, add all food components
            for (List<JComponent> foodComp : foodsComponents) {
                for (JComponent foodC : foodComp) {
                    windowFrame.add(foodC);
                }
            }

            for (List<JComponent> restComp : restaurantComponents) {
                for (JComponent restC : restComp) {
                    windowFrame.add(restC);
                }
            }

        } else {
            // Otherwise, add only the food components that match the search text
            for (int i = 0; i < foods.size(); i++) {
                if (foods.get(i).name.contains(searchFilteringText)) {
                    for (JComponent foodC : foodsComponents.get(i)) {
                        windowFrame.add(foodC);
                    }
                }
            }

            for (int i = 0; i < restaurants.size(); i++) {
                if (restaurants.get(i).name.contains(searchFilteringText)) {
                    for (JComponent resC : restaurantComponents.get(i)) {
                        windowFrame.add(resC);
                    }
                }
            }
        }
    }

    private int foodCardXPointCalc(int i){
        if (i > perRowFoodCardLimit-1){
            i = i-perRowFoodCardLimit;
        }
        return  150 * (i + 1) + (i * 20);
    }

    private int restaurantCardXPointCalc(int i){
        return  150 +  (450 * i) + (i * 20);
    }

    private int foodCardYPointCalc(int i){
        if (i > perRowFoodCardLimit-1){
            return 280 *  (1 + (i / perRowFoodCardLimit)) - (50 * (i/perRowFoodCardLimit));
        }
        else {
            return 280;
        }
    }

    private int restaurantCardYPointCalc(int i){
        return 600;
    }
}
