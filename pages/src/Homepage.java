import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class Homepage {
    private boolean cartPanelVisible = false;

    private JFrame windowFrame;
    private final int titlePanelHeight = 60;
    private int perRowFoodCardLimit = 8;
    String searchFilteringText = "";

    List<Food> foods = new ArrayList<>();
    List<Restaurant> restaurants = new ArrayList<>();

    private JPanel cartPanel;
    private List<JComponent> homeViewPane = new ArrayList<>();
    private java.util.List<JComponent> homepage_components = new ArrayList<>();
    private List<List<JComponent>> foodsComponents = new ArrayList<>();
    private List<List<JComponent>> restaurantComponents = new ArrayList<>();

    public Homepage(JFrame frame){
        this.windowFrame = frame;
    }

    public java.util.List<JComponent> getHomepage(List<Food> foods, List<Restaurant> restaurants){
        homepage_components = new ArrayList<>();
        User user = SessionManager.user;

        this.foods = foods;
        this.restaurants = restaurants;
//
//        // Iterate over all the foods
//        for (int i = 0; i < this.foods.size(); i++) {
//            List<JComponent> retrievedFoodComp = HomepaneComponents.getFoodCard(foods.get(i), this.foodCardXPointCalc(i), this.foodCardYPointCalc(i));
//            foodsComponents.add(retrievedFoodComp);
//        }

        // Iterate over all the restaurants
        for (int i = 0; i < this.restaurants.size(); i++) {
            List<JComponent> retrievedRest = HomepaneComponents.getRestaurantCard(restaurants.get(i), 150, this.restaurantCardYPointCalc(i));
            restaurantComponents.add(retrievedRest);
        }

        //Adding the top bar. It is global to all the pane in customer page.
        JPanel title_panel = new JPanel();
        title_panel.setBounds(0, 0, GUIConfig.WINDOW_WIDTH, titlePanelHeight);
        title_panel.setBackground(Color.white);
        java.util.List<JComponent> titleComponents = getTitleComponents(user);
        homepage_components.addAll(titleComponents);
        homepage_components.add(title_panel);

        homepage_components.addAll(homeViewPane());
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
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(cartPanelVisible){
                    cartPanelVisible = false;
                    windowFrame.remove(cartPanel);
                    windowFrame.revalidate();
                    windowFrame.repaint();
                    return;
                }
                cartPanelVisible = true;


                // open cart view pane's code here.
                cartPanel = new JPanel(new BorderLayout());
                cartPanel.setBounds(100, titlePanelHeight + 30, GUIConfig.WINDOW_WIDTH - 200, GUIConfig.WINDOW_HEIGHT - titlePanelHeight - 30);

                JLabel cartCloseButton = new JLabel("Back to home");
                cartCloseButton.setIcon(new ImageIcon("icons/left-chevron.png"));
                cartPanel.add(cartCloseButton, BorderLayout.NORTH); // Add close button to the north


                JLabel cartLabel = new JLabel("Cart");
                cartLabel.setFont(cartLabel.getFont().deriveFont(23f));
                cartPanel.add(cartLabel, BorderLayout.NORTH); // Add cart label to the north

                JTable cartTable = new JTable();
                DefaultTableModel tableModel = new DefaultTableModel();


                // Create the table columns
                tableModel.addColumn("Food Name");
                tableModel.addColumn("Price");
                tableModel.addColumn("");

                cartTable.setFont(cartLabel.getFont().deriveFont(15f));

                JButton removeButton = new JButton("Remove");
                removeButton.setOpaque(false);

                for (Food orderedFood : SessionManager.cart) {
                    Object[] rowData = {
                            orderedFood.name,
                            orderedFood.price,
                            "Remove"
                    };
                    tableModel.addRow(rowData);
                }

                cartTable.setModel(tableModel);

                DefaultTableCellRenderer buttonRenderer = new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        if (value instanceof String && value.equals("Remove")) {
                            return removeButton;
                        }
                        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    }
                };
                cartTable.getColumnModel().getColumn(2).setCellRenderer(buttonRenderer);

                // Add an action listener to the "Remove" button
                removeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = cartTable.getSelectedRow();
                        if (selectedRow >= 0) {
                            Food foodToRemove = SessionManager.cart.get(selectedRow);
                            SessionManager.cart.remove(foodToRemove);
                            tableModel.removeRow(selectedRow);
                            JOptionPane.showMessageDialog(removeButton, "Food " + foodToRemove.name + " has been removed from order." );
                        }
                    }
                });


                double foodPrice = 0;

                for(Food food : SessionManager.cart){
                    foodPrice += food.price;
                }

                JLabel pricelabel = new JLabel("Total cart value: " + String.valueOf(foodPrice));
                JLabel emptyLabel1 = new JLabel("     ");
                JLabel locLabel = new JLabel("Select delivery location");
                JTextField selectLocation = new JTextField();

                JLabel paymentLabel = new JLabel("Payment Information");
                paymentLabel.setFont(paymentLabel.getFont().deriveFont(20f));

                JLabel cardNumberLabel = new JLabel("Card number");
                JTextField cardNumber = new JTextField();

                JLabel cvcLabel = new JLabel("CVC");
                JTextField cvcNumber = new JTextField();

                JLabel cardExpireLabel = new JLabel("Card expiration date");
                JTextField cardExpireTime = new JTextField();


                JButton orderBtn = new JButton("Pay with Stripe");


                double finalFoodPrice = foodPrice;
                orderBtn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);

                        Order order = OrderPlacingManager.placeOrder(selectLocation.getText(), finalFoodPrice, cardNumber.getText(), cvcNumber.getText(), cardExpireTime.getText());

                        if(order != null){
                            JOptionPane.showMessageDialog(orderBtn, "Order Placed with OPT: " + order.otp + ". Please remember the otp.");
                            SessionManager.cart = new ArrayList<>(); //clear the previously added items.
                        }
                        else{
                            JOptionPane.showMessageDialog(orderBtn, "Error Placing order. Please try again.");
                        }
                    }
                });





                JPanel rightPanel = new JPanel(new GridLayout(15, 1, 10, 10)); // Panel with GridLayout for location and order button
                rightPanel.add(pricelabel);
                rightPanel.add(emptyLabel1);
                rightPanel.add(locLabel);
                rightPanel.add(selectLocation);
                rightPanel.add(paymentLabel);
                rightPanel.add(cardNumberLabel);
                rightPanel.add(cardNumber);
                rightPanel.add(cvcLabel);
                rightPanel.add(cvcNumber);
                rightPanel.add(cardExpireLabel);
                rightPanel.add(cardExpireTime);
                rightPanel.add(orderBtn);


                JPanel rightPanelWrapper = new JPanel(new GridLayout(1, 15, 100, 100));


                JPanel emptyPanel = new JPanel();
                rightPanelWrapper.add(emptyPanel);
                rightPanelWrapper.add(rightPanel);

                JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
                JScrollPane panelWithCartPanel = new JScrollPane(cartTable);

                mainPanel.add(panelWithCartPanel, BorderLayout.CENTER);
                mainPanel.add(rightPanelWrapper, BorderLayout.CENTER);

                cartPanel.add(mainPanel, BorderLayout.CENTER);

                windowFrame.add(cartPanel, 0);
                windowFrame.revalidate();
                windowFrame.repaint();

                // remove all foods and restaurants and search bar.
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


        changeToDriver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                assert user != null;
                if(user.convertToDriver()){
                    windowFrame.dispose();
                }
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
        homeViewPane = new ArrayList<>();

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


//        /*
//        * Foods View
//        * */
//
//
//        JLabel foodTitleLabel = new JLabel("Food");
//        foodTitleLabel.setFont(foodTitleLabel.getFont().deriveFont(20f));
//        foodTitleLabel.setForeground(Color.black);
//        foodTitleLabel.setBounds(150, 230, 200, 40);
//        homeViewPane.add(foodTitleLabel);
//        addFoodToHomepane(homeViewPane);


        /*
        * Restaurant view
        * */
//        JLabel restaurantTitle = new JLabel("Restaurant");
//        restaurantTitle.setFont(restaurantTitle.getFont().deriveFont(20f));
//        restaurantTitle.setForeground(Color.black);
//        restaurantTitle.setBounds(150, 550, 200, 40);
//        homeViewPane.add(restaurantTitle);
        addFoodToHomepane(homeViewPane);

        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                searchFilteringText = searchField.getText();

                removeAllFoodFromHomepane();
                addFoodToHomepane(homeViewPane);

                for (Food f : SessionManager.cart){
                    System.out.println(f.name);
                }

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
//            for (List<JComponent> foodComp : foodsComponents) {
//                for (JComponent foodC : foodComp) {
//                    windowFrame.add(foodC);
//                }
//            }

            //add restaurant component.
            for (List<JComponent> restComp : restaurantComponents) {
                for (JComponent restC : restComp) {
                    windowFrame.add(restC);
                }
            }

        } else {
//            // Otherwise, add only the food components that match the search text
//            for (int i = 0; i < foods.size(); i++) {
//                if (foods.get(i).name.contains(searchFilteringText)) {
//                    for (JComponent foodC : foodsComponents.get(i)) {
//                        windowFrame.add(foodC);
//                    }
//                }
//            }

            for (int i = 0; i < restaurants.size(); i++) {
                if (restaurants.get(i).name.contains(searchFilteringText)) {
                    for (JComponent resC : restaurantComponents.get(i)) {
                        windowFrame.add(resC);
                    }
                }
            }
        }
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
        return 200 + (i * 270);
    }
}
