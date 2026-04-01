import java.util.*;

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

    public String getBookingId() {
        return bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId +
                ", Customer: " + customerName +
                ", Room Type: " + roomType +
                ", Nights: " + nights;
    }
}

class BookingHistory {
    private List<Reservation> reservationList;

    public BookingHistory() {
        reservationList = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservationList.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(reservationList);
    }
}

class BookingReportService {

    public void generateReport(List<Reservation> reservations) {
        System.out.println("\n===== BOOKING REPORT =====");

        if (reservations.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        int totalBookings = reservations.size();
        int totalNights = 0;

        for (Reservation r : reservations) {
            totalNights += r.getNights();
        }

        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Nights Booked: " + totalNights);

        System.out.println("\n--- Booking Details ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }
}

public class BookMyApp {

    public static void main(String[] args) {

        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        Reservation r1 = new Reservation("B001", "Alice", "Deluxe", 2);
        Reservation r2 = new Reservation("B002", "Bob", "Suite", 3);
        Reservation r3 = new Reservation("B003", "Charlie", "Standard", 1);

        bookingHistory.addReservation(r1);
        bookingHistory.addReservation(r2);
        bookingHistory.addReservation(r3);
        List<Reservation> allBookings = bookingHistory.getAllReservations();

        reportService.generateReport(allBookings);
    }
}