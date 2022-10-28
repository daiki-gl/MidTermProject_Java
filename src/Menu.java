import java.util.*;

public class Menu {
    public static void showMenu() {
        char op;
        Scanner scan = new Scanner(System.in);

        do {
            System.out.println("*********************************\n" +
                    "Select the option from below.\n" +
                    "[A]Book a flight\n" +
                    "[B]Check your flight\n" +
                    "[C]Cancel the flight\n" +
                    "[D]Exit\n" +
                    "*********************************");
            op = scan.next().charAt(0);

            BookFlight bf = new BookFlight();
            switch (Character.toLowerCase(op)) {
                case 'a':
                    bf.booking();
                    break;
                case 'b':
                    App.clrscr();
                    System.out.println("+++++Your Reservations+++++");
                    bf.checkFlight();
                    break;
                case 'c':
                    App.clrscr();
                    try {
                        System.out.println("+++++Cancellation+++++");
                        if (BookFlight.getAllFlightData().size() > 0) {
                            bf.setDeleteNum();
                            System.out.println(bf.getDeleteNum());
                            bf.cancelFlight(BookFlight.getAllFlightData().get(bf.getDeleteNum()).get(0));
                        } else {
                            bf.errorMessage("There's no reservation.");
                        }
                    } catch (IndexOutOfBoundsException e) {
                        App.clrscr();
                        bf.errorMessage("Input specific number or alphabet.\nTry again from the top");
                    } catch (Exception e) {
                        App.clrscr();
                        bf.errorMessage("There's an error. Please try again.");
                    }
                    break;
                case 'd':
                    App.clrscr();
                    System.out.println("Thank you");
                    break;
                default:
                    App.clrscr();
                    bf.errorMessage("Please input an alphabet from [A] to [D]");
                    break;
            }
        } while (Character.toLowerCase(op) != 'd');
        scan.close();
    }
}
