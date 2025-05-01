import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime; // Add this import
import java.time.format.DateTimeFormatter; // Add this import

public class TextBasedCLI {
    EmployeeManager employeeManager = new EmployeeManager();
    Scanner sc = new Scanner(System.in);
    int choice;

    public void userMenu() {
        do {
            System.out.println(UIEnhancement.CYAN + "\n=============================================================");
            System.out.println("                 EMPLOYEE MANAGEMENT SYSTEM                 ");
            System.out.println("=============================================================" + UIEnhancement.RESET);

            System.out.println(UIEnhancement.YELLOW + ">> MENU OPTIONS <<" + UIEnhancement.RESET);
            System.out.println("1.  Load Employees");
            System.out.println("2.  View All Employees");
            System.out.println("3.  Add New Employee");
            System.out.println("4.  Search by Name");
            System.out.println("5.  Search by Performance Rating");
            System.out.println("6.  Search by ID");
            System.out.println("7.  Update Employee");
            System.out.println("8.  Delete Employee");
            System.out.println("9.  Performance & Salary Actions");
            System.out.println("10.  View Recent Actions");
            System.out.println("11.  Save Changes");
            System.out.println("12.  Exit");

            // user input validation
            while (true) {
                System.out.print("\nEnter your choice (1–12): ");
                String input = sc.nextLine();
                try {
                    choice = Integer.parseInt(input);
                    if (choice >= 1 && choice <= 12) break;
                    else System.out.println(UIEnhancement.RED + "❌ Please enter a number between 1 and 12." + UIEnhancement.RESET);
                } catch (NumberFormatException e) {
                    System.out.println(UIEnhancement.RED + "❌ Invalid input. Enter a valid number." + UIEnhancement.RESET);
                }
            }

            String filePath = "data/employee_data.txt";
            switch (choice) {
                case 1 -> {
                    UIEnhancement.progressBar("\uD83D\uDCC2 Loading Employees Data", 30);
                    employeeManager.loadFromFile(filePath);
                }

                case 2 -> {
                    employeeManager.viewAllEmployees();
                    System.out.println(UIEnhancement.GREEN + "-----------------------------------------------------");
                    System.out.println("Total Employees: " + employeeManager.getEmployeeCount());
                    System.out.println("-----------------------------------------------------" + UIEnhancement.RESET);
                }

                case 3 -> {
                    System.out.print("Enter Role (Manager/Regular/Intern): ");
                    String role = sc.nextLine();
                    System.out.print("Enter ID: ");
                    String id = sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();
                    System.out.print("Enter Gender: ");
                    String gender = sc.nextLine();
                    System.out.print("Enter Base Salary: ");
                    double salary = sc.nextDouble();
                    System.out.print("Enter Performance Rating: ");
                    int rating = sc.nextInt();
                    sc.nextLine();

                    Employee emp = null;
                    if (role.equalsIgnoreCase("Manager")) {
                        System.out.print("Enter Team Size: ");
                        int teamSize = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Project Name: ");
                        String project = sc.nextLine();
                        emp = new Manager(id, name, dept, gender, role, salary, rating, teamSize, project);
                    } else if (role.equalsIgnoreCase("Regular")) {
                        System.out.print("Enter Job Title: ");
                        String title = sc.nextLine();
                        System.out.print("Enter Years of Experience: ");
                        int exp = sc.nextInt();
                        sc.nextLine();
                        emp = new RegularEmployee(id, name, dept, gender, role, salary, rating, title, exp);
                    } else if (role.equalsIgnoreCase("Intern")) {
                        System.out.print("Enter University Name: ");
                        String uni = sc.nextLine();
                        System.out.print("Enter Internship Duration (months): ");
                        int duration = sc.nextInt();
                        sc.nextLine();
                        emp = new Intern(id, name, dept, gender, role, salary, rating, uni, duration);
                    }
                    if (emp != null) {
                        employeeManager.addEmployee(emp);
                        System.out.println(UIEnhancement.GREEN + "✔ Employee added successfully!" + UIEnhancement.RESET);
                    }
                }

                case 4 -> {
                    System.out.print("Enter name to search: ");
                    String name = sc.nextLine();
                    List<Employee> results = employeeManager.searchByName(name);
                    if (results != null && !results.isEmpty()) {
                        UIEnhancement.progressBar("Searching Employee by Name: ", 35);
                        for (Employee employee : results) {
                            employee.displayEmployeeDetails();
                        }
                        System.out.println(UIEnhancement.GREEN+"-----------------------------------------------------");
                        System.out.println("Total " + results.size() + " result(s) found for " + "'"+ name +"'");
                        System.out.println("-----------------------------------------------------"+UIEnhancement.RESET);
                    } else {
                        System.out.println(UIEnhancement.RED + "No employees found with that name." + UIEnhancement.RESET);
                    }
                }

                case 5 -> {
                    System.out.print("Enter minimum performance rating: ");
                    int rating = sc.nextInt();
                    sc.nextLine();
                    List<Employee> results = employeeManager.searchByPerformanceRating(rating);
                    if (results != null && !results.isEmpty()) {
                        UIEnhancement.progressBar("Searching Employee by Performance Rating: ", 35);

                        for (Employee e : results) e.displayEmployeeDetails();
                        System.out.println(UIEnhancement.GREEN+"-----------------------------------------------------");
                        System.out.println( "Total " + results.size() + " result(s) found for " + "'"+ rating +"'" );
                        System.out.println("-----------------------------------------------------"+UIEnhancement.RESET);
                    } else {
                        System.out.println(UIEnhancement.RED + "No employees found with that rating or higher." + UIEnhancement.RESET);
                    }
                }

                case 6 -> {
                    System.out.print("Enter ID to search: ");
                    String id = sc.nextLine();
                    employeeManager.searchById(id);

                }

                case 7 -> {
                    System.out.print("Enter ID: ");
                    String id = sc.nextLine();
                    employeeManager.updateEmployee(id, sc);
                }

                case 8 -> {
                    System.out.print("Enter ID to delete: ");
                    String id = sc.nextLine();
                    employeeManager.deleteEmployee(id);
                    System.out.println(UIEnhancement.GREEN + "✔ Employee deletion attempted for ID: " + id + UIEnhancement.RESET);
                }
                case 9 ->{
                    System.out.print("Enter employee ID to evaluate: ");
                    String id = sc.nextLine();
                    employeeManager.performanceSalaryActions(id);
                }

                case 10 -> employeeManager.recentActions();

                case 11 -> {

                    employeeManager.saveToFile(filePath);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String timestamp = LocalDateTime.now().format(formatter);
                    System.out.println(UIEnhancement.GREEN + "✔ Changes saved successfully at " + timestamp + UIEnhancement.RESET);


                }

                case 12 -> {
                    System.out.print("Are you sure you want to exit? (yes/no): ");
                    String confirm = sc.nextLine();
                    if (confirm.equalsIgnoreCase("yes")) {
                        System.out.println(UIEnhancement.GREEN + "\n👋 Exiting the system. Goodbye!" + UIEnhancement.RESET);
                    } else {
                        choice = -1; // Reset choice to avoid exiting
                    }
                }

            }
        } while (choice != 12);
        sc.close();
    }
}
