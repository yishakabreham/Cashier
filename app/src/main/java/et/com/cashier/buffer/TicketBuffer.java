package et.com.cashier.buffer;

import java.util.ArrayList;

public class TicketBuffer
{
    private ArrayList<PassengerInformation> passengerInformationList;

    public ArrayList<PassengerInformation> getPassengerInformationList() {
        return passengerInformationList;
    }

    public void setPassengerInformationList(ArrayList<PassengerInformation> passengerInformationList) {
        this.passengerInformationList = passengerInformationList;
    }
}
