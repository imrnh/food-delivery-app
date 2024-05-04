import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class SignupPage {

    public List<JComponent> getSignupScreen(){
        List<JComponent> components = new ArrayList<JComponent>();

        JLabel panelLabel = new JLabel("Create an account");
        Font font = panelLabel.getFont();
        panelLabel.setFont(font.deriveFont(Font.PLAIN, 25));

        JLabel nameLabel=new JLabel("Enter Full Name");
        JTextField nameTextField= new JTextField("");

        JLabel emailLabel=new JLabel("Enter Email");
        JTextField emailTextField= new JTextField("");

        JLabel passwordLabel=new JLabel("Enter Password");
        JTextField passwordTextArea= new JTextField("");

        JButton signupButton = new JButton("Sign up");

        JLabel loginButton = new JLabel("Already have an account?");

        signupButton.setBackground(Color.blue);
        signupButton.setForeground(Color.white);



        panelLabel.setBounds(GUIConfig.WINDOW_WIDTH/2-200, GUIConfig.WINDOW_HEIGHT/2-250, 360, 100);
        nameLabel.setBounds(GUIConfig.WINDOW_WIDTH/2-200, GUIConfig.WINDOW_HEIGHT/2-150, 150, 30);
        nameTextField.setBounds(GUIConfig.WINDOW_WIDTH/2-200, GUIConfig.WINDOW_HEIGHT/2-110, 360, 40);
        emailLabel.setBounds(GUIConfig.WINDOW_WIDTH/2-200, GUIConfig.WINDOW_HEIGHT/2-50, 150, 30);
        emailTextField.setBounds(GUIConfig.WINDOW_WIDTH/2-200, GUIConfig.WINDOW_HEIGHT/2-10, 360, 40);
        passwordLabel.setBounds(GUIConfig.WINDOW_WIDTH/2-200, GUIConfig.WINDOW_HEIGHT/2+50, 150, 30);
        passwordTextArea.setBounds(GUIConfig.WINDOW_WIDTH/2-200, GUIConfig.WINDOW_HEIGHT/2+90, 360, 40);
        signupButton.setBounds(GUIConfig.WINDOW_WIDTH/2-200, GUIConfig.WINDOW_HEIGHT/2+150, 150, 45);
        loginButton.setBounds(GUIConfig.WINDOW_WIDTH/2-200, GUIConfig.WINDOW_HEIGHT/2+210, 300, 30);

        components.add(panelLabel);
        components.add(nameLabel);
        components.add(nameTextField);
        components.add(emailLabel);
        components.add(emailTextField);
        components.add(passwordLabel);
        components.add(passwordTextArea);
        components.add(signupButton);
        components.add(loginButton);

        return components;
    }
}
