public class Room {

    private int roomNo;
    private int capacity;

    public Room(int roomNo, int capacity) {
        this.roomNo = roomNo;
        this.capacity = capacity;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public int getCapacity() {
        return capacity;
    }

    // one line of the rooms csv file
    public String toCSV() {
        return roomNo + "," + capacity;
    }

    // NumberFormatException is a kind of IllegalArgumentException,
    // so a non-numeric value in the file is also caught by the caller
    public static Room fromCSV(String line) {
        String[] parts = line.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("bad room record: " + line);
        }
        int roomNo = Integer.parseInt(parts[0].trim());
        int capacity = Integer.parseInt(parts[1].trim());
        return new Room(roomNo, capacity);
    }
}
