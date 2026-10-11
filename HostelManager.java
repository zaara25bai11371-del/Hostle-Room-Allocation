import java.util.ArrayList;
import java.util.List;

// All the hostel rules live here: adding rooms/students, allocating, vacating.
public class HostelManager {

    private final List<Room> rooms;
    private final List<Student> students;
    private final FileHandler fileHandler;

    public HostelManager(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.rooms = fileHandler.loadRooms();
        this.students = fileHandler.loadStudents();
    }

    public void addRoom(int roomNo, int capacity) throws HostelException {
        if (roomNo <= 0) {
            throw new HostelException("Room number must be positive.");
        }
        if (capacity < 1 || capacity > 10) {
            throw new HostelException("Capacity must be between 1 and 10.");
        }
        if (findRoom(roomNo) != null) {
            throw new HostelException("Room " + roomNo + " already exists.");
        }
        rooms.add(new Room(roomNo, capacity));
        save();
    }

    public void addStudent(String rollNo, String name) throws HostelException {
        if (rollNo == null || rollNo.trim().isEmpty()) {
            throw new HostelException("Roll number cannot be empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new HostelException("Name cannot be empty.");
        }
        if (findStudent(rollNo) != null) {
            throw new HostelException("A student with roll number " + rollNo.trim() + " already exists.");
        }
        students.add(new Student(rollNo.trim(), name.trim(), 0));
        save();
    }

    public void allocate(String rollNo, int roomNo) throws HostelException {
        Student student = findStudent(rollNo);
        if (student == null) {
            throw new HostelException("No student found with roll number " + rollNo + ".");
        }
        if (student.hasRoom()) {
            throw new HostelException(student.getName() + " is already in room " + student.getRoomNo()
                    + ". Vacate first if you want to move them.");
        }
        Room room = findRoom(roomNo);
        if (room == null) {
            throw new HostelException("Room " + roomNo + " does not exist.");
        }
        if (countOccupants(roomNo) >= room.getCapacity()) {
            throw new RoomFullException(roomNo);
        }
        student.setRoomNo(roomNo);
        save();
    }

    public void vacate(String rollNo) throws HostelException {
        Student student = findStudent(rollNo);
        if (student == null) {
            throw new HostelException("No student found with roll number " + rollNo + ".");
        }
        if (!student.hasRoom()) {
            throw new HostelException(student.getName() + " does not have a room.");
        }
        student.setRoomNo(0);
        save();
    }

    public Student findStudent(String rollNo) {
        if (rollNo == null) {
            return null;
        }
        for (Student s : students) {
            if (s.getRollNo().equalsIgnoreCase(rollNo.trim())) {
                return s;
            }
        }
        return null;
    }

    public Room findRoom(int roomNo) {
        for (Room r : rooms) {
            if (r.getRoomNo() == roomNo) {
                return r;
            }
        }
        return null;
    }

    public int countOccupants(int roomNo) {
        int count = 0;
        for (Student s : students) {
            if (s.getRoomNo() == roomNo) {
                count++;
            }
        }
        return count;
    }

    public List<Student> getStudentsInRoom(int roomNo) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getRoomNo() == roomNo) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Room> getAvailableRooms() {
        List<Room> result = new ArrayList<>();
        for (Room r : rooms) {
            if (countOccupants(r.getRoomNo()) < r.getCapacity()) {
                result.add(r);
            }
        }
        return result;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Student> getStudents() {
        return students;
    }

    private void save() {
        fileHandler.saveAll(rooms, students);
    }
}
