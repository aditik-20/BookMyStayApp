import java.util.*;

// Custom Exception
class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}

// Reservation Class
class Reservation {
    private String bookingId;
    private String customerName;
    private String roomType;
    private String roomId; // Unique allocated room
    private boolean isCancelled;

    public Reservation(String bookingId, String customerName, String roomType, String roomId) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void cancel() {
        isCancelled = true;
    }

    @Override
    public String toString() {
        return "BookingID: " + bookingId +
                ", Name: " + customerName +
                ", RoomType: " + roomType +
                ", RoomID: " + roomId +
                ", Status: " + (isCancelled ? "Cancelled" : "Confirmed");
    }
}

// Inventory Class
class RoomInventory {
    private Map<String, Integer> roomCount;
    private Map<String, Stack<String>> availableRooms;

    public RoomInventory() {
        roomCount = new HashMap<>();
        availableRooms = new HashMap<>();

        // Initialize inventory
        initializeRoomType("Standard", 2);
        initializeRoomType("Deluxe", 2);
        initializeRoomType("Suite", 1);
    }

    private void initializeRoomType(String type, int count) {
        roomCount.put(type, count);
        Stack<String> stack = new Stack<>();

        for (int i = count; i >= 1; i--) {
            stack.push(type.substring(0, 1) + i); // e.g., S1, D2
        }

        availableRooms.put(type, stack);
    }

    public String allocateRoom(String roomType) throws BookingException {
        if (!availableRooms.containsKey(roomType)) {
            throw new BookingException("Invalid room type: " + roomType);
        }

        Stack<String> stack = availableRooms.get(roomType);

        if (stack.isEmpty()) {
            throw new BookingException("No rooms available for type: " + roomType);
        }

        roomCount.put(roomType, roomCount.get(roomType) - 1);
        return stack.pop();
    }

    public void releaseRoom(String roomType, String roomId) {
        availableRooms.get(roomType).push(roomId);
        roomCount.put(roomType, roomCount.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("Inventory Count: " + roomCount);
    }
}

class BookingService {
    private Map<String, Reservation> bookings = new HashMap<>();
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void createBooking(String id, String name, String roomType) {
        try {
            String roomId = inventory.allocateRoom(roomType);

            Reservation res = new Reservation(id, name, roomType, roomId);
            bookings.put(id, res);

            System.out.println("Booking Successful: " + res);

        } catch (BookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }

    public void cancelBooking(String bookingId) {
        try {
            if (!bookings.containsKey(bookingId)) {
                throw new BookingException("Booking ID not found: " + bookingId);
            }

            Reservation res = bookings.get(bookingId);

            if (res.isCancelled()) {
                throw new BookingException("Booking already cancelled: " + bookingId);
            }

            inventory.releaseRoom(res.getRoomType(), res.getRoomId());
            res.cancel();

            System.out.println("Cancellation Successful: " + res);

        } catch (BookingException e) {
            System.out.println("Cancellation Failed: " + e.getMessage());
        }
    }

    public void showAllBookings() {
        System.out.println("\nAll Bookings:");
        for (Reservation r : bookings.values()) {
            System.out.println(r);
        }
    }
}


public class BookMyApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        service.createBooking("B001", "Alice", "Deluxe");
        service.createBooking("B002", "Bob", "Suite");

        service.cancelBooking("B001");

        service.cancelBooking("B001");

        service.cancelBooking("B999");

        service.createBooking("B003", "Charlie", "Deluxe");

        System.out.println();
        inventory.displayInventory();
        service.showAllBookings();
    }
}