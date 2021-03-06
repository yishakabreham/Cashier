package et.com.cashier.network.retrofit.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import et.com.cashier.buffer.PassengerInformation;

public class Consignee_ implements Parcelable {
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("middleName")
    @Expose
    private String middleName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("remark")
    @Expose
    private String remark;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Consignee_() {}

    public Consignee_(Parcel in)
    {
        code = in.readInt();
        firstName = in.readString();
        middleName = in.readString();
        lastName = in.readString();
        mobile = in.readString();
        isActive = (Boolean.parseBoolean(in.readString()));
        remark = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(code);
        dest.writeString(firstName);
        dest.writeString(middleName);
        dest.writeString(lastName);
        dest.writeString(mobile);
        dest.writeString(String.valueOf(isActive));
        dest.writeString(remark);
    }
    public static final Parcelable.Creator<Consignee_> CREATOR = new Parcelable.Creator<Consignee_>() {
        public Consignee_ createFromParcel(Parcel in) {
            return new Consignee_(in);
        }

        public Consignee_[] newArray(int size) {
            return new Consignee_[size];
        }
    };
}
