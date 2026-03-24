import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// Booking Service
class BookingService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> roomTypeToIds = new HashMap<>();

    public void processBookings(Queue<Reservation> queue, InventoryService inventoryService) {

        while (!queue.isEmpty()) {
            Reservation request = queue.poll();

            System.out.println("\nProcessing: " + request.getGuestName() +
                    " (" + request.getRoomType() + ")");

            if (inventoryService.isAvailable(request.getRoomType())) {

                String roomId = generateRoomId(request.getRoomType());

                while (allocatedRoomIds.contains(roomId)) {
                    roomId = generateRoomId(request.getRoomType());
                }

                allocatedRoomIds.add(roomId);

                roomTypeToIds.putIfAbsent(request.getRoomType(), new HashSet<>());
                roomTypeToIds.get(request.getRoomType()).add(roomId);

                inventoryService.decrement(request.getRoomType());

                System.out.println("Booking CONFIRMED -> Room ID: " + roomId);

            } else {
                System.out.println("Booking FAILED -> No rooms available");
            }
        }
    }

    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 5);
    }

    public void displayAllocations() {
        System.out.println("\nRoom Allocations:");
        for (Map.Entry<String, Set<String>> entry : roomTypeToIds.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}


public class BookMyApp {

    public static void main(String[] args) {

        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Alice", "Deluxe"));
        bookingQueue.add(new Reservation("Bob", "Standard"));
        bookingQueue.add(new Reservation("Charlie", "Suite"));
        bookingQueue.add(new Reservation("David", "Suite")); // should fail

        InventoryService inventoryService = new InventoryService();
        BookingService bookingService = new BookingService();

        bookingService.processBookings(bookingQueue, inventoryService);

        bookingService.displayAllocations();
        inventoryService.displayInventory();
    }
}