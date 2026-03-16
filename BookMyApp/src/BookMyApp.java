
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }
    public void registerRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}


public class BookMyApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        System.out.print("Single : ");
        int single = sc.nextInt();
        inventory.registerRoomType("single ", single);
        System.out.print("Double : ");
        int doubleR = sc.nextInt();
        inventory.registerRoomType("DoubleR", doubleR);
        System.out.print("Suite : ");
        int suite = sc.nextInt();
        inventory.registerRoomType("Suite", suite);


        inventory.displayInventory();

        System.out.println("\nAvailable Single Rooms: " + inventory.getAvailability("single"));
        System.out.println("\nAvailable double Rooms: " + inventory.getAvailability("DoubleR"));
 System.out.println("\nAvailable Suite Rooms: " + inventory.getAvailability("Suite"));






        inventory.displayInventory();
    }
}