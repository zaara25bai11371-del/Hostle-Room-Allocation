public class Student {

    private String rollNo;
    private String name;
    private int roomNo;   // 0 means the student has no room yet

    public Student(String rollNo, String name, int roomNo) {
        this.rollNo = rollNo;
        this.name = name;
        this.roomNo = roomNo;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public boolean hasRoom() {
        return roomNo != 0;
    }

    public String toCSV() {
        // a comma inside the name would break the csv, so swap it for a space
        return rollNo + "," + name.replace(",", " ") + "," + roomNo;
    }

    public static Student fromCSV(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 3) {
            throw new IllegalArgumentException("bad student record: " + line);
        }
        int roomNo = Integer.parseInt(parts[2].trim());
        return new Student(parts[0].trim(), parts[1].trim(), roomNo);
    }
}
