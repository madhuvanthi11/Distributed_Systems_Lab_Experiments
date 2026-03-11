//server-side implementation

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.Arrays;

public class CalcServiceImpl extends UnicastRemoteObject implements CalcService {

    protected CalcServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public double calculateTax(double income) throws RemoteException {
        if (income <= 250000) {
            return 0.0;
        } else if (income <= 500000) {
            return income * 0.05; // 5%
        } else {
            return income * 0.10; // 10%
        }
    }

    @Override
    public double calculateCGPA(int[] marks) throws RemoteException {
        if (marks == null || marks.length != 5) {
            throw new RemoteException("Marks array must have exactly 5 elements.");
        }
        int total = 0;
        for (int m : marks) {
            if (m < 0 || m > 100) {
                throw new RemoteException("Each mark must be between 0 and 100. Found: " + m);
            }
            total += m;
        }
        double cgpa = ((double) total / 500.0) * 10.0;
        // round to two decimal places for nicer display
        return Math.round(cgpa * 100.0) / 100.0;
    }

    @Override
    public boolean isEligibleToVote(int age) throws RemoteException {
        return age >= 18;
    }

    // Optional: for quick sanity logging
    @Override
    public String toString() {
        return "CalcServiceImpl";
    }
}
