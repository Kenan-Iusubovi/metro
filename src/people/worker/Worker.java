package people.worker;

public class Worker {

    private static long idCounter = 0;
    private Long id;
    private String firstname;
    private String surname;
    private WorkerProfession profession;
    private String licenseNumber;
    private int yearsOfExperience;
    private boolean working;
    private char grade;

    public Worker(String firstname, String surname,
                  String licenseNumber, int yearsOfExperience,
                  WorkerProfession profession, char grade) {

        this.id = ++idCounter;
        setFirstname(firstname);
        setSurname(surname);
        setLicenseNumber(licenseNumber);
        setYearsOfExperience(yearsOfExperience);
        setProfession(profession);
        setWorking(false);
        setGrade(grade);
    }

    public long getId() {

        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        if (firstname == null || firstname.isBlank()) {
            throw new IllegalArgumentException("Firstname can't be empty.");
        }
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (surname == null || surname.isBlank()) {
            throw new IllegalArgumentException("Surname can't be empty.");
        }
        this.surname = surname;
    }

    public WorkerProfession getProfession() {
        return profession;
    }

    public void setProfession(WorkerProfession profession) {
        if (profession == null) {
            throw new IllegalArgumentException("Profession can't be null.");
        }
        this.profession = profession;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        if (licenseNumber == null || licenseNumber.isBlank()) {
            throw new IllegalArgumentException("License number can't be empty.");
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

    public void setWorking(boolean working) {
        this.working = working;
    }

    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = Character.toUpperCase(grade);
    }
}
