import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Implementation of the RoomService interface
 * Uses in-memory Map to store room information
 */
public class RoomServiceImpl extends UnicastRemoteObject implements RoomService {
    
    // In-memory storage using HashMap
    private Map<String, RoomInfo> roomDatabase;
    
    /**
     * Constructor initializes the in-memory database with sample data
     */
    public RoomServiceImpl() throws RemoteException {
        super();
        roomDatabase = new HashMap<>();
        initializeRoomData();
    }
    
    /**
     * Initialize sample room data
     * In a real system, this could be loaded from a file or configuration
     */
    private void initializeRoomData() {
        // Sample hostel rooms with occupants
        roomDatabase.put("101", new RoomInfo(
            "101",
            Arrays.asList("Rajesh Kumar", "Amit Sharma"),
            "+91-9876543210"
        ));
        
        roomDatabase.put("102", new RoomInfo(
            "102",
            Arrays.asList("Priya Patel", "Sneha Reddy"),
            "+91-9876543210"
        ));
        
        roomDatabase.put("103", new RoomInfo(
            "103",
            Arrays.asList("Arjun Singh"),
            "+91-9876543211"
        ));
        
        roomDatabase.put("201", new RoomInfo(
            "201",
            Arrays.asList("Kavya Iyer", "Meera Nair", "Divya Krishna"),
            "+91-9876543211"
        ));
        
        roomDatabase.put("202", new RoomInfo(
            "202",
            Arrays.asList("Vikram Rao", "Karthik Menon"),
            "+91-9876543212"
        ));
        
        roomDatabase.put("203", new RoomInfo(
            "203",
            Arrays.asList("Ananya Das"),
            "+91-9876543212"
        ));
        
        roomDatabase.put("301", new RoomInfo(
            "301",
            Arrays.asList("Rohan Desai", "Sanjay Verma"),
            "+91-9876543213"
        ));
        
        roomDatabase.put("302", new RoomInfo(
            "302",
            Arrays.asList("Neha Gupta", "Pooja Jain"),
            "+91-9876543213"
        ));
        
        System.out.println("Room database initialized with " + roomDatabase.size() + " rooms");
    }
    
    /**
     * Remote method to get room information
     */
    @Override
    public RoomInfo getRoomInfo(String roomNumber) throws RemoteException {
        System.out.println("Request received for room: " + roomNumber);
        
        RoomInfo info = roomDatabase.get(roomNumber);
        
        if (info == null) {
            System.out.println("Room not found: " + roomNumber);
            return null;
        }
        
        System.out.println("Room info retrieved successfully: " + roomNumber);
        return info;
    }
    
    /**
     * Remote method to get all room numbers
     */
    @Override
    public List<String> getAllRoomNumbers() throws RemoteException {
        System.out.println("Request received for all room numbers");
        List<String> rooms = new ArrayList<>(roomDatabase.keySet());
        Collections.sort(rooms);
        return rooms;
    }
}