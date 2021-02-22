package et.com.cashier.model.transaction;

import java.util.ArrayList;

public class BaseTransaction
{
    private String device;
    private String user;

    private ArrayList<TransactionElements> transactionElementsList;

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

    public ArrayList<TransactionElements> getTransactionElementsList() {
        return transactionElementsList;
    }

    public void setTransactionElementsList(ArrayList<TransactionElements> transactionElementsList) {
        this.transactionElementsList = transactionElementsList;
    }

}
