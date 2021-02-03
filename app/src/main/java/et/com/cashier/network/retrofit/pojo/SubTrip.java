package et.com.cashier.network.retrofit.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import et.com.cashier.model.Company;

public class SubTrip implements Parcelable
{
    @SerializedName("tripCode")
    @Expose
    private String tripCode;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("discount")
    @Expose
    private Double discount;
    @SerializedName("sourceLocal")
    @Expose
    private String sourceLocal;
    @SerializedName("destinationLocal")
    @Expose
    private String destinationLocal;

    public SubTrip(){}
    SubTrip(Parcel in)
    {
        tripCode = in.readString();
        source = in.readString();
        destination = in.readString();
        price = in.readDouble();
        discount = in.readDouble();
        sourceLocal = in.readString();
        destinationLocal = in.readString();
    }
    public String getTripCode() {
        return tripCode;
    }

    public void setTripCode(String tripCode) {
        this.tripCode = tripCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getSourceLocal() {
        return sourceLocal;
    }

    public void setSourceLocal(String sourceLocal) {
        this.sourceLocal = sourceLocal;
    }

    public String getDestinationLocal() {
        return destinationLocal;
    }

    public void setDestinationLocal(String destinationLocal) {
        this.destinationLocal = destinationLocal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tripCode);
        dest.writeString(source);
        dest.writeString(destination);
        dest.writeDouble(price);
        dest.writeDouble(discount);
        dest.writeString(sourceLocal);
        dest.writeString(destinationLocal);
    }
    public static final Parcelable.Creator<SubTrip> CREATOR = new Parcelable.Creator<SubTrip>() {
        public SubTrip createFromParcel(Parcel in) {
            return new SubTrip(in);
        }

        public SubTrip[] newArray(int size) {
            return new SubTrip[size];
        }
    };
}
