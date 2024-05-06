import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HomepaneComponents {
    public static java.util.List<JComponent> getFoodCard(Food food, int x, int y){
        java.util.List<JComponent> foodCardComponents = new ArrayList<>();

        double rating = 4.5; //fetch rating from db with food id.

        JPanel foodCardPanel = new JPanel();
        foodCardPanel.setBounds(x, y, 150, 200);
        foodCardPanel.setBackground(Color.white);
        foodCardPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout


        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(food.imagePath));
        imageLabel.setBounds(x, y, 150, 150);


        JLabel nameLabel = new JLabel(food.name);
        nameLabel.setBounds(x + 15, y + 150, 150, 25);
        nameLabel.setFont(nameLabel.getFont().deriveFont(17f));

        JLabel priceLabel = new JLabel(String.valueOf(food.price) + " $");
        priceLabel.setBounds(x + 15, y + 175, 100, 25);
        priceLabel.setFont(nameLabel.getFont().deriveFont(12f));

        JLabel ratingLabel = new JLabel(String.valueOf(rating));
        JLabel ratingImageLabel = new JLabel(new ImageIcon("icons/star_8x.png"));

        ratingLabel.setBounds(x + 110, y + 175, 30, 25);
        ratingImageLabel.setBounds(x + 136, y + 175,  15, 25);
        ratingLabel.setFont(nameLabel.getFont().deriveFont(12f));



        foodCardComponents.add(imageLabel);
        foodCardComponents.add(nameLabel);
        foodCardComponents.add(priceLabel);
        foodCardComponents.add(ratingLabel);
        foodCardComponents.add(ratingImageLabel);
        foodCardComponents.add(foodCardPanel);

        return foodCardComponents;
    }
}
