import java.util.*;

public class BookFlight extends FlightsData {
    private FlightsData fData = new FlightsData();

    private String fromWhere;
    private boolean found = false;
    private int passengerNum;
    private int deleteNum = -1;
    private static ArrayList<ArrayList<String>> allFlightData = new ArrayList<>();
    private ArrayList<String> bookingData = new ArrayList<>();
    private TreeMap<String, Integer[][]> flightList = new TreeMap<>();

    public static ArrayList<ArrayList<String>> getAllFlightData() {
        return allFlightData;
    }

    public BookFlight() {
        flightList.put("japan", FlightsData.japanSeats);
        flightList.put("germany", FlightsData.germanySeats);
    }

    public ArrayList<String> getBookingData() {
        return bookingData;
    }

    public String getFromWhere() {
        return fromWhere;
    }

    public int getDeleteNum() {
        return deleteNum;
    }

    public void setDeleteNum() {
        checkFlight();
        System.out.println("*Input the number which you want to cancel.");
        this.deleteNum = scan.nextInt() - 1;
    }

    Scanner scan = new Scanner(System.in);

    public void booking() {
        App.clrscr();
        try {
            System.out.println("How many passengers do you want to book?");
            passengerNum = scan.nextInt();
            App.clrscr();
            System.out.println(passengerNum + " passengers");

            // Searching flight
            searchFlight();

            if (found) {
                chooseTime();
                App.clrscr();

                // Booking seats
                do {
                    showSeats(getFromWhere());
                    storeSeatData(getFromWhere());
                } while (passengerNum > 0);
                allFlightData.add(bookingData);
            }

        } catch (InputMismatchException e) {
            App.clrscr();
            errorMessage("Input specific number or alphabet.\nTry again from the top");
        } catch (ArrayIndexOutOfBoundsException e) {
            App.clrscr();
            errorMessage("Input incorrect number\nTry again from the top");
        } catch (Exception e) {
            App.clrscr();
            errorMessage("There's an error.\nTry again from the first");
        }
    }

    public void searchFlight() {
        System.out.println(
                "=======================\nWhere do you want to go.\nInput the countries\n========================");
        System.out.println("From :");
        fromWhere = scan.next().toLowerCase();
        System.out.println("To :");
        String toWhere = scan.next().toLowerCase();

        App.clrscr();

        for (int i = 0; i < fData.flights.length; i++) {
            if (fData.flights[i][0].equals(fromWhere) && fData.flights[i][1].equals(toWhere)) {
                found = true;
                System.out.println("================================================\nThere're flights from " +
                        fromWhere.toUpperCase() + " to " + toWhere.toUpperCase() +
                        "\n================================================");
                bookingData.add(fromWhere);
                bookingData.add(toWhere);
            }
        }
        if (!found) {
            errorMessage("========================\nSorry there's no flight from " + fromWhere
                    + " right now\n========================");
        }
    }

    public Integer[][] searchByCountry(String country) {
        return flightList.get(country);
    }

    public void chooseTime() {
        System.out.println("Choose the the time of departure.");
        for (int i = 0; i < fData.times.length; i++) {
            System.out.println("[" + (i + 1) + "]" + fData.times[i]);
        }
        int flightTime = scan.nextInt();
        bookingData.add(fData.times[flightTime - 1]);
    }

    public String showSeats(String country) {
        Integer[][] flightSeats = searchByCountry(country.toLowerCase());

        if (flightSeats == null) {
            return "No flights found for " + country;
        }

        System.out.printf("%3s %1s %1s %3s %1s %1s", "A", "B", "C", "D", "E", "F");
        for (int i = 0; i < flightSeats.length; i++) {
            System.out.println("");
            System.out.print(i + 1);

            for (int j = 0; j < 6; j++) {
                if (j == 3) {
                    if (flightSeats[i][j] == 0) {
                        System.out.printf("%4s", "●");
                    } else {
                        System.out.printf("%4s", "×");
                    }
                } else if (flightSeats[i][j] == 0 && !(j == 3)) {
                    System.out.printf("%2s", "●");
                } else {
                    System.out.printf("%2s", "×");
                }

            }
        }
        return "\n";
    }

    public void storeSeatData(String country) {
        Integer[][] flightSeats = searchByCountry(country.toLowerCase());
        System.out.println("\n========================\n*Choose " + passengerNum + " seats \ne.g) A1");

        String inputColRow = scan.next();
        String[] colRow = inputColRow.split("");

        int row = Integer.parseInt(colRow[1]) - 1;
        char[] colArr = colRow[0].toUpperCase().toCharArray();
        char col = colArr[0];

        if (flightSeats[row][checkColumn(col)] == 0) {
            App.clrscr();
            flightSeats[row][checkColumn(col)] = 1;
            System.out.println("* Booked your seats *");
            passengerNum--;
            bookingData.add(inputColRow.toUpperCase());
        } else {
            App.clrscr();
            errorMessage(
                    "*=*=*=*=*=*=*=*=*=*=*=*=*=*\nYou cannot choose this seat.\nChose other one.\n*=*=*=*=*=*=*=*=*=*=*=*=*=*");
        }
    }

    public int checkColumn(int col) {
        int colInt = col;
        switch (col) {
            case 'A':
                colInt = 0;
                break;
            case 'B':
                colInt = 1;
                break;
            case 'C':
                colInt = 2;
                break;
            case 'D':
                colInt = 3;
                break;
            case 'E':
                colInt = 4;
                break;
            case 'F':
                colInt = 5;
                break;
            default:
                errorMessage("Please type the alphabet");
                break;
        }
        return colInt;
    }

    public void checkFlight() {
        if (allFlightData.size() == 0) {
            errorMessage("There's no booking");
        } else {
            for (int i = 0; i < allFlightData.size(); i++) {
                System.out.println("[" + (i + 1) + "]\n" + allFlightData.get(i).get(0).toUpperCase() + " ---> "
                        + allFlightData.get(i).get(1).toUpperCase() + "\n" + "Date: " + allFlightData.get(i).get(2));

                System.out.print("Seat: ");
                for (int j = 3; j < allFlightData.get(i).size(); j++) {
                    System.out.print(allFlightData.get(i).get(j) + ",");
                }
                System.out.println("\n====================");
            }
        }
    }

    public void cancelFlight(String country) {
        Integer[][] flightSeats = searchByCountry(country.toLowerCase());
        App.clrscr();
        for (int j = 3; j < allFlightData.get(deleteNum).size(); j++) {
            allFlightData.get(deleteNum).get(j);
            String[] colRow = allFlightData.get(deleteNum).get(j).split("");

            int row = Integer.parseInt(colRow[1]) - 1;
            char[] colArr = colRow[0].toUpperCase().toCharArray();
            char col = colArr[0];

            flightSeats[row][checkColumn(col)] = 0;
        }
        getAllFlightData().remove(deleteNum);
        App.clrscr();
        System.out.println("Successfully canceled your flight");
    }

    public void errorMessage(String msg) {
        System.out.println(msg);
    }

}