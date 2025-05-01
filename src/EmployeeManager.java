import java.io.*;
import java.util.*;

public class EmployeeManager {
    private final LinkedList<Employee> employees = new LinkedList<>();
    private final Queue<String> recentActions = new LinkedList<>();
    private boolean isModified = false;

    //total number of employees
    public int getEmployeeCount() {
        return employees.size();
    }

    // load file
    public void loadFromFile(String filePath) {
        employees.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String readLine;

            while ((readLine = br.readLine()) != null) {
                // skip first line that contains header
                if (readLine.toLowerCase().contains("id")) {
                    continue;
                }
                String[] parts = readLine.split("\\|");
                if (parts.length >= 9) {
                    String id = parts[0];
                    String name = parts[1];
                    String dept = parts[2];
                    String gender = parts[3];
                    String role = parts[4];
                    double baseSalary = Double.parseDouble(parts[5]);
                    int performanceRating = Integer.parseInt(parts[6]);
                    String roleBased1 = parts[7];
                    String roleBased2 = parts[8];

                    Employee employee = null;
                    switch (role.toLowerCase()) {
                        case "manager" -> employee = new Manager(id, name, dept, gender, role, baseSalary, performanceRating,
                                Integer.parseInt(roleBased1), roleBased2);
                        case "regular" -> employee = new RegularEmployee(id, name, dept, gender, role, baseSalary, performanceRating,
                                roleBased1, Integer.parseInt(roleBased2));
                        case "intern" -> employee = new Intern(id, name, dept, gender, role, baseSalary, performanceRating,
                                roleBased1, Integer.parseInt(roleBased2));
                    }
                    if (employee != null) employees.add(employee);

                }
            }
            System.out.println("Loaded from " + filePath);
            recentActions.add("File Loaded from " + filePath);

        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
            recentActions.add("Error loading from file: " + e.getMessage());

        } catch (NumberFormatException e) {
            System.out.println("Data format error: " + e.getMessage());
            recentActions.add("Data format error: " + e.getMessage());
        }
    }

    // save data to file
    public void saveToFile(String filePath) {
        /* to prevent file overwrite by unchanged */
        if (!isModified) {
            System.out.println( UIEnhancement.RED + "---------------------------------------");
            System.out.println("No changes detected. File not updated.");
            System.out.println("---------------------------------------" +UIEnhancement.RESET);

            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Employee employee : employees) {
                StringBuilder sb = new StringBuilder();
                sb.append(employee.getEmployeeID()).append("|")
                        .append(employee.getEmployeeName()).append("|")
                        .append(employee.getDepartment()).append("|")
                        .append(employee.getGender()).append("|")
                        .append(employee.getRole()).append("|")
                        .append(employee.getBaseSalary()).append("|")
                        .append(employee.getPerformanceRating()).append("|");

                if (employee instanceof Manager manager) {
                    sb.append(manager.getTeamSize()).append("|").append(manager.getProjectName());
                } else if (employee instanceof RegularEmployee regularEmployee) {
                    sb.append(regularEmployee.getJobTitle()).append("|").append(regularEmployee.getYearsOfExperience());
                } else if (employee instanceof Intern intern) {
                    sb.append(intern.getUniversityName()).append("|").append(intern.getInternshipDuration());
                }

                bw.write(sb.toString());
                bw.newLine();
            }
            System.out.println("File saved to " + filePath);
            UIEnhancement.progressBar("💾 Saving Changes", 20);

            recentActions.add("File saved to " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    // add employee
    public void addEmployee(Employee employee) {
        employees.add(employee);
        isModified = true;
        recentActions.add("New employee added: " + employee.getEmployeeID());
    }

    // delete employee
    public void deleteEmployee(String id) {
        employees.removeIf(employee -> employee.getEmployeeID().equalsIgnoreCase(id));
        isModified = true;
        recentActions.add("Deleted employee: " + id);
    }

    // update employee
    public void updateEmployee(String id, Scanner sc) {
        for (Employee employee : employees) {
            if (employee.getEmployeeID().equalsIgnoreCase(id)) {
                System.out.println("Please press 'ENTER' to skip field that you don't want to change.");
                System.out.print("Enter Employee Name: ");
                String newName = sc.nextLine();
                System.out.print("Enter Department: ");
                String newDepartment = sc.nextLine();
                System.out.print("Enter Role: ");
                String newRole = sc.nextLine();
                System.out.print("Enter New Salary: ");
                String newSalary = sc.nextLine();
                System.out.print("Enter new performance rating: ");
                String newPerformanceRating = sc.nextLine();
                if (!newName.trim().isEmpty()) {
                    employee.setEmployeeName(newName);
                    isModified = true;

                }
                if (!newDepartment.trim().isEmpty()) {
                    employee.setDepartment(newDepartment);
                    isModified = true;

                }
                if (!newRole.trim().isEmpty()) {
                    employee.setRole(newRole);
                    isModified = true;

                }
                if (!newSalary.trim().isEmpty()) {
                    try {
                        double salary = Double.parseDouble(newSalary);
                        employee.setBaseSalary(salary);
                        isModified = true;

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid salary. Skipping update for salary.");
                    }
                }
                if (!newPerformanceRating.trim().isEmpty()) {
                    try {
                        int newRating = Integer.parseInt(newPerformanceRating);
                        employee.setPerformanceRating(newRating);
                        isModified = true;

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid rating. Skipping update for performance rating.");
                    }
                }

                switch (employee) {
                    case Manager manager -> {
                        System.out.print("Enter new team size: ");
                        String teamSize = sc.nextLine();
                        System.out.print("Enter new project name: ");
                        String projectName = sc.nextLine();
                        if (!teamSize.trim().isEmpty()) {
                            manager.setTeamSize(Integer.parseInt(teamSize)) ;
                            isModified = true;

                        }
                        if (!projectName.trim().isEmpty()) {
                            manager.setProjectName(projectName)  ;
                            isModified = true;

                        }
                    }
                    case RegularEmployee regularEmployee -> {
                        System.out.print("Enter new job title: ");
                        String jobTitle = sc.nextLine();
                        System.out.print("Enter new years of experience: ");
                        String experience = sc.nextLine();
                        sc.nextLine();
                        if (!jobTitle.trim().isEmpty()) {
                            regularEmployee.setJobTitle(jobTitle);
                            isModified = true;

                        }
                        if (!experience.trim().isEmpty()) {
                            regularEmployee.setYearsOfExperience(Integer.parseInt(experience));
                            isModified = true;

                        }
                    }
                    case Intern intern -> {
                        System.out.print("Enter new university name: ");
                        String university = sc.nextLine();
                        System.out.print("Enter new internship duration (months): ");
                        String duration = sc.nextLine();
                        sc.nextLine();
                        if (!university.trim().isEmpty()) {
                            intern.setUniversityName(university);
                            isModified = true;

                        }
                        if (!duration.trim().isEmpty()) {
                            intern.setInternshipDuration(Integer.parseInt(duration));
                            isModified = true;

                        }

                    }
                    default -> {
                    }
                }

                recentActions.add("Updated employee: " + id);
                return;
            }
        }
        System.out.println("No employee found with that ID.");
    }

    // view all employees
    public void viewAllEmployees() {
        for (Employee employee : employees) {
            employee.displayEmployeeDetails();
        }
        System.out.println("-----------------------------------------------------");
        System.out.println("Total " + employees.size() + " result found.");
        System.out.println("-----------------------------------------------------");
        recentActions.add("Displayed all employees data.");
    }

    // search employee by id
    // return employee details with matching id else show error
    public Employee searchById(String id) {
        for (Employee employee : employees) {
            if (employee.getEmployeeID().equalsIgnoreCase(id)) {
                employee.displayEmployeeDetails();
                recentActions.add( "'" + id + "'" + "  searched, using search by id.");
                return employee;
            }
        }
        return null;
    }

    // search employee by name
    // return list of employee with same name
    public List<Employee> searchByName(String name) {
        List<Employee> searchByNameResult = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getEmployeeName().toLowerCase().contains(name.toLowerCase())) {
                searchByNameResult.add(employee);
            }
        }
        recentActions.add("'" + name + "'"  + " employee searched, using search by name.");
        return searchByNameResult;

    }

    // search employee by performance rating
    // return list of employee matching the search data
    public List<Employee> searchByPerformanceRating(int minRating) {
        List<Employee> searchByPerformanceRatingResult = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getPerformanceRating() == minRating) {
                searchByPerformanceRatingResult.add(employee);
            }
        }
        recentActions.add("Employee searched, using search by performance.");
        return searchByPerformanceRatingResult;
    }

    // display every action done by admin
    public void recentActions() {
        System.out.println(UIEnhancement.GREEN + "\n----- Admin Recent Actions -----" + UIEnhancement.RESET);
        if (recentActions.isEmpty()) {
            System.out.println("----- No recent actions. ------");
        } else {
            for (String action : recentActions) {
                System.out.println(UIEnhancement.CYAN + action + UIEnhancement.RESET );
            }
        }
        System.out.println( UIEnhancement.GREEN + "----- Admin Recent Actions -----"+ UIEnhancement.RESET);

    }

    public void performanceSalaryActions(String id){

        Employee e = searchById(id);
        if (e != null) {
            System.out.println("\nBase Salary: $" + e.getBaseSalary());

            UIEnhancement.progressBar("Evaluating performance... ", 35);

            e.evaluatePerformance();
            double finalPay = e.calculateBonus();
            System.out.println("Final Salary after bonus/fine: $" + finalPay);
            recentActions.add("Performance evaluated for: " + id);
        } else {
            System.out.println("Employee not found.");
        }
    }


}



