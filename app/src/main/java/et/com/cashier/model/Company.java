package et.com.cashier.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Company implements Parcelable
{
    private String tradeName;
    private String brandName;

    public Company(){}
    Company(Parcel in)
    {
        tradeName = in.readString();
        brandName = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(tradeName);
        dest.writeString(brandName);
    }

    public static final Parcelable.Creator<Company> CREATOR = new Parcelable.Creator<Company>() {
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        public Company[] newArray(int size) {
            return new Company[size];
        }
    };
    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public String getBrandName() {
        return brandName;
    }
}
