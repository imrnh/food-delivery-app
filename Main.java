

public class Main{

    public static void main(String[] args){
        Runner runner = new Runner();
        runner.createUser();
        runner.createUser();
        runner.createUser();
        runner.createUser();

        for(User usr: runner.users){
            System.out.println(usr.getId());
            System.out.println(usr.getName());
            System.out.println(usr.getEmail());
            System.out.println(usr.getPassword());
        }
    }

}