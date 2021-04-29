package et.com.cashier.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import et.com.cashier.R;
import et.com.cashier.adapters.SeatTicket;
import et.com.cashier.buffer.TicketInfo;
import et.com.cashier.model.transaction.BaseTransaction;
import et.com.cashier.model.transaction.SaveResult;
import et.com.cashier.model.transaction.TransactionElements;
import et.com.cashier.model.transaction.VoucherBuffer;
import et.com.cashier.network.retrofit.API;
import et.com.cashier.utilities.CommonElements;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class windowConfirmation extends AppCompatActivity
{
    private ArrayList<TicketInfo> ticketInfoList;
    private RecyclerView listView;
    private GridLayoutManager gridLayoutManager;

    private TextView qty, sub, dis, grand;
    private Button btnConfirm;
    private BaseTransaction baseTransaction;
    private SaveResult saveResult;
    private String tripCode;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_confirmation);

        GetData();
        init();
        render();
    }
    private void GetData()
    {
        Bundle bundle = getIntent().getExtras();
        ticketInfoList = bundle.getParcelableArrayList("tickets");
        tripCode = bundle.getString("tripCode");
        token = bundle.getString("token");
    }
    private void init()
    {
        Toolbar toolbar = findViewById(R.id.tbConfirmation);
        toolbar.setTitle("Confirmation");
        setSupportActionBar(toolbar);

        baseTransaction = new BaseTransaction();

        qty = findViewById(R.id.txtQuantity);
        sub = findViewById(R.id.txtSubtotal);
        dis = findViewById(R.id.txtDiscount);
        grand = findViewById(R.id.txtGrandTotal);
        btnConfirm = findViewById(R.id.btnPrint);

        SeatTicket adapter = new SeatTicket(ticketInfoList, getApplicationContext(), 0.0);

        listView = findViewById(R.id.lstItems);
        gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        listView.setLayoutManager(gridLayoutManager);
        listView.setAdapter(adapter);
        listView.setNestedScrollingEnabled(false);

        saveTransaction();
    }
    private void render()
    {
        int quantity;
        Double subTotal = 0.0, discount = 0.0 , grandTotal = 0.0;
        if(ticketInfoList != null && ticketInfoList.size() > 0)
        {
            quantity = ticketInfoList.size();
            for(TicketInfo ticketInfo : ticketInfoList)
            {
                grandTotal += ticketInfo.getPrice();
                discount += ticketInfo.getDiscount();
            }
            subTotal = grandTotal + discount;
            qty.setText(String.valueOf(quantity));
            sub.setText(CommonElements.currencyFormat(String.valueOf(subTotal)));
            dis.setText(CommonElements.currencyFormat(String.valueOf(discount)));
            grand.setText(CommonElements.currencyFormat(String.valueOf(grandTotal)));
        }
    }
    private boolean saveTransaction()
    {
        boolean result = false;
        try
        {
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final windowProgress progress = windowProgress.getInstance();
                    progress.showProgress(windowConfirmation.this, "Saving Transactions", false);

                    if(ticketInfoList != null && ticketInfoList.size() > 0)
                    {
                        ArrayList<TransactionElements> transactionElementsList = new ArrayList<>();
                        for(TicketInfo info : ticketInfoList)
                        {
                            TransactionElements transactionElements = new TransactionElements();
                            transactionElements.setvIsChild(false);
                            transactionElements.setvRemark(null);
                            transactionElements.setlTripCode(Integer.parseInt(tripCode));
                            transactionElements.setlSeatCode(info.getSeatCode());
                            transactionElements.setlRemark(tripCode.equals(info.getTripCode()) ? null : info.getTripCode());
                            transactionElements.setcCode(info.getPassenger().getCode());
                            transactionElements.setcFirstName(info.getPassenger().getFirstName());
                            transactionElements.setcMiddleName(info.getPassenger().getMiddleName());
                            transactionElements.setcLastName(info.getPassenger().getLastName());
                            transactionElements.setcIsActive(true);
                            transactionElements.setcRemark(null);
                            transactionElements.setcFlag(0);

                            transactionElementsList.add(transactionElements);
                        }
                        baseTransaction.setUser("User000000001");
                        baseTransaction.setDevice("DEV000000001");
                        baseTransaction.setTransactionElementsList(transactionElementsList);
                        API.postTransaction(token).saveTransaction(baseTransaction)
                                .enqueue(new Callback<SaveResult>() {
                                    @Override
                                    public void onResponse(Call<SaveResult> call, Response<SaveResult> response)
                                    {
                                        if(response.isSuccessful() && response.code() == 200)
                                        {
                                            Log.i("Entering Response", "200");
                                            saveResult = response.body();
                                            if(saveResult != null)
                                            {
                                                Log.i("Entering Response", String.valueOf(saveResult));
                                            }
                                        }
                                        else
                                        {
                                            Log.i("Error", "Error while saving transaction");
                                        }
                                        progress.hideProgress();
                                    }

                                    @Override
                                    public void onFailure(Call<SaveResult> call, Throwable t)
                                    {
                                        progress.hideProgress();
                                    }
                                });
                    }
                }
            });
            result = true;
        }
        catch (Exception ex)
        {

        }
        return result;
    }
}