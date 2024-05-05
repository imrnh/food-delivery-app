import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class LoginPage {

    public List<JComponent> getLoginScreen(){
        List<JComponent> components = new ArrayList<JComponent>();

        JLabel panelLabel = new JLabel("Customer/Driver Login");
        Font font = panelLabel.getFont();
        panelLabel.setFont(font.deriveFont(Font.PLAIN, 25));

        JLabel emailLabel=new JLabel("Enter Email");
        JTextField emailTextField= new JTextField("");

        JLabel passwordLabel=new JLabel("Enter Password");
        JTextField passwordTextArea= new JTextField("");

        JButton loginButton = new JButton("Login");

        JLabel signupButton = new JLabel("Don't have an account?");

        loginButton.setBackground(Color.blue);
        loginButton.setForeground(Color.white);


        panelLabel.setBounds(GUIConfig.WINDOW_WIDTH/2-200, GUIConfig.WINDOW_HEIGHT/2-250, 300, 100);
        emailLabel.setBounds(GUIConfig.WINDOW_WIDTH/2-200, GUIConfig.WINDOW_HEIGHT/2-150, 150, 30);
        emailTextField.setBounds(GUIConfig.WINDOW_WIDTH/2-200, GUIConfig.WINDOW_HEIGHT/2-110, 300, 40);
        passwordLabel.setBounds(GUIConfig.WINDOW_WIDTH/2-200, GUIConfig.WINDOW_HEIGHT/2-50, 150, 30);
        passwordTextArea.setBounds(GUIConfig.WINDOW_WIDTH/2-200, GUIConfig.WINDOW_HEIGHT/2-10, 300, 40);
        loginButton.setBounds(GUIConfig.WINDOW_WIDTH/2-200, GUIConfig.WINDOW_HEIGHT/2+60, 150, 45);
        signupButton.setBounds(GUIConfig.WINDOW_WIDTH/2-200, GUIConfig.WINDOW_HEIGHT/2+110, 300, 30);

        components.add(panelLabel);
        components.add(emailLabel);
        components.add(passwordLabel);
        components.add(emailTextField);
        components.add(passwordTextArea);
        components.add(loginButton);
        components.add(signupButton);

        return components;
    }
}
