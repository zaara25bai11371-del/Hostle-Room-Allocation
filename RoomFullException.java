// Thrown when we try to put a student in a room that has no free bed.
// It extends HostelException so Main can catch it separately if it wants to.
public class RoomFullException extends HostelException {

    public RoomFullException(int roomNo) {
        super("Room " + roomNo + " is already full.");
    }
}
