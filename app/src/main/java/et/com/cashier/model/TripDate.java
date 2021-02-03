package et.com.cashier.model;

import java.util.Date;

public class TripDate
{
    private String ethiopianDate;
    private String ethiopianYear;
    private Date gregorianDate;

    private String firstRow;
    private String secondRow;
    private String thirdRow;
    private boolean isSelected = false;

    public TripDate(String firstRow, String secondRow, String thirdRow, Date gregorianDate)
    {
        this.setFirstRow(firstRow);
        this.setSecondRow(secondRow);
        this.setThirdRow(thirdRow);
        this.setGregorianDate(gregorianDate);
    }

    public String getEthiopianDate() {
        return ethiopianDate;
    }

    public void setEthiopianDate(String ethiopianDate) {
        this.ethiopianDate = ethiopianDate;
    }

    public Date getGregorianDate() {
        return gregorianDate;
    }

    public void setGregorianDate(Date gregorianDate) {
        this.gregorianDate = gregorianDate;
    }

    public String getEthiopianYear() {
        return ethiopianYear;
    }

    public void setEthiopianYear(String ethiopianYear) {
        this.ethiopianYear = ethiopianYear;
    }

    public String getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(String firstRow) {
        this.firstRow = firstRow;
    }

    public String getSecondRow() {
        return secondRow;
    }

    public void setSecondRow(String secondRow) {
        this.secondRow = secondRow;
    }

    public String getThirdRow() {
        return thirdRow;
    }

    public void setThirdRow(String thirdRow) {
        this.thirdRow = thirdRow;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
