package et.com.cashier.model.transaction;

public class OrganizationUnit
{
    private String code;
    private String reference;
    private String organizationUnitDefinition;
    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getOrganizationUnitDefinition() {
        return organizationUnitDefinition;
    }

    public void setOrganizationUnitDefinition(String organizationUnitDefinition) {
        this.organizationUnitDefinition = organizationUnitDefinition;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
