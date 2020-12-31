package et.com.cashier.network.retrofit.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusInfo
{
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("plateNo")
    @Expose
    private String plateNo;
    @SerializedName("sideNo")
    @Expose
    private String sideNo;
    @SerializedName("driver")
    @Expose
    private String driver;
    @SerializedName("coDrivers")
    @Expose
    private List<Object> coDrivers = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getSideNo() {
        return sideNo;
    }

    public void setSideNo(String sideNo) {
        this.sideNo = sideNo;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public List<Object> getCoDrivers() {
        return coDrivers;
    }

    public void setCoDrivers(List<Object> coDrivers) {
        this.coDrivers = coDrivers;
    }
}

