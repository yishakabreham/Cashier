package et.com.cashier.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import et.com.cashier.R;

import et.com.cashier.adapters.SeatTicket;
import et.com.cashier.network.retrofit.API;
import et.com.cashier.network.retrofit.pojo.Result;
import et.com.cashier.network.retrofit.pojo.SeatArrangement;
import et.com.cashier.network.retrofit.pojo.SingleSeatArrangement;
import et.com.cashier.network.retrofit.post.ItemCode;
import et.com.cashier.utilities.windowProgress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class windowSeatArrangement extends AppCompatActivity
{
    private SingleSeatArrangement singleSeatArrangement;

    private String token;
    private String tripCode;
    private int seatWH;
    private int btnClicks;
    private int numSeats = 0;
    private double mPrice = 400.00;

    private String totalPriceToPay;
    private ArrayList<String> seatsCollection;

    private ArrayList<String> selectedSeats;
    private ArrayList<String> seatNames;

    private ScrollView SV;
    private TextView totalPrice__;
    private Button btnPay;

    private SeatTicket ticketsAdapter;
    private ArrayList<SeatArrangement> arrangements;

    private RecyclerView ticketRecycler;
    private GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_seat_arrangement);

        init();
        GetData();
        ItemCode itemCode = new ItemCode();
        itemCode.setCode(tripCode);
        GetSeatArrangement_(token, itemCode);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(numSeats == 0)
                {
                    View rootLayout = findViewById(R.id.rootLayoutSeatArrangement);
                    Snackbar snackbar = Snackbar
                            .make(rootLayout, Html.fromHtml("<B>Error</B><Br/>please select seat"), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else
                {
                    Intent intent = new Intent(windowSeatArrangement.this, windowPassengerDetail.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                }
            }
        });
    }
    private void init()
    {
        SV = findViewById(R.id.vScroll);
        totalPrice__ = findViewById(R.id.totPrice);
        btnPay = findViewById(R.id.btnTotalPrice);

        btnClicks = 0;
        selectedSeats = new ArrayList<>();
        seatNames = new ArrayList<>();
        seatsCollection = new ArrayList<>();
        arrangements = new ArrayList<>();

        gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        ticketRecycler = findViewById(R.id.ticketRecycler);

        ticketRecycler.setLayoutManager(gridLayoutManager);
        ticketsAdapter = new SeatTicket(arrangements, getApplicationContext(), mPrice);
        ticketRecycler.setAdapter(ticketsAdapter);
        ticketRecycler.setNestedScrollingEnabled(false);

        calculatePrice();
    }
    private void GetData()
    {
        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token");
        tripCode = bundle.getString("tripCode");
    }
    private void GetSeatArrangement_(String token, ItemCode itemCode)
    {
        final windowProgress progress = windowProgress.getInstance();
        progress.showProgress(windowSeatArrangement.this, "Arranging Seats", false);

        API.seatArrangement(token).getSeatArrangement(itemCode)
                .enqueue(new Callback<SingleSeatArrangement>() {
                    @Override
                    public void onResponse(Call<SingleSeatArrangement> call, Response<SingleSeatArrangement> response)
                    {
                        if(response.isSuccessful() && response.code() == 200)
                        {
                            singleSeatArrangement = response.body();
                            drawSeats(singleSeatArrangement.getResult());
                        }

                        progress.hideProgress();
                    }

                    @Override
                    public void onFailure(Call<SingleSeatArrangement> call, Throwable t)
                    {
                        View rootLayout = findViewById(R.id.rootLayout);
                        Snackbar snackbar = Snackbar
                                .make(rootLayout, Html.fromHtml("<B>Error</B><Br/>error occurred while fetching data from server"), Snackbar.LENGTH_LONG);
                        snackbar.show();

                        progress.hideProgress();
                    }
                });
    }
    private void drawSeats(Result result)
    {
        List<SeatArrangement> seatArrangements = result.getSeatArrangements();
        List<String> soldSeats = result.getSoldSeats();
        int maxX = result.getMaxX();
        int maxY = result.getMaxY();

        int dpi = getDensity();

        if(dpi >= 100 && dpi < 160)
        {
            seatWH = 38;
        }
        else if(dpi >= 160 && dpi < 240)
        {
            seatWH = 42;
        }
        else if(dpi >= 240 && dpi < 320)
        {
            seatWH = 46;
        }
        else if(dpi >= 320 && dpi < 480)
        {
            seatWH = 54;
        }
        else if(dpi >= 480 && dpi < 580)
        {
            seatWH = 62;
        }
        else if(dpi >= 580)
        {
            seatWH = 74;
        }
        else
        {
            seatWH = 30;
        }

        LinearLayout.LayoutParams linearY = new LinearLayout.LayoutParams(seatWH, seatWH);
        linearY.setMargins(3,3,3,3);

        LinearLayout.LayoutParams linearX = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        linearX.setMargins(0,0,0,0);

        LinearLayout.LayoutParams linearParam = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL);

        LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(linearParam);

        final LinearLayout linearLayout1 = new LinearLayout(this);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setLayoutParams(linearParam);

        for(int x = 1 ; x <= maxX ; x++) {
            final LinearLayout linearLayoutX = new LinearLayout(this);
            linearLayoutX.setOrientation(LinearLayout.VERTICAL);
            linearLayoutX.setLayoutParams(linearX);

            for (int y = maxY; y >= 1; y--) {
                final LinearLayout linearLayoutY = new LinearLayout(this);
                linearLayoutY.setOrientation(LinearLayout.HORIZONTAL);
                linearLayoutY.setLayoutParams(linearY);
                TextView textView = new TextView(this);
                textView.setTextSize(32 / 4);

                textView.setTextColor(getResources().getColor(android.R.color.black));
                textView.setLayoutParams(textParam);
                textView.setGravity(Gravity.CENTER);

                for (int i = 0; i < seatArrangements.size(); i++)
                {
                    final SeatArrangement seatArrangement = seatArrangements.get(i);
                    if(seatArrangement.getX() == x && seatArrangement.getY() == y)
                    {
                        String seatType = seatArrangement.getType();
                        switch (seatType)
                        {
                            case "LKUP000000023":
                                linearLayoutY.setTag(seatArrangement);
                                SeatArrangement arrangement = (SeatArrangement)linearLayoutY.getTag();
                                String seatCode = arrangement.getCode();

                                if(soldSeats != null && soldSeats.size() > 0)
                                {
                                    if(soldSeats.contains(seatCode)) {
                                        linearLayoutY.setBackgroundResource(R.drawable.icon_sold_seat);
                                        textView.setText(seatArrangement.getName());
                                        linearLayoutY.setEnabled(false);
                                    }
                                    else
                                    {
                                        linearLayoutY.setBackgroundResource(R.drawable.icon_available_seat);
                                        textView.setText(seatArrangement.getName());
                                    }
                                }
                                else
                                {
                                    linearLayoutY.setBackgroundResource(R.drawable.icon_available_seat);
                                    textView.setText(seatArrangement.getName());
                                }
                                linearLayoutY.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        SeatArrangement tag = (SeatArrangement) v.getTag();
                                        String code = seatArrangement.getCode();
                                        String name = seatArrangement.getName();
                                        String sCode = tag.getCode();

                                        if (code == sCode && btnClicks == 0)
                                        {
                                            if (selectedSeats.contains(sCode))
                                            {
                                                linearLayoutY.setBackgroundResource(R.drawable.icon_available_seat);
                                                selectedSeats.remove(sCode);
                                                seatNames.remove(name);

                                                arrangements.remove(tag);
                                                ticketsAdapter.notifyDataSetChanged();
                                                numSeats -= 1;
                                                calculatePrice();

                                                if(seatsCollection != null)
                                                {
                                                    seatsCollection.remove(sCode);
                                                }

                                            } else {
                                                linearLayoutY.setBackgroundResource(R.drawable.icon_selected_seat);
                                                selectedSeats.add(sCode);
                                                seatNames.add(name);

                                                arrangements.add(tag);
                                                ticketsAdapter.notifyDataSetChanged();
                                                numSeats += 1;
                                                calculatePrice();

                                                seatsCollection.add(sCode);
                                            }

                                            btnClicks++;
                                        } else {
                                            if (selectedSeats.contains(sCode)) {
                                                linearLayoutY.setBackgroundResource(R.drawable.icon_available_seat);
                                                selectedSeats.remove(sCode);
                                                seatNames.remove(name);

                                                arrangements.remove(tag);

                                                ticketsAdapter.notifyDataSetChanged();

                                                numSeats -= 1;
                                                calculatePrice();

                                                if(seatsCollection != null)
                                                {
                                                    seatsCollection.remove(sCode);
                                                }

                                            } else {
                                                linearLayoutY.setBackgroundResource(R.drawable.icon_selected_seat);
                                                selectedSeats.add(sCode);
                                                seatNames.add(name);
                                                arrangements.add(tag);
                                                ticketsAdapter.notifyDataSetChanged();

                                                numSeats += 1;
                                                calculatePrice();

                                                seatsCollection.add(sCode);
                                            }
                                            btnClicks--;
                                        }
                                    }
                                });
                                break;
                            //ood
                            case "LKUP000000026":
                                linearLayoutY.setBackgroundResource(R.drawable.icon_out_of_order_seat);
                                break;
                            //aile
                            case "LKUP000000024":
                            //empty
                            case "LKUP000000025":
                                linearLayoutY.setBackgroundResource(android.R.color.transparent);
                                break;
                        }
                    }
                }
                linearLayoutY.addView(textView);
                linearLayoutX.addView(linearLayoutY);
            }
            linearLayout.addView(linearLayoutX);
        }
        SV.addView(linearLayout);
    }
    private int getDensity()
    {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int densityDpi = metrics.densityDpi;
        return densityDpi;
    }
    private void calculatePrice()
    {
        double totPrice = mPrice * numSeats;
        totalPriceToPay = String.format("%.2f", totPrice);

        totalPrice__.setText("total price: ETB/ብር " + totalPriceToPay);
    }
}
