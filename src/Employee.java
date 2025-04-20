
abstract class Employee {
    private String employeeID;
    private String employeeName;
    private String department;
    private String gender;
    private double baseSalary;
    private int performanceRating;

    //constructor
    public Employee(String employeeID, String employeeName, String department, String gender, double baseSalary, int performanceRating) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.department = department;
        this.gender = gender;
        this.baseSalary = baseSalary;
        this.performanceRating = performanceRating;
    }

    //getters
    public String getEmployeeID() {
        return employeeID; }

    public String getEmployeeName() {
        return employeeName; }

    public String getGender(){
        return gender;}

    public String getDepartment() {
        return department; }

    public double getBaseSalary() {
        return baseSalary; }

    public int getPerformanceRating() {
        return performanceRating; }

    //setters
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName; }

    public void setDepartment(String department) {
        this.department = department; }

    public void setGender(String gender){
        this.gender = gender; }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary; }

    public void setPerformanceRating(int performanceRating) {
        this.performanceRating = performanceRating; }


    //abstract method to calculate bonus
    public abstract double calculateBonus();

    // Display default info of employee
    public void displayEmployeeDetails() {
        System.out.println("Employee ID: " + employeeID);
        System.out.println("Employee Name: " + employeeName);
        System.out.println("Department: " + department);
        System.out.println("Gender: " + gender);
        System.out.println("Base Salary: $" + baseSalary);
        System.out.println("Performance Rating: " + performanceRating +" ⭐️");
    }


    // evaluatePerformance
    public void evaluatePerformance() {
        if (performanceRating <= 3) {
            System.out.println("Warning Letter Issued.");
        } else if (performanceRating >= 8) {
            System.out.println("Appreciation Letter Issued.");
        } else {
            System.out.println("Performance is Satisfactory.");
        }
    }
}
