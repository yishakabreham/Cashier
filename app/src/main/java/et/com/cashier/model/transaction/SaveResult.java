package et.com.cashier.model.transaction;

import java.util.ArrayList;

public class SaveResult
{
    private boolean result;
    private ArrayList<Bug> bugs;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ArrayList<Bug> getBugs() {
        return bugs;
    }

    public void setBugs(ArrayList<Bug> bugs) {
        this.bugs = bugs;
    }
}
