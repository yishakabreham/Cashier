package et.com.cashier.network.retrofit.pojo;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("soldSeatsCount")
    @Expose
    private Integer soldSeatsCount;
    @SerializedName("soldSeats")
    @Expose
    private List<String> soldSeats = null;
    @SerializedName("seatArrangements")
    @Expose
    private List<SeatArrangement> seatArrangements = null;
    @SerializedName("maxX")
    @Expose
    private Integer maxX;
    @SerializedName("maxY")
    @Expose
    private Integer maxY;
    @SerializedName("busInfo")
    @Expose
    private BusInfo busInfo;
    @SerializedName("subTrips")
    @Expose
    private ArrayList<SubTrip> subTrips;

    public Integer getSoldSeatsCount() {
        return soldSeatsCount;
    }

    public void setSoldSeatsCount(Integer soldSeatsCount) {
        this.soldSeatsCount = soldSeatsCount;
    }

    public List<String> getSoldSeats() {
        return soldSeats;
    }

    public void setSoldSeats(List<String> soldSeats) {
        this.soldSeats = soldSeats;
    }

    public List<SeatArrangement> getSeatArrangements() {
        return seatArrangements;
    }

    public void setSeatArrangements(List<SeatArrangement> seatArrangements) {
        this.seatArrangements = seatArrangements;
    }

    public Integer getMaxX() {
        return maxX;
    }

    public void setMaxX(Integer maxX) {
        this.maxX = maxX;
    }

    public Integer getMaxY() {
        return maxY;
    }

    public void setMaxY(Integer maxY) {
        this.maxY = maxY;
    }

    public BusInfo getBusInfo() {
        return busInfo;
    }

    public void setBusInfo(BusInfo busInfo) {
        this.busInfo = busInfo;
    }

    public ArrayList<SubTrip> getSubTrips() {
        return subTrips;
    }

    public void setSubTrips(ArrayList<SubTrip> subTrips) {
        this.subTrips = subTrips;
    }
}
