package et.com.cashier.model.transaction;

import java.util.Date;

public class Voucher
{
    private String code;
    private String type;
    private int consignee;
    private Date issuedDate;
    private boolean isIssued;
    private boolean isVoid;
    private Double grandTotal;
    private String lastObjectState;
    private String lastActivity;
    private String remark;
    private boolean isChild;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getConsignee() {
        return consignee;
    }

    public void setConsignee(int consignee) {
        this.consignee = consignee;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    public boolean isVoid() {
        return isVoid;
    }

    public void setVoid(boolean aVoid) {
        isVoid = aVoid;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getLastObjectState() {
        return lastObjectState;
    }

    public void setLastObjectState(String lastObjectState) {
        this.lastObjectState = lastObjectState;
    }

    public String getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(String lastActivity) {
        this.lastActivity = lastActivity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isChild() {
        return isChild;
    }

    public void setChild(boolean child) {
        isChild = child;
    }
}
