package et.com.cashier.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInformation implements Parcelable
{
    private int status;
    private String token;
    private User user;
    private Company company;

    public UserInformation(){}
    public UserInformation(Parcel in)
    {
        token = in.readString();
        user = in.readParcelable(getClass().getClassLoader());
        company = in.readParcelable(getClass().getClassLoader());
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(token);
        dest.writeParcelable(user, flags);
        dest.writeParcelable(company, flags);
    }
    public static final Parcelable.Creator<UserInformation> CREATOR = new Parcelable.Creator<UserInformation>() {
        public UserInformation createFromParcel(Parcel in) {
            return new UserInformation(in);
        }

        public UserInformation[] newArray(int size) {
            return new UserInformation[size];
        }
    };
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

