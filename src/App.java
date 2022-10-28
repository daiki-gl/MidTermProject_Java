import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        Passenger passenger = new Passenger("user", "pass");

        clrscr();
        System.out.println("=================================");
        System.out.println("Welcome to ABC Air Line");
        System.out.println("=================================");

        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("Please enter your username");
            String user = scan.nextLine();
            System.out.println("Please enter your password");
            String pass = scan.nextLine();

            if (user.equals(passenger.getUserName()) &&
                    pass.equals(passenger.getUserPassword())) {
                System.out.println("Welcome, " + user);
                break;
            } else {
                clrscr();
                System.out.println("Wrong username or password.\nTry again.");
            }
        }
        clrscr();
        Menu.showMenu();
        scan.close();
    }

    public static void clrscr() {
        // Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
            System.out.print("\033\143");
        } catch (IOException | InterruptedException ex) {
        }
    }

}
