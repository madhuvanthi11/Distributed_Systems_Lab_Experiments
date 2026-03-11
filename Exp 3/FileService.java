import java.rmi.Remote;
import java.rmi.RemoteExpception;
public interface FileService extends Remote{
    void sendFule(String fileName, byte[] fileData)
throws RemoteException;
}