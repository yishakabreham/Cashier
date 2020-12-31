package et.com.cashier.network.retrofit.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trip {

    @SerializedName("trips")
    @Expose
    private List<Trip_> trips = null;

    public List<Trip_> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip_> trips) {
        this.trips = trips;
    }

}