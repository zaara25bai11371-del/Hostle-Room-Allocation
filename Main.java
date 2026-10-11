import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static HostelManager hostel;

    public static void main(String[] args) {
        hostel = new HostelManager(new FileHandler("data/rooms.csv", "data/students.csv"));

        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    addRoom();
                    break;
                case 3:
                    allocateRoom();
                    break;
                case 4:
                    vacateRoom();
                    break;
                case 5:
                    viewRooms();
                    break;
                case 6:
                    viewAvailableRooms();
                    break;
                case 7:
                    viewStudents();
                    break;
                case 8:
                    searchStudent();
                    break;
                case 9:
                    running = false;
                    System.out.println("\nBye! All changes are saved in the data folder.");
                    break;
                default:
                    System.out.println("\nInvalid choice, please pick a number from 1 to 9.");
            }
        }
        sc.close();
    }

    private static void showMenu() {
        System.out.println("\n===== HOSTEL ROOM ALLOCATION SYSTEM =====");
        System.out.println("1. Add Student");
        System.out.println("2. Add Room");
        System.out.println("3. Allocate Room to Student");
        System.out.println("4. Vacate Student from Room");
        System.out.println("5. View All Rooms");
        System.out.println("6. View Available Rooms");
        System.out.println("7. View All Students");
        System.out.println("8. Search Student by Roll No");
        System.out.println("9. Exit");
    }

    // keeps asking until the user types a proper whole number
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    // ---------- menu actions ----------

    private static void addStudent() {
        System.out.println("\n--- Add Student ---");
        String rollNo = readText("Roll number: ");
        String name = readText("Name: ");
        try {
            hostel.addStudent(rollNo, name);
            System.out.println("Student added.");
        } catch (HostelException e) {
            System.out.println("Could not add student: " + e.getMessage());
        }
    }

    private static void addRoom() {
        System.out.println("\n--- Add Room ---");
        int roomNo = readInt("Room number: ");
        int capacity = readInt("Capacity (1-10): ");
        try {
            hostel.addRoom(roomNo, capacity);
            System.out.println("Room " + roomNo + " added.");
        } catch (HostelException e) {
            System.out.println("Could not add room: " + e.getMessage());
        }
    }

    private static void allocateRoom() {
        System.out.println("\n--- Allocate Room ---");
        String rollNo = readText("Student roll number: ");
        int roomNo = readInt("Room number: ");
        try {
            hostel.allocate(rollNo, roomNo);
            System.out.println("Done. Room " + roomNo + " allocated.");
        } catch (RoomFullException e) {
            // more specific exception first, then the general one
            System.out.println("Sorry! " + e.getMessage());
        } catch (HostelException e) {
            System.out.println("Could not allocate: " + e.getMessage());
        }
    }

    private static void vacateRoom() {
        System.out.println("\n--- Vacate Room ---");
        String rollNo = readText("Student roll number: ");
        try {
            hostel.vacate(rollNo);
            System.out.println("Student has been moved out of the room.");
        } catch (HostelException e) {
            System.out.println("Could not vacate: " + e.getMessage());
        }
    }

    private static void viewRooms() {
        System.out.println("\n--- All Rooms ---");
        List<Room> rooms = hostel.getRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms added yet.");
            return;
        }
        System.out.printf("%-8s %-10s %-10s %s%n", "Room", "Capacity", "Occupied", "Residents");
        System.out.println("-----------------------------------------------------------");
        for (Room r : rooms) {
            List<Student> inside = hostel.getStudentsInRoom(r.getRoomNo());
            String names = "";
            for (int i = 0; i < inside.size(); i++) {
                names += inside.get(i).getName();
                if (i < inside.size() - 1) {
                    names += ", ";
                }
            }
            if (names.isEmpty()) {
                names = "-";
            }
            System.out.printf("%-8d %-10d %-10d %s%n",
                    r.getRoomNo(), r.getCapacity(), inside.size(), names);
        }
    }

    private static void viewAvailableRooms() {
        System.out.println("\n--- Rooms With Free Beds ---");
        List<Room> free = hostel.getAvailableRooms();
        if (free.isEmpty()) {
            System.out.println("All rooms are full.");
            return;
        }
        System.out.printf("%-8s %s%n", "Room", "Free Beds");
        System.out.println("-------------------");
        for (Room r : free) {
            int freeBeds = r.getCapacity() - hostel.countOccupants(r.getRoomNo());
            System.out.printf("%-8d %d%n", r.getRoomNo(), freeBeds);
        }
    }

    private static void viewStudents() {
        System.out.println("\n--- All Students ---");
        List<Student> students = hostel.getStudents();
        if (students.isEmpty()) {
            System.out.println("No students added yet.");
            return;
        }
        System.out.printf("%-12s %-22s %s%n", "Roll No", "Name", "Room");
        System.out.println("----------------------------------------------");
        for (Student s : students) {
            String room = s.hasRoom() ? String.valueOf(s.getRoomNo()) : "Not allocated";
            System.out.printf("%-12s %-22s %s%n", s.getRollNo(), s.getName(), room);
        }
    }

    private static void searchStudent() {
        String rollNo = readText("\nEnter roll number to search: ");
        Student s = hostel.findStudent(rollNo);
        if (s == null) {
            System.out.println("No student found with roll number " + rollNo + ".");
            return;
        }
        System.out.println("Roll No : " + s.getRollNo());
        System.out.println("Name    : " + s.getName());
        System.out.println("Room    : " + (s.hasRoom() ? s.getRoomNo() : "Not allocated"));
    }
}
