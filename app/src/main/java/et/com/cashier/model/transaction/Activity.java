package et.com.cashier.model.transaction;

import java.util.Date;

public class Activity
{
    private String code;
    private String reference;
    private int activityDefinition;
    private Date startTimeStamp;
    private Date endTimeStamp;
    private String organizationUnitDefinition;
    private String device;
    private String user;
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

    public int getActivityDefinition() {
        return activityDefinition;
    }

    public void setActivityDefinition(int activityDefinition) {
        this.activityDefinition = activityDefinition;
    }

    public Date getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(Date startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public Date getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(Date endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public String getOrganizationUnitDefinition() {
        return organizationUnitDefinition;
    }

    public void setOrganizationUnitDefinition(String organizationUnitDefinition) {
        this.organizationUnitDefinition = organizationUnitDefinition;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
