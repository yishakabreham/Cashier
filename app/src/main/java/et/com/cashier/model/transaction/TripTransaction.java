package et.com.cashier.model.transaction;

public class TripTransaction
{
    private String code;
    private String lineItem;
    private String seat;
    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLineItem() {
        return lineItem;
    }

    public void setLineItem(String lineItem) {
        this.lineItem = lineItem;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
