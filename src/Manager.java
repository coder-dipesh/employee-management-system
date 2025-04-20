
public class Manager extends Employee {
    private int teamSize;
    private String projectName;

    public Manager(String employeeID, String employeeName, String department, String gender, double salary,
                   int performanceRating, int teamSize, String projectName) {
        super(employeeID, employeeName, department, gender, salary, performanceRating);
        this.teamSize = teamSize;
        this.projectName = projectName;

    }

    @Override
    public double calculateBonus() {
        if (getPerformanceRating() >= 8) {
            evaluatePerformance();
            System.out.println("Congratulations! you have received 15% bonus.");
            return getBaseSalary() * 1.15; // 15% bonus
        }
        else if (getPerformanceRating() <= 3){
            evaluatePerformance();
            System.out.println("5% salary is deducted from your base salary.");
            return getBaseSalary() - (getBaseSalary() * 0.05);
        }
        else return getBaseSalary();
    }

    @Override
    public void displayEmployeeDetails() {
        super.displayEmployeeDetails();
        System.out.println("Team Size: " + teamSize+ " people.");
        System.out.println("Project: " + projectName);

    }
}
