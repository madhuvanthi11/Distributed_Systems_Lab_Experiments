//remote interface

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalcService extends Remote {
    /**
     * Calculate tax according to:
     * ≤ 250000 -> 0
     * 250001 - 500000 -> 5% on income (as a value)
     * > 500000 -> 10% on income (as a value)
     *
     * @param income Income in rupees
     * @return tax amount (in rupees)
     */
    double calculateTax(double income) throws RemoteException;

    /**
     * Calculate CGPA from marks of 5 subjects.
     * CGPA = (totalMarks / maxMarks) * 10, where maxMarks = 500.
     *
     * @param marks An int array of length 5 containing marks (0-100)
     * @return CGPA as double
     */
    double calculateCGPA(int[] marks) throws RemoteException;

    /**
     * Check voting eligibility: age >= 18 -> true
     *
     * @param age age in years
     * @return true if eligible, otherwise false
     */
    boolean isEligibleToVote(int age) throws RemoteException;
}
