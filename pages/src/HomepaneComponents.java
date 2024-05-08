import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
//
//        JLabel ratingLabel = new JLabel(String.valueOf(rating));
//        JLabel ratingImageLabel = new JLabel(new ImageIcon("icons/star.png"));

        JLabel addToCartButton = new JLabel("Buy");
        addToCartButton.setBounds(x + 175, y + 100,  110, 25);
        addToCartButton.setBackground(Color.orange);
        addToCartButton.setIcon(new ImageIcon("icons/add-to-cart.png"));

//        ratingLabel.setBounds(x + 300 - 50, y + 110, 30, 25);
//        ratingImageLabel.setBounds(x + 300 - 20, y + 110,  15, 25);
//        ratingLabel.setFont(nameLabel.getFont().deriveFont(12f));

        addToCartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                SessionManager.cart.add(food);
                JOptionPane.showMessageDialog(addToCartButton, food.name + " added to cart.\nClick cart icon to view or complete order", "Cart", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        foodCardComponents.add(imageLabel);
        foodCardComponents.add(nameLabel);
        foodCardComponents.add(priceLabel);
//        foodCardComponents.add(ratingLabel);
//        foodCardComponents.add(ratingImageLabel);
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

        JLabel locationLabel = new JLabel( " " + restaurant.location);
        locationLabel.setIcon(new ImageIcon("icons/placeholder.png"));
        locationLabel.setBounds(x + 600, y + 10, 430, 25);
        locationLabel.setFont(nameLabel.getFont().deriveFont(12f));

        JLabel ratingLabel = new JLabel(String.valueOf(rating));
        JLabel ratingImageLabel = new JLabel(new ImageIcon("icons/star.png"));
        JLabel reviewButton = new JLabel("Show Reviews");
        reviewButton.setFont(reviewButton.getFont().deriveFont(11f));

        JLabel writeAReviewButton = new JLabel("Write a Review");
        writeAReviewButton.setFont(reviewButton.getFont().deriveFont(11f));

        ratingLabel.setBounds(x + 1050, y + 10, 30, 25);
        ratingImageLabel.setBounds(x + 1080, y + 10,  15, 25);
        reviewButton.setBounds(x + 1110, y + 10,  123, 25);
        writeAReviewButton.setBounds(x + 1225, y + 10,  123, 25);
        ratingLabel.setFont(nameLabel.getFont().deriveFont(12f));


        JPanel seperatorLine = new JPanel();
        seperatorLine.setBounds(x + 10, y+45, GUIConfig.WINDOW_WIDTH-250, 1);
        seperatorLine.setBackground(Color.getHSBColor(217, 1, 98));


        //add food cards to the restaurant.
        for (int i=0; i<restaurant.foods.size(); i++) {
            foodsComponents.add(HomepaneComponents.getFoodCard(restaurant.foods.get(i), x+15 + (i * 320), y+50));
        }


        reviewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                //read reviews
                String reviewText = Filereader.readFile("reviews/restaurant_"+restaurant.getId()+".txt");
                JOptionPane.showMessageDialog(seperatorLine, reviewText);
            }
        });

        writeAReviewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                JFrame reviewFrame = new JFrame();

                JTextArea rtField = new JTextArea();
                rtField.setBounds(30, 20, 440, 150);

                JLabel ratingLabel = new JLabel("Rating");
                ratingLabel.setBounds(30, 180, 70, 20);
                reviewFrame.add(ratingLabel);

                String ratingStars[] = {"1", "2", "3", "4", "5"};
                JComboBox cb =new JComboBox(ratingStars);
                cb.setBounds(110, 180,100,20);
                reviewFrame.add(cb);

                JTextField dummyField = new JTextField();

                cb.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String rat = (String) cb.getItemAt(cb.getSelectedIndex());
                        dummyField.setText(rat);

                    }
                });

                JButton submitReviewButton = new JButton("Add Review");
                submitReviewButton.setBounds(150, 230, 200, 35);

                submitReviewButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        //write rewivew text to restaurant_id.txt
                        Filereader.fileWrite("reviews/restaurant_" + restaurant.getId() + ".txt",  rtField.getText() + "\n \n \n");
                        Filereader.fileWrite("reviews/ratings.txt", restaurant.getId() + "--"  + dummyField.getText());

//                        reviewFrame.dispose();
                    }
                });


                reviewFrame.add(rtField);
                reviewFrame.add(submitReviewButton);
                reviewFrame.setTitle("Write a review");

                reviewFrame.setLayout(null);

                reviewFrame.setSize(500, 320);
                reviewFrame.setLocationRelativeTo(seperatorLine);
                reviewFrame.setVisible(true);
            }
        });



        restaurantCardComponent.add(nameLabel);
        restaurantCardComponent.add(locationLabel);
        restaurantCardComponent.add(ratingLabel);
        restaurantCardComponent.add(ratingImageLabel);
        restaurantCardComponent.add(reviewButton);
        restaurantCardComponent.add(writeAReviewButton);
        for(java.util.List<JComponent> foodCard: foodsComponents){
            restaurantCardComponent.addAll(foodCard);
        }

//        restaurantCardComponent.add(foodCardPanel);

        return restaurantCardComponent;
    }
}
