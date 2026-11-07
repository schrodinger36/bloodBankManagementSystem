
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// ABSTRACTION: Abstract parent class
abstract class Person {

    private int id;
    private String name;
    private int age;
    private String phone;

    // Constructor
    public Person(int id, String name, int age, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    // ENCAPSULATION: Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    // Abstract method
    public abstract void displayDetails();
}


// INHERITANCE: Donor inherits Person
class Donor extends Person {

    private String bloodGroup;

    private static int donorCounter = 101;

    public Donor(String name, int age, String phone, String bloodGroup) {
        super(donorCounter++, name, age, phone);
        this.bloodGroup = bloodGroup;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    // POLYMORPHISM: Method overriding
    @Override
    public void displayDetails() {
        System.out.println("Donor ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Phone: " + getPhone());
        System.out.println("Blood Group: " + bloodGroup);
    }
}


// INHERITANCE: Recipient also inherits Person
class Recipient extends Person {

    private static int recipientCounter = 201;

    public Recipient(String name, int age, String phone) {
        super(recipientCounter++, name, age, phone);
    }

    // POLYMORPHISM: Method overriding
    @Override
    public void displayDetails() {
        System.out.println("Recipient ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Phone: " + getPhone());
    }
}


// ABSTRACTION: Interface
interface BloodOperations {

    void addBloodStock(String group, int units);

    void viewBloodStock();

    boolean issueBlood(String group, int units);
}


// Service class implements interface
class BloodBankService implements BloodOperations {

    private ArrayList<Donor> donors = new ArrayList<>();

    private ArrayList<Recipient> recipients = new ArrayList<>();

    private HashMap<String, Integer> bloodStock = new HashMap<>();

    public BloodBankService() {

        String[] groups = {
            "A+", "A-", "B+", "B-",
            "AB+", "AB-", "O+", "O-"
        };

        for (String group : groups) {
            bloodStock.put(group, 0);
        }
    }

    // METHOD OVERLOADING: Version 1
    public void registerDonor(Donor donor) {
        donors.add(donor);
        System.out.println("Donor registered successfully!");
    }

    // METHOD OVERLOADING: Version 2
    public void registerDonor(String name, int age,
                              String phone, String group) {

        Donor donor = new Donor(name, age, phone, group);
        registerDonor(donor);
    }

    public void viewDonors() {

        if (donors.isEmpty()) {
            System.out.println("No donors registered.");
            return;
        }

        for (Donor donor : donors) {

            // POLYMORPHISM
            Person person = donor;
            person.displayDetails();

            System.out.println("----------------------");
        }
    }

    public void searchDonor(int id) {

        for (Donor donor : donors) {

            if (donor.getId() == id) {
                donor.displayDetails();
                return;
            }
        }

        System.out.println("Donor not found.");
    }

    public void registerRecipient(String name, int age, String phone) {

        Recipient recipient = new Recipient(name, age, phone);

        recipients.add(recipient);

        System.out.println("Recipient registered successfully!");
        System.out.println("Recipient ID: " + recipient.getId());
    }

    public void viewRecipients() {

        if (recipients.isEmpty()) {
            System.out.println("No recipients registered.");
            return;
        }

        for (Recipient recipient : recipients) {
            Person person = recipient;
            person.displayDetails();
            System.out.println("----------------------");
        }
    }

    // INTERFACE METHOD IMPLEMENTATION
    @Override
    public void addBloodStock(String group, int units) {

        group = group.toUpperCase();

        if (!bloodStock.containsKey(group)) {
            System.out.println("Invalid blood group.");
            return;
        }

        if (units <= 0) {
            System.out.println("Units must be greater than zero.");
            return;
        }

        bloodStock.put(group, bloodStock.get(group) + units);

        System.out.println("Blood stock added successfully!");
    }

    @Override
    public void viewBloodStock() {

        System.out.println("\n----- BLOOD INVENTORY -----");

        for (Map.Entry<String, Integer> entry : bloodStock.entrySet()) {
            System.out.println(entry.getKey() + " : "
                    + entry.getValue() + " units");
        }
    }

    @Override
    public boolean issueBlood(String group, int units) {

        group = group.toUpperCase();

        if (!bloodStock.containsKey(group)) {
            System.out.println("Invalid blood group.");
            return false;
        }

        if (units <= 0) {
            System.out.println("Invalid quantity.");
            return false;
        }

        int available = bloodStock.get(group);

        if (units > available) {
            System.out.println("Insufficient blood stock.");
            return false;
        }

        bloodStock.put(group, available - units);

        System.out.println("Blood issued successfully!");
        System.out.println("Issued: " + units);
        System.out.println("Remaining: " + bloodStock.get(group));

        return true;
    }
}


// MAIN APPLICATION CLASS
public class BloodBankApplication {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BloodBankService service = new BloodBankService();

        int choice;

        do {

            System.out.println("\n================================");
            System.out.println("    BLOOD BANK MANAGEMENT");
            System.out.println("================================");

            System.out.println("1. Register Donor");
            System.out.println("2. View All Donors");
            System.out.println("3. Search Donor");
            System.out.println("4. Register Recipient");
            System.out.println("5. View Recipients");
            System.out.println("6. Add Blood Stock");
            System.out.println("7. View Blood Stock");
            System.out.println("8. Issue Blood");
            System.out.println("9. Exit");

            System.out.print("Enter your choice: ");

            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:

                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter age: ");
                    int age = Integer.parseInt(sc.nextLine());

                    System.out.print("Enter phone: ");
                    String phone = sc.nextLine();

                    System.out.print("Enter blood group: ");
                    String group = sc.nextLine().toUpperCase();

                    service.registerDonor(name, age, phone, group);

                    break;

                case 2:

                    service.viewDonors();

                    break;

                case 3:

                    System.out.print("Enter donor ID: ");
                    int id = Integer.parseInt(sc.nextLine());

                    service.searchDonor(id);

                    break;

                case 4:

                    System.out.print("Enter recipient name: ");
                    String rName = sc.nextLine();

                    System.out.print("Enter age: ");
                    int rAge = Integer.parseInt(sc.nextLine());

                    System.out.print("Enter phone: ");
                    String rPhone = sc.nextLine();

                    service.registerRecipient(rName, rAge, rPhone);

                    break;

                case 5:

                    service.viewRecipients();

                    break;

                case 6:

                    System.out.print("Enter blood group: ");
                    String addGroup = sc.nextLine();

                    System.out.print("Enter units: ");
                    int units = Integer.parseInt(sc.nextLine());

                    service.addBloodStock(addGroup, units);

                    break;

                case 7:

                    service.viewBloodStock();

                    break;

                case 8:

                    System.out.print("Enter blood group: ");
                    String issueGroup = sc.nextLine();

                    System.out.print("Enter required units: ");
                    int required = Integer.parseInt(sc.nextLine());

                    service.issueBlood(issueGroup, required);

                    break;

                case 9:

                    System.out.println("Thank you for using Blood Bank Management System!");

                    break;

                default:

                    System.out.println("Invalid choice!");
            }

        } while (choice != 9);

        sc.close();
    }
}
