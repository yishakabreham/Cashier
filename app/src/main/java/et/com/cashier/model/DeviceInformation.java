package et.com.cashier.model;

import java.io.Serializable;

public class DeviceInformation implements Serializable
{
    private String imeiNumber;
    private Location location;

    public String getImeiNumber() {
        return imeiNumber;
    }

    public void setImeiNumber(String imeiNumber) {
        this.imeiNumber = imeiNumber;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
