import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Employee implements Serializable {
    private int id;
    private String name;
    private String position;
    private double salary;

    public Employee(int id, String name, String position, double salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}

class EmployeeManagementSystem {
    private ArrayList<Employee> employees;
    private static final String FILE_NAME = "employees.ser";

    public EmployeeManagementSystem() {
        employees = new ArrayList<>();
        loadEmployees();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        saveEmployees();
        System.out.println("Employee added successfully.");
    }

    public void viewAllEmployees() {
        for (Employee employee : employees) {
            System.out.println(employee.getId() + " " + employee.getName() + " " + employee.getPosition() + " "
                    + employee.getSalary());
        }
    }

    public void updateEmployee(int id, String name, String position, double salary) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employee.setName(name);
                employee.setPosition(position);
                employee.setSalary(salary);
                saveEmployees();
                System.out.println("Employee information updated successfully.");
                return;
            }
        }
        System.out.println("Employee not found.");
    }

    public void deleteEmployee(int id) {
        employees.removeIf(employee -> employee.getId() == id);
        saveEmployees();
        System.out.println("Employee deleted successfully.");
    }

    public Employee findEmployee(String query) {
        for (Employee employee : employees) {
            if (String.valueOf(employee.getId()).equals(query) ||
                    employee.getName().equalsIgnoreCase(query) ||
                    employee.getPosition().equalsIgnoreCase(query)) {
                return employee;
            }
        }
        return null;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    private void saveEmployees() {
        try {
            FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(employees);
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Error saving employees: " + e.getMessage());
        }
    }

    private void loadEmployees() {
        try {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                FileInputStream fileIn = new FileInputStream(FILE_NAME);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                employees = (ArrayList<Employee>) objectIn.readObject();
                objectIn.close();
                fileIn.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading employees: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeManagementSystem system = new EmployeeManagementSystem();

        while (true) {
            System.out.println("Employee Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Search Employee");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter employee name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter employee position: ");
                    String position = scanner.nextLine();
                    double salary;
                    do {
                        System.out.print("Enter employee salary: ");
                        while (!scanner.hasNextDouble()) {
                            System.out.println("Invalid input! Please enter a number.");
                            System.out.print("Enter employee salary: ");
                            scanner.next();
                        }
                        salary = scanner.nextDouble();
                        if (salary <= 0) {
                            System.out.println("Salary must be a positive number.");
                        }
                    } while (salary <= 0);
                    scanner.nextLine();
                    system.addEmployee(new Employee(system.getEmployees().size() + 1, name, position, salary));
                    break;
                case 2:
                    system.viewAllEmployees();
                    break;
                case 3:
                    System.out.print("Enter employee ID to update: ");
                    int idToUpdate = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new position: ");
                    String newPosition = scanner.nextLine();
                    double newSalary;
                    do {
                        System.out.print("Enter new salary: ");
                        while (!scanner.hasNextDouble()) {
                            System.out.println("Invalid input! Please enter a number.");
                            System.out.print("Enter new salary: ");
                            scanner.next();
                        }
                        newSalary = scanner.nextDouble();
                        if (newSalary <= 0) {
                            System.out.println("Salary must be a positive number.");
                        }
                    } while (newSalary <= 0);
                    scanner.nextLine();
                    system.updateEmployee(idToUpdate, newName, newPosition, newSalary);
                    break;
                case 4:
                    System.out.print("Enter employee ID to delete: ");
                    int idToDelete = scanner.nextInt();
                    system.deleteEmployee(idToDelete);
                    break;
                case 5:
                    System.out.print("Enter search query (ID, name, or position): ");
                    String query = scanner.nextLine();
                    Employee foundEmployee = system.findEmployee(query);
                    if (foundEmployee != null) {
                        System.out.println("Employee found:");
                        System.out.println(foundEmployee.getId() + " " + foundEmployee.getName() + " " +
                                foundEmployee.getPosition() + " " + foundEmployee.getSalary());
                    } else {
                        System.out.println("No employee found matching the query.");
                    }
                    break;
                case 6:
                    System.out.println("Exiting program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
