import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class HomepaneComponents {
    public static java.util.List<JComponent> getFoodCard(Food food, int x, int y){
        java.util.List<JComponent> foodCardComponents = new ArrayList<>();

        double rating = 4.5; //fetch rating from db with food id.

        JPanel foodCardPanel = new JPanel();
        foodCardPanel.setBounds(x, y, 310, 160);
        foodCardPanel.setBackground(Color.white);
        foodCardPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout


        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(food.imagePath));
        imageLabel.setBounds(x + 10, y + 10, 140, 140);

        JLabel nameLabel = new JLabel(food.name);
        nameLabel.setBounds(x + 175, y + 15, 150, 25);
        nameLabel.setFont(nameLabel.getFont().deriveFont(17f));

        JLabel priceLabel = new JLabel(String.valueOf(food.price) + " $");
        priceLabel.setBounds(x + 175, y + 40, 100, 25);
        priceLabel.setFont(nameLabel.getFont().deriveFont(12f));

        JLabel ratingLabel = new JLabel(String.valueOf(rating));
        JLabel ratingImageLabel = new JLabel(new ImageIcon("icons/star.png"));

        JLabel addToCartButton = new JLabel("Buy");
        addToCartButton.setBounds(x + 175, y + 100,  110, 25);
        addToCartButton.setBackground(Color.orange);
        addToCartButton.setIcon(new ImageIcon("icons/trolley.png"));

        ratingLabel.setBounds(x + 300 - 50, y + 110, 30, 25);
        ratingImageLabel.setBounds(x + 300 - 20, y + 110,  15, 25);
        ratingLabel.setFont(nameLabel.getFont().deriveFont(12f));

        addToCartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                SessionManager.cart.add(food);
                JOptionPane.showMessageDialog(addToCartButton, food.name + " added to cart", "Cart", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        foodCardComponents.add(imageLabel);
        foodCardComponents.add(nameLabel);
        foodCardComponents.add(priceLabel);
        foodCardComponents.add(ratingLabel);
        foodCardComponents.add(ratingImageLabel);
        foodCardComponents.add(addToCartButton);
        foodCardComponents.add(foodCardPanel);

        return foodCardComponents;
    }

    public static java.util.List<JComponent> getRestaurantCard(Restaurant restaurant, int x, int y){
        java.util.List<JComponent> restaurantCardComponent = new ArrayList<>();
        java.util.List<java.util.List<JComponent>> foodsComponents = new ArrayList<>();

        double rating = 4.5; //fetch rating from db with food id.

//        JPanel foodCardPanel = new JPanel();
//        foodCardPanel.setBounds(x, y, 450, 150);
//        foodCardPanel.setBackground(Color.white);
//        foodCardPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout


        JLabel nameLabel = new JLabel(restaurant.name);
        nameLabel.setBounds(x + 15, y + 10, 500, 30);
        nameLabel.setFont(nameLabel.getFont().deriveFont(17f));

        JLabel ratingLabel = new JLabel(String.valueOf(rating));
        JLabel ratingImageLabel = new JLabel(new ImageIcon("icons/star.png"));

        ratingLabel.setBounds(x + 800, y + 10, 30, 25);
        ratingImageLabel.setBounds(x + 830, y + 10,  15, 25);
        ratingLabel.setFont(nameLabel.getFont().deriveFont(12f));


        JLabel locationLabel = new JLabel( " " + restaurant.location);
        locationLabel.setIcon(new ImageIcon("icons/placeholder.png"));
        locationLabel.setBounds(x + 900, y + 10, 430, 25);
        locationLabel.setFont(nameLabel.getFont().deriveFont(12f));

        JPanel seperatorLine = new JPanel();
        seperatorLine.setBounds(x + 10, y+50, GUIConfig.WINDOW_WIDTH-300, 1);
        seperatorLine.setBackground(Color.gray);


        //add food cards to the restaurant.
        for (int i=0; i<restaurant.foods.size(); i++) {
            foodsComponents.add(HomepaneComponents.getFoodCard(restaurant.foods.get(i), x+15 + (i * 320), y+70));
        }


        restaurantCardComponent.add(nameLabel);
        restaurantCardComponent.add(locationLabel);
        restaurantCardComponent.add(ratingLabel);
        restaurantCardComponent.add(ratingImageLabel);
        restaurantCardComponent.add(seperatorLine);

        for(java.util.List<JComponent> foodCard: foodsComponents){
            restaurantCardComponent.addAll(foodCard);
        }

//        restaurantCardComponent.add(foodCardPanel);

        return restaurantCardComponent;
    }
}
