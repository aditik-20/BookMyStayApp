import java.util.LinkedList;
import java.util.Queue;

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

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Room Type: " + roomType;
    }
}

public class BookMyApp {

    public static void main(String[] args) {

        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Alice", "Deluxe"));
        bookingQueue.add(new Reservation("Bob", "Standard"));
        bookingQueue.add(new Reservation("Charlie", "Suite"));

        System.out.println("Booking requests received (in order):\n");

        for (Reservation r : bookingQueue) {
            System.out.println(r);
        }

        System.out.println("\nProcessing order (FIFO):\n");

        while (!bookingQueue.isEmpty()) {
            Reservation request = bookingQueue.poll();
            System.out.println("Processing request -> " + request);
        }
    }
}