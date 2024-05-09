import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class User{
    public int userID;
    public String name;
    public String email;
    public String password;

    public int getUserType() {
        return userType;
    }

    public int userType; //1 -> customer, 2-> driver, 3->administrator.


    public boolean convertToCustomer(){
        this.userType = 1;

        //find credentials of this customer and make the last element as driver
        List<String> userCredLines = readFileLine("user.txt");
        int lineNumber = 0;

        for(String line : userCredLines){
            System.out.println(line);
            String creds[] = line.split("--");
            if(creds[0].equals(String.valueOf(this.userID)) && creds[2].equals(email) && creds[3].equals(password) && creds[4].equals("driver")){
                fileWrite("user.txt",String.valueOf(this.userID) + "--" + name + "--" + email + "--" + password + "--" + "customer");
                FileOps.lineRemover("user.txt", lineNumber + 1);
                return true;
            }
            lineNumber++;
        }

        return false;
    }
    public boolean convertToDriver(){
        this.userType = 2;

        //find credentials of this customer and make the last element as driver
        List<String> userCredLines = readFileLine("user.txt");

        int lineNumber = 0;
        for(String line : userCredLines){
            String creds[] = line.split("--");
            if(creds[0].equals(String.valueOf(this.userID)) && creds[2].equals(email) && creds[3].equals(password) && creds[4].equals("customer")){
                fileWrite("user.txt",String.valueOf(this.userID) + "--" + name + "--" + email + "--" + password + "--" + "driver");
                FileOps.lineRemover("user.txt", lineNumber + 1);
                return true;
            }
            lineNumber++;
        }

        return false;
    }

    public int getId(){
        return this.userID;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    void register(int UID, String name, String email, String password){
        this.userID = UID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = 1;
    }

    boolean login(String email, String password){
        return !this.email.equals(email) || !this.password.equals(password);
    }

    void deleteUser(String email, String password){

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
}