import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bookingId;
    private String customerName;
    private String roomType;

    public Reservation(String bookingId, String customerName, String roomType) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "BookingID: " + bookingId +
                ", Name: " + customerName +
                ", RoomType: " + roomType;
    }
}

class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state loaded successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state. Starting with default values.");
        }
        return null;
    }
}

class BookingSystem {
    private Map<String, Integer> inventory;
    private List<Reservation> bookings;

    public BookingSystem() {
        inventory = new HashMap<>();
        bookings = new ArrayList<>();

        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public void restore(SystemState state) {
        if (state != null) {
            this.inventory = state.inventory;
            this.bookings = state.bookings;
        }
    }

    public void createBooking(String id, String name, String roomType) {
        if (!inventory.containsKey(roomType)) {
            System.out.println("Invalid room type.");
            return;
        }

        if (inventory.get(roomType) <= 0) {
            System.out.println("No rooms available for " + roomType);
            return;
        }

        inventory.put(roomType, inventory.get(roomType) - 1);
        Reservation r = new Reservation(id, name, roomType);
        bookings.add(r);

        System.out.println("Booking successful: " + r);
    }

    public void displayState() {
        System.out.println("\nCurrent Inventory: " + inventory);

        System.out.println("Bookings:");
        if (bookings.isEmpty()) {
            System.out.println("No bookings.");
        } else {
            for (Reservation r : bookings) {
                System.out.println(r);
            }
        }
    }
    public SystemState getState() {
        return new SystemState(inventory, bookings);
    }
}

// Main Class
public class BookMyApp {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        SystemState loadedState = PersistenceService.load();
        system.restore(loadedState);

        system.createBooking("B001", "Alice", "Deluxe");
        system.createBooking("B002", "Bob", "Suite");

        system.displayState();

        PersistenceService.save(system.getState());
    }
}