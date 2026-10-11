// General exception for anything that goes wrong while managing the hostel
// (unknown roll number, duplicate room, bad input, etc.)
public class HostelException extends Exception {

    public HostelException(String message) {
        super(message);
    }
}
