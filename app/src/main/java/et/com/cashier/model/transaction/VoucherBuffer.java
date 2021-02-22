package et.com.cashier.model.transaction;

public class VoucherBuffer
{
    private int identifier;
    private Voucher voucher;
    private LineItem lineItem;
    private TripTransaction tripTransaction;
    private VoucherConsignee voucherConsignee;
    private Activity activity;
    private OrganizationUnit organizationUnit;
    private Refund otherFees;

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public LineItem getLineItem() {
        return lineItem;
    }

    public void setLineItem(LineItem lineItem) {
        this.lineItem = lineItem;
    }

    public TripTransaction getTripTransaction() {
        return tripTransaction;
    }

    public void setTripTransaction(TripTransaction tripTransaction) {
        this.tripTransaction = tripTransaction;
    }

    public VoucherConsignee getVoucherConsignee() {
        return voucherConsignee;
    }

    public void setVoucherConsignee(VoucherConsignee voucherConsignee) {
        this.voucherConsignee = voucherConsignee;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public OrganizationUnit getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(OrganizationUnit organizationUnit) {
        this.organizationUnit = organizationUnit;
    }

    public Refund getOtherFees() {
        return otherFees;
    }

    public void setOtherFees(Refund otherFees) {
        this.otherFees = otherFees;
    }
}
