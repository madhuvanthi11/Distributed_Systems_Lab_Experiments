import java.rmi.Naming;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            String name = "rmi://localhost/CalcService";
            CalcService service = (CalcService) Naming.lookup(name);

            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("\n=== Choose Service ===");
                System.out.println("1. Tax Calculation");
                System.out.println("2. CGPA Calculation");
                System.out.println("3. Voting Age Validation");
                System.out.println("4. Exit");
                System.out.print("Enter choice (1-4): ");
                int choice = Integer.parseInt(sc.nextLine().trim());

                if (choice == 1) {
                    System.out.print("Enter income (numeric): ");
                    double income = Double.parseDouble(sc.nextLine().trim());
                    double tax = service.calculateTax(income);
                    System.out.printf("Calculated tax for income %.2f = %.2f%n", income, tax);

                } else if (choice == 2) {
                    System.out.println("Enter marks for 5 subjects (0-100). Press Enter after each:");
                    int[] marks = new int[5];
                    for (int i = 0; i < 5; i++) {
                        System.out.printf("Subject %d: ", i + 1);
                        marks[i] = Integer.parseInt(sc.nextLine().trim());
                    }
                    double cgpa = service.calculateCGPA(marks);
                    System.out.printf("CGPA = %.2f%n", cgpa);

                } else if (choice == 3) {
                    System.out.print("Enter age (years): ");
                    int age = Integer.parseInt(sc.nextLine().trim());
                    boolean eligible = service.isEligibleToVote(age);
                    if (eligible) System.out.println("Eligible to vote.");
                    else System.out.println("Not eligible to vote.");

                } else if (choice == 4) {
                    System.out.println("Exiting client.");
                    break;
                } else {
                    System.out.println("Invalid choice, try again.");
                }
            }
            sc.close();

        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }
}
