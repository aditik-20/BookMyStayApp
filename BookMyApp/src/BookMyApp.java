import java.util.*;

// Custom Exception for invalid booking scenarios
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Reservation {
    private String bookingId;
    private String customerName;
    private String roomType;
    private int nights;

    public Reservation(String bookingId, String customerName, String roomType, int nights) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId +
                ", Customer: " + customerName +
                ", Room Type: " + roomType +
                ", Nights: " + nights;
    }
}

class RoomInventory {
    private Map<String, Integer> roomStock;

    public RoomInventory() {
        roomStock = new HashMap<>();
        roomStock.put("Standard", 2);
        roomStock.put("Deluxe", 2);
        roomStock.put("Suite", 1);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!roomStock.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void validateAvailability(String roomType) throws InvalidBookingException {
        int available = roomStock.get(roomType);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
    }

    public void bookRoom(String roomType) {
        int current = roomStock.get(roomType);
        roomStock.put(roomType, current - 1);
    }

    public void displayInventory() {
        System.out.println("Current Room Inventory: " + roomStock);
    }
}

class BookingValidator {

    public static void validate(String customerName, String roomType, int nights, RoomInventory inventory)
            throws InvalidBookingException {

        if (customerName == null || customerName.trim().isEmpty()) {
            throw new InvalidBookingException("Customer name cannot be empty.");
        }

        if (nights <= 0) {
            throw new InvalidBookingException("Nights must be greater than zero.");
        }

        inventory.validateRoomType(roomType);
        inventory.validateAvailability(roomType);
    }
}

// Booking Service
class BookingService {
    private RoomInventory inventory;
    private List<Reservation> bookingList;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.bookingList = new ArrayList<>();
    }

    public void createBooking(String bookingId, String customerName, String roomType, int nights) {

        try {
            BookingValidator.validate(customerName, roomType, nights, inventory);

            inventory.bookRoom(roomType);

            Reservation reservation = new Reservation(bookingId, customerName, roomType, nights);
            bookingList.add(reservation);

            System.out.println("Booking Successful -> " + reservation);

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed -> " + e.getMessage());
        }
    }

    public void showAllBookings() {
        System.out.println("\nConfirmed Bookings:");
        if (bookingList.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Reservation r : bookingList) {
            System.out.println(r);
        }
    }
}

// Main Class
public class BookMyApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);
        bookingService.createBooking("B001", "Alice", "Deluxe", 2);

        bookingService.createBooking("B002", "Bob", "Premium", 2);

        bookingService.createBooking("B003", "Charlie", "Standard", 0);

        bookingService.createBooking("B004", "David", "Suite", 1);

        bookingService.createBooking("B005", "Eve", "Suite", 1);

        bookingService.createBooking("B006", "", "Standard", 1);

        System.out.println();
        inventory.displayInventory();
        bookingService.showAllBookings();
    }
}