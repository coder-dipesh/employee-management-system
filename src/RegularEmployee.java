public class RegularEmployee extends Employee{
    private String jobTitle;
    private int yearsOfExperience;

    public RegularEmployee(String employeeID, String employeeName, String department, String gender,
                           double baseSalary, int performanceRating, String jobTitle, int yearsOfExperience) {
        super(employeeID, employeeName, department, gender, baseSalary, performanceRating);
        this.jobTitle = jobTitle;
        this.yearsOfExperience = yearsOfExperience;
    }


    @Override
    public double calculateBonus() {
        if (getPerformanceRating() >= 8) {
            evaluatePerformance();
            System.out.println("Congratulations! you have received 5% bonus.");
            return getBaseSalary() * 0.5; // 5% bonus
        }
        else if (getPerformanceRating() <= 3){
            evaluatePerformance();
            System.out.println("2% salary is deducted from your base salary.");
            return getBaseSalary() - (getBaseSalary() * 0.02);
        }
        else return getBaseSalary();
    }

    @Override
    public void displayEmployeeDetails() {
        super.displayEmployeeDetails();
        System.out.println("Job Title: " + jobTitle);
        System.out.println("Years Of Experience: " + yearsOfExperience);

    }


}
