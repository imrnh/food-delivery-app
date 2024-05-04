import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;

public class Main {
    static boolean isLoggedIn = false;
    static JFrame frame;
    static Runner runner;
    static User user;


    public static void main(String[] args) {
        frame =new JFrame();
        runner = new Runner();

        windowManager();

        System.out.println("Recalling the main class");

        frame.setSize(GUIConfig.WINDOW_WIDTH,GUIConfig.WINDOW_HEIGHT);//400 width and 500 height
        frame.setLayout(null);//using no layout managers
        frame.setVisible(true);//making the frame visible
    }


    private static void windowManager(){

        if (isLoggedIn){

        }
        else{
            authManger();
        }

    }

    //Manage Authentication screens
    private static void authManger(){
        LoginPage loginPage=new LoginPage();
        java.util.List<JComponent> loginComponents = loginPage.getLoginScreen();

        SignupPage signupPage=new SignupPage();
        java.util.List<JComponent> signupComponents = signupPage.getSignupScreen();


        //Add all the login components.
        for (JComponent component : loginComponents) {
            frame.add(component);
        }


        //Page switching for login page.
        loginComponents.get(loginComponents.toArray().length-1).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //delete login page and show signup page instead.
                for (JComponent component : loginComponents) {
                    frame.remove(component);
                }
                frame.revalidate();
                frame.repaint();
                //Add all the signup components.
                for (JComponent component : signupComponents) {
                    frame.add(component);
                }
            }
        });


        //page switching for signup page.
        signupComponents.get(signupComponents.toArray().length-1).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //delete login page and show signup page instead.
                for (JComponent component : signupComponents) {
                    frame.remove(component);
                }
                frame.revalidate();
                frame.repaint();
                //Add all the signup components.
                for (JComponent component : loginComponents) {
                    frame.add(component);
                }
            }
        });

        //Signup operation.
        signupComponents.get(signupComponents.toArray().length - 2).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                String name = ((JTextField) signupComponents.get(2)).getText();
                String email  = ((JTextField)signupComponents.get(4)).getText();
                String password = ((JTextField) signupComponents.get(6)).getText();

                String msg = runner.createUser(name, email, password);
                JOptionPane.showMessageDialog(frame, msg);

                //remove all singup components.
                for (JComponent component : signupComponents) {
                    frame.remove(component);
                }
                frame.repaint();

                isLoggedIn = true;
                windowManager();
            }
        });

        //Login operation.
        loginComponents.get(loginComponents.toArray().length - 2).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String email  = ((JTextField)loginComponents.get(3)).getText();
                String password = ((JTextField) loginComponents.get(4)).getText();

                boolean userValidated = runner.loginUser(email, password);

                if(userValidated){
                    //remove all login components.
                    for (JComponent component : loginComponents) {
                        frame.remove(component);
                    }
                    frame.repaint();

                    isLoggedIn = true;
                    windowManager();
                }
                else{
                    JOptionPane.showMessageDialog(frame, "Invalid username or password");
                }
            }
        });
    }


    //Customer pages
    private static void customerScreenManager(){
        //pass
    }

    //Driver pages
    private static void driverScreenManager(){}
}