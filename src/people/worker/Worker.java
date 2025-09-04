package people.worker;

public class Worker {

    private static long idCounter = 0;

    private long id;

    private String firstname;

    private String surname;

    private WorkerProffessionE proffession;

    private String licenseNumber;

    private int yearsOfExperience;

    private boolean isWorking;

    public Worker(String firstname, String surname, String licenseNumber,
                  int yearsOfExperience, WorkerProffessionE proffession) {

        if (firstname == null || firstname.isBlank()){
            throw new IllegalArgumentException("Firstname can't be empty!");
        }
        if (surname == null || surname.isBlank()){
            throw new IllegalArgumentException("Surname can't be empty!");
        }
        if (licenseNumber == null || licenseNumber.isBlank()){
            throw new IllegalArgumentException("License number can't be empty!");
        }
        if (yearsOfExperience < 0 ){
            throw new IllegalArgumentException("Experience can't be negative!");
        }

        this.id = ++idCounter;
        this.firstname = firstname;
        this.surname = surname;
        this.licenseNumber = licenseNumber;
        this.yearsOfExperience = yearsOfExperience;
        this.proffession = proffession;
        this.isWorking = false;
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        if (firstname == null || firstname.isBlank()){
            throw new IllegalArgumentException("Firstname can't be empty!");
        }
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (surname == null || surname.isBlank()){
            throw new IllegalArgumentException("Surname can't be empty!");
        }
        this.surname = surname;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        if (licenseNumber == null || licenseNumber.isBlank()){
            throw new IllegalArgumentException("License number can't be empty!");
        }
        this.licenseNumber = licenseNumber;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        if (yearsOfExperience < 0 ){
            throw new IllegalArgumentException("Experience can't be negative!");
        }
        this.yearsOfExperience = yearsOfExperience;
    }

    public WorkerProffessionE getProffession() {
        return proffession;
    }

    public void setProffession(WorkerProffessionE proffession) {
        this.proffession = proffession;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void startWorking() {
        isWorking = true;
    }
    public void stopWorking() {
        isWorking = false;
    }
}
