import java.util.*;

abstract class Room {
    int roomNumber;
    boolean isBooked;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isBooked = false;
    }

    public abstract String getCategory();
    public abstract double getPrice();
}

class StandardRoom extends Room {
    public StandardRoom(int roomNumber) {
        super(roomNumber);
    }

    public String getCategory() {
        return "Standard";
    }

    public double getPrice() {
        return 1000.0;
    }
}

class DeluxeRoom extends Room {
    public DeluxeRoom(int roomNumber) {
        super(roomNumber);
    }

    public String getCategory() {
        return "Deluxe";
    }

    public double getPrice() {
        return 2000.0;
    }
}

class SuiteRoom extends Room {
    public SuiteRoom(int roomNumber) {
        super(roomNumber);
    }

    public String getCategory() {
        return "Suite";
    }

    public double getPrice() {
        return 3500.0;
    }
}

class Booking {
    String customerName;
    Room room;
    String date;

    public Booking(String customerName, Room room, String date) {
        this.customerName = customerName;
        this.room = room;
        this.date = date;
    }

    public String toString() {
        return "Name: " + customerName + " | Room: " + room.roomNumber + " | Type: " + room.getCategory() + " | Date: " + date;
    }
}

public class HotelReservationManagementSystem {
    static Scanner scanner = new Scanner(System.in);
    static Map<Integer, Room> roomMap = new HashMap<>();
    static List<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {
        initRooms();

        while (true) {
            System.out.println("\n--- Hotel Booking Menu ---");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Show All Bookings");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1: showAvailableRooms();
                        break;
                case 2: bookRoom(); 
                        break;
                case 3: cancelBooking(); 
                        break;
                case 4: showBookings();
                        break;
                case 5: System.out.println("Exiting....\n"); 
                        System.out.println("We Hope You Have A Great Stay!");
                        return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    static void initRooms() {
        for (int i = 1; i <= 10; i++) roomMap.put(i, new StandardRoom(i));
        for (int i = 11; i <= 15; i++) roomMap.put(i, new DeluxeRoom(i));
        for (int i = 16; i <= 18; i++) roomMap.put(i, new SuiteRoom(i));
    }

    static void showAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Room room : roomMap.values()) {
            if (!room.isBooked) {
                System.out.println("Room " + room.roomNumber + " - " + room.getCategory() + " - ₹" + room.getPrice());
            }
        }
    }

    static void bookRoom() {
        showAvailableRooms();
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter room number to book: ");
        int num = scanner.nextInt(); scanner.nextLine();

        Room room = roomMap.get(num);

        if (room != null && !room.isBooked) {
            room.isBooked = true;

            System.out.println("Simulating payment of ₹" + room.getPrice() + "...");
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            System.out.println("Payment successful!");

            System.out.print("Enter booking date (dd-mm-yyyy): ");
            String date = scanner.nextLine();

            bookings.add(new Booking(name, room, date));
            System.out.println("Booking confirmed!");
        } else {
            System.out.println("Room not available or invalid room number.");
        }
    }

    static void cancelBooking() {
        System.out.print("Enter your name to cancel booking: ");
        String name = scanner.nextLine();

        Iterator<Booking> it = bookings.iterator();
        boolean found = false;

        while (it.hasNext()) {
            Booking b = it.next();
            if (b.customerName.equalsIgnoreCase(name)) {
                b.room.isBooked = false;
                it.remove();
                System.out.println("Booking cancelled for Room " + b.room.roomNumber);
                found = true;
            }
        }

        if (!found) System.out.println("No booking found under that name.");
    }

    static void showBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No current bookings.");
        } else {
            System.out.println("--- All Bookings ---");
            for (Booking b : bookings) {
                System.out.println(b);
            }
        }
    }
}
