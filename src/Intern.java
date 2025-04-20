public class Intern extends Employee{
    String universityName;
    int internshipDuration;

    public Intern(String employeeID, String employeeName, String department, String gender, double baseSalary, int performanceRating, String universityName, int internshipDuration) {
        super(employeeID, employeeName, department, gender, baseSalary, performanceRating);
        this.universityName = universityName;
        this.internshipDuration = internshipDuration;
    }

    @Override
    public double calculateBonus() {
        return 0;
    }

    @Override
    public void displayEmployeeDetails() {
        super.displayEmployeeDetails();
        System.out.println("University Name: " + universityName);
        System.out.println("Internship Duration(in months): " + internshipDuration);

    }
}
