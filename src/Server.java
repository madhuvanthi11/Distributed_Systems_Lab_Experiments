import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;

public class Server {
    public static void main(String[] args) {
        try {
            // create RMI registry programmatically on port 1099
            LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry started on port 1099.");

            CalcServiceImpl service = new CalcServiceImpl();

            // Bind the remote object's stub in the registry with name "CalcService"
            String name = "CalcService";
            Naming.rebind(name, service);

            System.out.println("CalcService bound in registry. Server is ready.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}
