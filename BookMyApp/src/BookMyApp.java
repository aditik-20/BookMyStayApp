// Version 3.0
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
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        } else {
            System.out.println("Room type not found in inventory.");
        }
    }
    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}
// Version 3.1

public class BookMyApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        System.out.print("Standard : ");
        int standard = sc.nextInt();
        inventory.registerRoomType("Standard", standard);
        System.out.print("Deluxe : ");
        int deluxe = sc.nextInt();
        inventory.registerRoomType("Deluxe", deluxe);
        System.out.print("Suite : ");
        int suite = sc.nextInt();
        inventory.registerRoomType("Suite", suite);


        inventory.displayInventory();

        System.out.println("\nAvailable Deluxe Rooms: " + inventory.getAvailability("Deluxe"));


        inventory.updateAvailability("Deluxe", 4);

        System.out.println("\nAfter Updating Deluxe Rooms:");
        inventory.displayInventory();
    }
}