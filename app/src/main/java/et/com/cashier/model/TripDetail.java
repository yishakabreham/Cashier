package et.com.cashier.model;

public class TripDetail
{
    private String id;
    private String tripCode;
    private String tripDate;
    private String busDesc;
    private Double tripUnitAmount;
    private Double tripDiscount;
    private String routeDesc;
    private Double timeLength;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTripCode() {
        return tripCode;
    }

    public void setTripCode(String tripCode) {
        this.tripCode = tripCode;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public String getBusDesc() {
        return busDesc;
    }

    public void setBusDesc(String busDesc) {
        this.busDesc = busDesc;
    }

    public Double getTripUnitAmount() {
        return tripUnitAmount;
    }

    public void setTripUnitAmount(Double tripUnitAmount) {
        this.tripUnitAmount = tripUnitAmount;
    }

    public Double getTripDiscount() {
        return tripDiscount;
    }

    public void setTripDiscount(Double tripDiscount) {
        this.tripDiscount = tripDiscount;
    }

    public String getRouteDesc() {
        return routeDesc;
    }

    public void setRouteDesc(String routeDesc) {
        this.routeDesc = routeDesc;
    }

    public Double getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(Double timeLength) {
        this.timeLength = timeLength;
    }
}
