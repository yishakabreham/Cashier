package et.com.cashier.model;

public class User
{
    private String id;
    private String name;
    private String dob;
    private String title;
    private String gender;
    private String position;
    private boolean isActive;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getTitle() {
        return title;
    }

    public String getGender() {
        return gender;
    }

    public String getPosition() {
        return position;
    }

    public boolean isActive() {
        return isActive;
    }

}
