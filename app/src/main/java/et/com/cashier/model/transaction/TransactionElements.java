package et.com.cashier.model.transaction;

public class TransactionElements
{
    //region voucher related
    private boolean vIsChild;
    private String vRemark;
    //endregion
    //region lineItem related
    private int lTripCode;
    private String lSeatCode;
    private String lRemark;
    //endregion
    //region consignee related
    private int cCode;
    private String cFirstName;
    private String cMiddleName;
    private String cLastName;
    private String cMobile;
    private boolean cIsActive;
    private String cRemark;
    private int cFlag;
    //endregion
    //region getters/setters

    public String getvRemark() {
        return vRemark;
    }

    public void setvRemark(String vRemark) {
        this.vRemark = vRemark;
    }

    public boolean isvIsChild() {
        return vIsChild;
    }

    public void setvIsChild(boolean vIsChild) {
        this.vIsChild = vIsChild;
    }

    public int getlTripCode() {
        return lTripCode;
    }

    public void setlTripCode(int lTripCode) {
        this.lTripCode = lTripCode;
    }

    public String getlSeatCode() {
        return lSeatCode;
    }

    public void setlSeatCode(String lSeatCode) {
        this.lSeatCode = lSeatCode;
    }

    public String getlRemark() {
        return lRemark;
    }

    public void setlRemark(String lRemark) {
        this.lRemark = lRemark;
    }

    public int getcCode() {
        return cCode;
    }

    public void setcCode(int cCode) {
        this.cCode = cCode;
    }

    public String getcFirstName() {
        return cFirstName;
    }

    public void setcFirstName(String cFirstName) {
        this.cFirstName = cFirstName;
    }

    public String getcMiddleName() {
        return cMiddleName;
    }

    public void setcMiddleName(String cMiddleName) {
        this.cMiddleName = cMiddleName;
    }

    public String getcLastName() {
        return cLastName;
    }

    public void setcLastName(String cLastName) {
        this.cLastName = cLastName;
    }

    public boolean iscIsActive() {
        return cIsActive;
    }

    public void setcIsActive(boolean cIsActive) {
        this.cIsActive = cIsActive;
    }

    public String getcRemark() {
        return cRemark;
    }

    public void setcRemark(String cRemark) {
        this.cRemark = cRemark;
    }

    public int getcFlag() {
        return cFlag;
    }

    public void setcFlag(int cFlag) {
        this.cFlag = cFlag;
    }

    public String getcMobile() {
        return cMobile;
    }

    public void setcMobile(String cMobile) {
        this.cMobile = cMobile;
    }
    //endregion
}
