import java.util.*;

abstract class Room {

    private int numberOfBeds;
    private int roomSize;
    private double price;

    public Room(int numberOfBeds, int roomSize, double price) {
        this.numberOfBeds = numberOfBeds;
        this.roomSize = roomSize;
        this.price = price;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getRoomType();

    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Room Size: " + roomSize + " sq ft");
        System.out.println("Price: $" + price);
    }
}
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 100);
    }

    public String getRoomType() {
        return "Single Room";
    }
}
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 180);
    }

    public String getRoomType() {
        return "Double Room";
    }
}
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 500, 300);
    }

    public String getRoomType() {
        return "Suite Room";
    }
}

public class BookMyApp {
    static void main() {
    Scanner sc = new Scanner(System.in);
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        System.out.println("Single Room");
        int singleRoomAvailable = sc.nextInt();
        System.out.println("Double Room");
        int doubleRoomAvailable = sc.nextInt();
        System.out.println("Suite Room");
        int suiteRoomAvailable = sc.nextInt();


        System.out.println(" Hotel Room Information \n");

        singleRoom.displayRoomDetails();
        System.out.println("Available: " + singleRoomAvailable);
        System.out.println();

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleRoomAvailable);
        System.out.println();

        suiteRoom.displayRoomDetails();
        System.out.println("Available: " + suiteRoomAvailable);
        System.out.println();

        System.out.println("Application Terminated.");
    }
}