package et.com.cashier.network.retrofit.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Consignee {

    @SerializedName("consignees")
    @Expose
    private List<Consignee_> consignees = null;

    public List<Consignee_> getConsignees() {
        return consignees;
    }

    public void setConsignees(List<Consignee_> consignees) {
        this.consignees = consignees;
    }
}