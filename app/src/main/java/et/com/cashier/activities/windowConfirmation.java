package et.com.cashier.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

public class windowConfirmation extends AppCompatActivity
{
    private ArrayList<TicketInfo> ticketInfoList;
    private RecyclerView listView;
    private GridLayoutManager gridLayoutManager;

    private TextView qty, sub, dis, grand;
    private Button btnConfirm;

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
    }
    private void init()
    {
        Toolbar toolbar = findViewById(R.id.tbConfirmation);
        toolbar.setTitle("Confirmation");
        setSupportActionBar(toolbar);

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
                subTotal += ticketInfo.getPrice();
                discount += ticketInfo.getDiscount();
            }
            grandTotal = subTotal - discount;
            qty.setText(String.valueOf(quantity));
            sub.setText(String.valueOf(subTotal));
            dis.setText(String.valueOf(discount));
            grand.setText(String.valueOf(grandTotal));
        }
    }
    private boolean saveTransaction()
    {
        boolean result = false;
        return result;
    }
}