package people.worker;

public class Worker {

    private static long idCounter = 0;

    private long id;

    private String firstname;

    private String surname;

    private WorkerProfessionE profession;

    private String licenseNumber;

    private int yearsOfExperience;

    private boolean isWorking;

    public Worker(String firstname, String surname, String licenseNumber,
                  int yearsOfExperience, WorkerProfessionE profession) {

        this.id = ++idCounter;
        this.firstname = firstname;
        this.surname = surname;
        this.licenseNumber = licenseNumber;
        this.yearsOfExperience = yearsOfExperience;
        this.profession = profession;
        this.isWorking = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public WorkerProfessionE getProfession() {
        return profession;
    }

    public void setProfession(WorkerProfessionE profession) {
        this.profession = profession;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }
}
