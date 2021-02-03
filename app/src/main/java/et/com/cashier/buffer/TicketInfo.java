package et.com.cashier.buffer;

import android.os.Parcel;
import android.os.Parcelable;

public class TicketInfo implements Parcelable
{
    private int flag;
    private String seatCode;
    private String seatName;
    private String tripCode;
    private String source;
    private String destination;
    private Double price;
    private Double discount;
    private String sourceLocal;
    private String destinationLocal;

    private PassengerInformation passenger;

    public TicketInfo(){}
    public TicketInfo(Parcel in)
    {
        flag = in.readInt();
        seatCode = in.readString();
        seatName = in.readString();
        tripCode = in.readString();
        source = in.readString();
        destination = in.readString();
        price = in.readDouble();
        discount = in.readDouble();
        sourceLocal = in.readString();
        destinationLocal = in.readString();

        passenger = in.readParcelable(getClass().getClassLoader());
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

    public PassengerInformation getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerInformation passenger) {
        this.passenger = passenger;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(flag);
        dest.writeString(seatCode);
        dest.writeString(seatName);
        dest.writeString(tripCode);
        dest.writeString(source);
        dest.writeString(destination);
        dest.writeDouble(price);
        dest.writeDouble(discount);
        dest.writeString(sourceLocal);
        dest.writeString(destinationLocal);
        dest.writeParcelable(passenger, flags);
    }
    public static final Parcelable.Creator<TicketInfo> CREATOR = new Parcelable.Creator<TicketInfo>() {
        public TicketInfo createFromParcel(Parcel in) {
            return new TicketInfo(in);
        }

        public TicketInfo[] newArray(int size) {
            return new TicketInfo[size];
        }
    };
}
