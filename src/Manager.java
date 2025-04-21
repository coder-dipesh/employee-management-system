
public class Manager extends Employee {
    private int teamSize;
    private String projectName;

    public Manager(String employeeID, String employeeName, String department, String gender, String role, double salary,
                   int performanceRating, int teamSize, String projectName) {
        super(employeeID, employeeName, department, gender,role, salary, performanceRating);
        this.teamSize = teamSize;
        this.projectName = projectName;

    }

    //getter
    int getTeamSize(){
        return teamSize;
    }
    String getProjectName(){
        return projectName;
    }

    //setters
    public void setTeamSize(int teamSize){
        this.teamSize = teamSize; }

    public void setProjectName(String projectName) {
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
        System.out.println(String.format(
                "ID: %s | Name: %s | Department: %s | Gender: %s | Role: %s | Salary: $%.2f | Performance Rating: %d ⭐️ | Team Size: %d pp | Project: %s",
                getEmployeeID(), getEmployeeName(), getDepartment(), getGender(),
                getRole(), getBaseSalary(), getPerformanceRating(), teamSize, projectName
        ));

    }
}
