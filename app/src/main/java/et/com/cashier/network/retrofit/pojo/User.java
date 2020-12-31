package et.com.cashier.network.retrofit.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable
{
    private String id;
    private String name;
    private String dob;
    private String title;
    private String gender;
    private String position;
    private boolean isActive;

    public User(){}
    User(Parcel in)
    {
        id = in.readString();
        name = in.readString();
        dob = in.readString();
        title = in.readString();
        gender = in.readString();
        position = in.readString();
        isActive = Boolean.parseBoolean(in.readString());
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(dob);
        dest.writeString(title);
        dest.writeString(gender);
        dest.writeString(position);
        dest.writeString(String.valueOf(isActive));
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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
