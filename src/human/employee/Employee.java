package human.employee;

import human.Human;

import java.time.LocalDate;

public abstract class Employee extends Human {

    private static long idCounter = 0;

    private Long id;
    private String licenseNumber;
    private int yearsOfExperience;
    private boolean working;

    public Employee(String firstname, String surname,
                    String licenseNumber, int yearsOfExperience) {
        super(firstname, surname);
        this.id = ++idCounter;
        setFirstname(firstname);
        setSurname(surname);
        setLicenseNumber(licenseNumber);
        setYearsOfExperience(yearsOfExperience);
        this.working = false;
    }

    public Employee(String firstname, String surname, LocalDate dateOfBirth,
                    String licenseNumber, int yearsOfExperience) {
        super(firstname, surname,dateOfBirth);
        this.id = ++idCounter;
        setFirstname(firstname);
        setSurname(surname);
        setLicenseNumber(licenseNumber);
        setYearsOfExperience(yearsOfExperience);
        this.working = false;
    }

    public long getId() {
        return id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    private void setLicenseNumber(String licenseNumber) {
        if (licenseNumber == null || licenseNumber.isBlank()) {
            throw new IllegalArgumentException("License number can't be null or empty.");
        }
        this.licenseNumber = licenseNumber;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        if (yearsOfExperience < 0) {
            throw new IllegalArgumentException("Years of experience can't be negative.");
        }
        this.yearsOfExperience = yearsOfExperience;
    }

    public boolean isWorking() {
        return working;
    }

    protected void setWorking(boolean working){
        this.working = working;
    }
    protected abstract void startWorking();

    protected abstract void stopWorking();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee other)) return false;
        return this.licenseNumber.equals(other.licenseNumber);
    }

    @Override
    public int hashCode() {
        return licenseNumber.hashCode();
    }

    @Override
    public String toString() {
        return "Employee{%s %s, license=%s, experience=%d}"
                .formatted(getFirstname(), getSurname(),
                        this.licenseNumber, this.yearsOfExperience);
    }
}
