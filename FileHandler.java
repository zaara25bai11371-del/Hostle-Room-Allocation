import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Reads and writes the two csv files. Nothing else in the project touches files.
public class FileHandler {

    private final String roomsFile;
    private final String studentsFile;

    public FileHandler(String roomsFile, String studentsFile) {
        this.roomsFile = roomsFile;
        this.studentsFile = studentsFile;
    }

    public List<Room> loadRooms() {
        List<Room> rooms = new ArrayList<>();
        File file = new File(roomsFile);

        if (!file.exists()) {
            // first run: start with 5 rooms of 2 beds each so the menu is usable
            for (int no = 101; no <= 105; no++) {
                rooms.add(new Room(no, 2));
            }
            saveAll(rooms, new ArrayList<>());
            return rooms;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    if (line.startsWith("RoomNo")) {
                        continue;   // header row
                    }
                }
                if (line.trim().isEmpty()) {
                    continue;
                }
                try {
                    rooms.add(Room.fromCSV(line));
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping a bad line in " + roomsFile + ": " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read " + roomsFile + ": " + e.getMessage());
        }
        return rooms;
    }

    public List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        File file = new File(studentsFile);
        if (!file.exists()) {
            return students;   // nothing saved yet
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    if (line.startsWith("RollNo")) {
                        continue;
                    }
                }
                if (line.trim().isEmpty()) {
                    continue;
                }
                try {
                    students.add(Student.fromCSV(line));
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping a bad line in " + studentsFile + ": " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read " + studentsFile + ": " + e.getMessage());
        }
        return students;
    }

    // Rewrites both files completely. Called after every change.
    public void saveAll(List<Room> rooms, List<Student> students) {
        makeFolderIfNeeded(roomsFile);
        makeFolderIfNeeded(studentsFile);

        try (FileWriter fw = new FileWriter(roomsFile)) {
            fw.write("RoomNo,Capacity" + System.lineSeparator());
            for (Room r : rooms) {
                fw.write(r.toCSV() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Could not save " + roomsFile + ": " + e.getMessage());
        }

        try (FileWriter fw = new FileWriter(studentsFile)) {
            fw.write("RollNo,Name,RoomNo" + System.lineSeparator());
            for (Student s : students) {
                fw.write(s.toCSV() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Could not save " + studentsFile + ": " + e.getMessage());
        }
    }

    private void makeFolderIfNeeded(String path) {
        File parent = new File(path).getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }
}
