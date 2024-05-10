import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DriverHomepage {
    private JFrame windowFrame;
    private final int titlePanelHeight = 60;
    private User user;
    double totalEarned = 0;

    private int currentYPositionTrack = 100;

    private java.util.List<JComponent> homepage_components = new ArrayList<>();

    public DriverHomepage(JFrame frame){
        this.windowFrame = frame;
    }


    List<Delivery> myDeliveryList = new ArrayList<>();
    List<Integer> trackDelvLine = new ArrayList<>();

    List<List<JComponent>> deliverCardComponents = new ArrayList<>();

    public java.util.List<JComponent> getHomepage(){
        homepage_components = new ArrayList<>();
        user = SessionManager.user;

        //iterate over the orders.txt and check if any order with my name.
        List<String> orderList = Filereader.readFileLine("orders.txt");

        int fileLineNumber = 0;
        for(String orderLine: orderList){
            String orderLineSplitted[] = orderLine.split("--");
            if(orderLineSplitted[2].equals(String.valueOf(user.userID))){
                myDeliveryList.add(new Delivery(orderLineSplitted[6], new Order(Integer.parseInt(orderLineSplitted[0]), Integer.parseInt(orderLineSplitted[1]), Integer.parseInt(orderLineSplitted[2]), Integer.parseInt(orderLineSplitted[3]), Double.parseDouble(orderLineSplitted[5]), 1, orderLineSplitted[4])));
                trackDelvLine.add(fileLineNumber); //track file's line number.

                if(orderLineSplitted[3].equals(String.valueOf("2"))){ //completed order, add 10% as earned.
                    totalEarned += Double.parseDouble(orderLineSplitted[5]) * 0.1;
                }
            }
            fileLineNumber++;
        }

        //build order card components
        for(int i = 0; i < myDeliveryList.size(); i++){
            deliverCardComponents.add(getDeliveryViewCard(myDeliveryList.get(i),  150, currentYPositionTrack, i));
            currentYPositionTrack += myDeliveryList.get(i).getOrder().orderStatus == 1 ? 500 : 130;
        }

        for(List<JComponent> componentList: deliverCardComponents){
            homepage_components.addAll(componentList);
        }


        //Adding the top bar. It is global to all the pane in customer page.
        JPanel title_panel = new JPanel();
        title_panel.setBounds(0, 0, GUIConfig.WINDOW_WIDTH, titlePanelHeight);
        title_panel.setBackground(Color.white);
        java.util.List<JComponent> titleComponents = getTitleComponents(user);
        homepage_components.addAll(titleComponents);
        homepage_components.add(title_panel);

        windowFrame.repaint();

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
        if (user instanceof Driver){
            credit_balance = ((Customer) user).credit;
        }

        String ordersBox = "icons/box.png";
        String cartBox = "icons/basket.png";

        JLabel fullNameLabel = new JLabel("Hi, " + fullName);
        JLabel balanceLabel = new JLabel("Earned: " + String.valueOf(totalEarned));


        JLabel viewCart = new JLabel();
        JLabel viewOrders = new JLabel();
        JButton changeToCustomer = new JButton("Change To Customer");


        fullNameLabel.setFont(fullNameLabel.getFont().deriveFont(23f));
        fullNameLabel.setForeground(Color.black);

        balanceLabel.setFont(balanceLabel.getFont().deriveFont(15f));
        balanceLabel.setForeground(Color.orange);

        viewCart.setBackground(Color.white);
        viewOrders.setBackground(Color.white);
        changeToCustomer.setBackground(Color.white);

        viewCart.setBorder(null);
        viewOrders.setBorder(null);
        changeToCustomer.setBorder(null);

        fullNameLabel.setBounds(100, 0, 250, 50);
        balanceLabel.setBackground(Color.darkGray);
        balanceLabel.setBounds(GUIConfig.WINDOW_WIDTH-420, 10, 150, 30);

        viewCart.setBounds(GUIConfig.WINDOW_WIDTH - 270, 10, 40, 30);
        viewOrders.setBounds(GUIConfig.WINDOW_WIDTH - 210, 10, 40, 30);
        changeToCustomer.setBounds(GUIConfig.WINDOW_WIDTH - 170, 10, 170, 30);

        changeToCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assert user != null;
                if(user.convertToCustomer()){
                    windowFrame.dispose();
                }
            }
        });

        titleComponents.add(fullNameLabel);
        titleComponents.add(viewCart);
        titleComponents.add(viewOrders);
        titleComponents.add(changeToCustomer);
        titleComponents.add(balanceLabel);

        return titleComponents;
    }


    private java.util.List<JComponent> getDeliveryViewCard(Delivery delivery, int x, int y, int delvCompIdx){
        List<JComponent> dVCard = new ArrayList<>();

        JPanel whitePanel = new JPanel();
        whitePanel.setBounds(x, y, GUIConfig.WINDOW_WIDTH-x, delivery.getOrder().orderStatus == 1 ? 450 : 110);
        whitePanel.setBackground(Color.white);
        whitePanel.setLayout(new FlowLayout());

        JLabel orderId = new JLabel("Order: " +String.valueOf(delivery.getOrder().orderID));
        orderId.setBounds(x + 15, y + 15, 150, 30);
        orderId.setFont(orderId.getFont().deriveFont(20f));

        if(delivery.getOrder().orderStatus == 1){ //not delivered yet
            orderId.setForeground(Color.green);

            //map label
            JLabel imageLabel = new JLabel();
            imageLabel.setBounds(x+15, y+110, 500, 300);
            dVCard.add(imageLabel);

            // Load and set an image
            downloadMapWithAddress(delivery.getAddress());
            try {
                BufferedImage image = ImageIO.read(new File("images/location_image.jpg"));
                ImageIcon icon = new ImageIcon(image);
                imageLabel.setIcon(icon);
            } catch (IOException e) {
                e.printStackTrace();
            }

            JLabel deliveredUseOTPWarningLabel = new JLabel("Delivered? Use customer's OTP to confirm.");
            JTextField OTPField = new JTextField();
            JButton confirmButton = new JButton("Confirm");

            deliveredUseOTPWarningLabel.setBounds(GUIConfig.WINDOW_WIDTH-x-400, y+310, 400, 45);
            OTPField.setBounds(GUIConfig.WINDOW_WIDTH-x-400, y+370, 250, 40);
            confirmButton.setBounds(GUIConfig.WINDOW_WIDTH-x-150, y+370, 100, 40);

            deliveredUseOTPWarningLabel.setBackground(Color.orange);

            confirmButton.setBackground(Color.orange);
            confirmButton.setForeground(Color.white);
            confirmButton.setBorder(null);


            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Perform the non-UI operations on the current thread
                    if (!delivery.getOrder().otp.equals(OTPField.getText())) {
                        JOptionPane.showMessageDialog(whitePanel, "Invalid OTP");
                        return;
                    }

                    FileOps.lineRemover("orders.txt", trackDelvLine.get(delvCompIdx) + 1);

                    Order theOrderWas = delivery.getOrder();
                    theOrderWas.orderStatus = 2;
                    Filereader.fileWrite("orders.txt", theOrderWas.orderID + "--" + theOrderWas.userID + "--" + theOrderWas.driverID + "--" +  theOrderWas.orderStatus + "--" + theOrderWas.otp + "--"  + theOrderWas.orderCost + "--" + delivery.getAddress() + "--" + "Food Names Hidden" );

                    Order prevOrder = delivery.getOrder();
                    prevOrder.orderStatus = 2;
                    Delivery newDelv = new Delivery(delivery.getAddress(), prevOrder);
                    myDeliveryList.set(delvCompIdx, newDelv);

                    // Update the UI components on the EDT
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            JOptionPane.showMessageDialog(whitePanel, "Order Confirmed");

                            totalEarned += delivery.getOrder().orderCost * 0.1;

                            // Remove and rebuild the delivery card components
                            for (List<JComponent> delvCompList : deliverCardComponents) {
                                for(JComponent component: delvCompList){
                                    windowFrame.remove(component);
                                }
                            }

                            deliverCardComponents.clear();
                            for (int i = 0; i < myDeliveryList.size(); i++) {
                                int y_axis = myDeliveryList.get(i).getOrder().orderStatus == 1 ? 100 + (i * 500) : 100 + (i * 130);
                                deliverCardComponents.add(getDeliveryViewCard(myDeliveryList.get(i), 150, y_axis, i));
                            }

                            for (List<JComponent> componentList : deliverCardComponents) {
                                for(JComponent component: componentList){
                                    windowFrame.add(component);
                                }
                            }

                            windowFrame.revalidate();
                            windowFrame.repaint();
                        }
                    });
                }
            });
            dVCard.add(deliveredUseOTPWarningLabel);
            dVCard.add(confirmButton);
            dVCard.add(OTPField);
        }



        //google map view
        try {
            // this url should be api url.
            String imageUrl = "https://www.google.com/maps/place/New+York,+NY,+USA/@40.6976307,-74.1448306,11z/data=!3m1!4b1!4m6!3m5!1s0x89c24fa5d33f083b:0xc80b8f06e177fe62!8m2!3d40.7127753!4d-74.0059728!16zL20vMDJfMjg2?entry=ttu";
            String destinationFile = "image.jpg";
            String str = destinationFile;
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destinationFile);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        dVCard.add(new JLabel(new ImageIcon((new ImageIcon("image.jpg")).getImage().getScaledInstance(630, 600,
                java.awt.Image.SCALE_SMOOTH))));


        JLabel orderValue = new JLabel("Price: " + String.valueOf(delivery.getOrder().orderCost) + " USD");
        orderValue.setBounds(GUIConfig.WINDOW_WIDTH-x-100, y+15, 100, 30);

        JLabel locLabel = new JLabel(delivery.getAddress());
        locLabel.setBounds(x + 15, y + 60, GUIConfig.WINDOW_WIDTH-x-10, 30);
        locLabel.setIcon(new ImageIcon("icons/placeholder.png"));

        dVCard.add(orderId);
        dVCard.add(locLabel);
        dVCard.add(orderValue);

        dVCard.add(whitePanel);

        return dVCard;
    }

    public static List<String> readFileLine(String fileName) {
        java.util.List<String> fileLine = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileLine.add(line);
            }
        } catch (IOException e) {
            System.out.println("File reading error: " + e.getMessage());
        }
        return fileLine;
    }

    public static void fileWrite(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true)))) {
            writer.write(content + System.lineSeparator());
            System.out.println("Content added to the file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void downloadMapWithAddress(String address){
        try {

            String API_KEY = "AIzaSyDtf_GHR8n_rFsWuRdNM1XAI8HS-qQZ8G4";

            String apiUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" + address.replace(" ", "+")
                    + "&zoom=15&size=800x600&markers=color:red%7C" + address.replace(" ", "+") + "&key=+" + API_KEY;

            String destinationFile = "images/location_image.jpg";
            URL url = new URL(apiUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destinationFile);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
