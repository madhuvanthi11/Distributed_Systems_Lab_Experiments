import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.UnicastRemoteObject;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileServer extends UnicastRemoteObject implements FileService{
    public static void main(String[] args){
        try{
            new java.io.File("server_files").mkdir();
            FileServer server=new FileServer();
            Naming.rebind("rmi://localhost/FileService",server);
            System.out.println("Fle Service is running...");
        }
       
        catch(Exception e){
            System.out.println("Server error: "+e.getMessage());
        }
    }
}

