import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Remote interface for Room Service
 * Defines methods that can be invoked remotely
 */
public interface RoomService extends Remote {
    
    /**
     * Get room information by room number
     * @param roomNumber The room number to search
     * @return RoomInfo object containing room details
     * @throws RemoteException if remote communication fails
     */
    RoomInfo getRoomInfo(String roomNumber) throws RemoteException;
    
    /**
     * Get all available room numbers
     * @return List of all room numbers in the system
     * @throws RemoteException if remote communication fails
     */
    List<String> getAllRoomNumbers() throws RemoteException;
}