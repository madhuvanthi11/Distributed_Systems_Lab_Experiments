import java.io.Serializable;
import java.util.List;

/**
 * Data model for room information
 * Implements Serializable to be transferred over RMI
 */
public class RoomInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String roomNumber;
    private List<String> occupantNames;
    private String wardenContact;
    
    public RoomInfo(String roomNumber, List<String> occupantNames, String wardenContact) {
        this.roomNumber = roomNumber;
        this.occupantNames = occupantNames;
        this.wardenContact = wardenContact;
    }
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
    public List<String> getOccupantNames() {
        return occupantNames;
    }
    
    public String getWardenContact() {
        return wardenContact;
    }
    
    @Override
    public String toString() {
        return "Room: " + roomNumber + 
               "\nOccupants: " + String.join(", ", occupantNames) +
               "\nWarden Contact: " + wardenContact;
    }
}