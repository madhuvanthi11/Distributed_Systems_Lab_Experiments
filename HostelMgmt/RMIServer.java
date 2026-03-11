import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RMI Server that hosts the Room Service
 */
public class RMIServer {
    
    public static void main(String[] args) {
        try {
            // Create the service implementation
            RoomServiceImpl service = new RoomServiceImpl();
            
            // Create RMI registry on port 1099 (default RMI port)
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Bind the service to the registry with name "RoomService"
            registry.rebind("RoomService", service);
            
            System.out.println("========================================");
            System.out.println("Hostel Room Information Service Started");
            System.out.println("========================================");
            System.out.println("Server is running on port 1099");
            System.out.println("Service name: RoomService");
            System.out.println("Waiting for client requests...");
            System.out.println("Press Ctrl+C to stop the server");
            System.out.println("========================================");
            
        } catch (Exception e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}