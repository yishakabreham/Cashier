package et.com.cashier.buffer;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import et.com.cashier.network.retrofit.pojo.UserInformation;

public class PassengerInformation implements Parcelable
{
    private int code;
    private String firstName;
    private String middleName;
    private String lastName;
    private String additionalInformation;
    private String phoneNumber;
    private String tripCode;
    private String busCode;
    private String seatCode;
    private String seatName;
    private int isChild;

    public int getChild() {
        return isChild;
    }

    public void setChild(int child) {
        isChild = child;
    }

    public PassengerInformation(){}
    public PassengerInformation(Parcel in)
    {
        code = in.readInt();
        firstName = in.readString();
        middleName = in.readString();
        lastName = in.readString();
        additionalInformation = in.readString();
        phoneNumber = in.readString();
        tripCode = in.readString();
        busCode = in.readString();
        seatCode = in.readString();
        seatName = in.readString();
        isChild = in.readInt();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTripCode() {
        return tripCode;
    }

    public void setTripCode(String tripCode) {
        this.tripCode = tripCode;
    }

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(firstName);
        dest.writeString(middleName);
        dest.writeString(lastName);
        dest.writeString(additionalInformation);
        dest.writeString(phoneNumber);
        dest.writeString(tripCode);
        dest.writeString(busCode);
        dest.writeString(seatCode);
        dest.writeString(seatName);
        dest.writeInt(isChild);
    }
    public static final Parcelable.Creator<PassengerInformation> CREATOR = new Parcelable.Creator<PassengerInformation>() {
        public PassengerInformation createFromParcel(Parcel in) {
            return new PassengerInformation(in);
        }

        public PassengerInformation[] newArray(int size) {
            return new PassengerInformation[size];
        }
    };

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
