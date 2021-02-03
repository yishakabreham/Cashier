package et.com.cashier.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import et.com.cashier.R;

import et.com.cashier.adapters.RecyclerTouchListener;
import et.com.cashier.adapters.SeatTicket;
import et.com.cashier.buffer.PassengerInformation;
import et.com.cashier.buffer.TicketBuffer;
import et.com.cashier.buffer.TicketInfo;
import et.com.cashier.network.retrofit.API;
import et.com.cashier.network.retrofit.pojo.Result;
import et.com.cashier.network.retrofit.pojo.SeatArrangement;
import et.com.cashier.network.retrofit.pojo.SingleSeatArrangement;
import et.com.cashier.network.retrofit.pojo.SubTrip;
import et.com.cashier.network.retrofit.pojo.Trip_;
import et.com.cashier.network.retrofit.post.ItemCode;
import et.com.cashier.utilities.EthiopianCalendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class windowSeatArrangement extends AppCompatActivity implements View.OnClickListener
{
    private SingleSeatArrangement singleSeatArrangement;
    private ImageView imgSubTrips;

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

    private HashMap<String, seatIDs> map = new HashMap<>();
    private LinearLayout linearLayoutX;
    private LinearLayout linearLayoutY;
    private LinearLayout linearLayout;

    private int xID = 122145, yID = 541221;
    private int LAUNCH_PASSENGER_INFORMATION_ACTIVITY = 1;
    private int LAUNCH_SUB_TRIP_ACTIVITY = 2;

    private SeatArrangement selectedArrangement;
    private TicketBuffer ticketBuffer;
    private ArrayList<PassengerInformation> passengerInformationArrayList;

    private SeatArrangement seatArrangement;
    private RelativeLayout relativeLayoutSubTrips;

    private TextView txtTripDescEn, txtTripDescAm;
    private TextView txtDateEn, txtDateAm;
    private TextView txtUnitPriceEn, txtUnitPriceAm;
    private TextView txtDiscountEn, txtDiscountAm;
    private TextView txtAvailableSeatsEn, txtAvailableSeatsAm;
    private FloatingActionButton btnContinue;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    private Trip_ trip_;
    private SubTrip parent;
    private SubTrip selectedSubTrip;

    private ArrayList<TicketInfo> ticketInfoList;
    private TicketInfo onProcessTicketInfo;

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
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBackPressed()
    {
        signalR_(false);
        if(selectedSeats.size() > 0)
        {
            sendSeatStatus(-1, String.join("%", selectedSeats));
        }
        finish();
        super.onBackPressed();
    }
    private void init()
    {
        ticketBuffer = new TicketBuffer();
        passengerInformationArrayList = new ArrayList<>();

        imgSubTrips = findViewById(R.id.imgDown);
        SV = findViewById(R.id.vScroll);

        relativeLayoutSubTrips = findViewById(R.id.rLSubTrips);

        txtTripDescEn = findViewById(R.id.txtEnRouteDesc);
        txtTripDescAm = findViewById(R.id.txtAmRouteDesc);

        txtDateEn = findViewById(R.id.txtSeatDateEn);
        txtDateAm = findViewById(R.id.txtSeatDateAm);

        txtUnitPriceEn = findViewById(R.id.txtSeatUnitPriceEn);
        txtUnitPriceAm = findViewById(R.id.txtSeatUnitPriceAm);

        txtDiscountEn = findViewById(R.id.txtSeatDiscountEn);
        txtDiscountAm = findViewById(R.id.txtSeatDiscountAm);

        txtAvailableSeatsEn = findViewById(R.id.txtSeatAvailableEn);
        txtAvailableSeatsAm = findViewById(R.id.txtSeatAvailableAm);

        btnContinue = findViewById(R.id.btnContinue);

        btnClicks = 0;
        selectedSeats = new ArrayList<>();
        seatNames = new ArrayList<>();
        seatsCollection = new ArrayList<>();
        arrangements = new ArrayList<>();
        ticketInfoList = new ArrayList<>();

        gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        ticketRecycler = findViewById(R.id.ticketRecycler);

        ticketRecycler.setLayoutManager(gridLayoutManager);
        ticketsAdapter = new SeatTicket(ticketInfoList, getApplicationContext(), mPrice);
        ticketRecycler.setAdapter(ticketsAdapter);
        ticketRecycler.setNestedScrollingEnabled(false);

        ticketRecycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), ticketRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position)
            {
                onProcessTicketInfo = ticketInfoList.get(position);

                selectedArrangement = arrangements.get(position);
                String firstName__, middleName__, lastName__, additionalInformation__, phoneNumber__;

                if(onProcessTicketInfo != null) {

                    if (onProcessTicketInfo.getPassenger() != null) {
                        firstName__ = onProcessTicketInfo.getPassenger().getFirstName();
                        middleName__ = onProcessTicketInfo.getPassenger().getMiddleName();
                        lastName__ = onProcessTicketInfo.getPassenger().getLastName();
                        additionalInformation__ = onProcessTicketInfo.getPassenger().getAdditionalInformation();
                        phoneNumber__ = onProcessTicketInfo.getPassenger().getPhoneNumber();
                    } else {
                        firstName__ = "";
                        middleName__ = "";
                        lastName__ = "";
                        additionalInformation__ = "";
                        phoneNumber__ = "";
                    }

                    Intent intent = new Intent(windowSeatArrangement.this, windowPassengerDetail.class);

                    intent.putExtra("token", token);
                    intent.putExtra("firstName", firstName__);
                    intent.putExtra("middleName", middleName__);
                    intent.putExtra("lastName", lastName__);
                    intent.putExtra("mobile", phoneNumber__);
                    intent.putExtra("additionalInformation", additionalInformation__);

                    startActivityForResult(intent, LAUNCH_PASSENGER_INFORMATION_ACTIVITY);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ticketInfoList == null || ticketInfoList.size() == 0){
                    //Toast
                }else {
                    Boolean inValid = Stream.of(ticketInfoList).anyMatch(t -> t.getPassenger() == null);
                    if(inValid){
                        Stream.of(ticketInfoList).forEach(t -> t.setFlag(t.getPassenger() == null ? -1 : 1));
                        ticketsAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        Intent intent = new Intent(windowSeatArrangement.this, windowConfirmation.class);
                        intent.putParcelableArrayListExtra("tickets", ticketInfoList);
                        startActivity(intent);
                    }
                }
            }
        });

        calculatePrice();
    }
    private void GetData()
    {
        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token");
        tripCode = bundle.getString("tripCode");

        trip_ = (Trip_)bundle.getSerializable("selectedTrip");
        txtTripDescEn.setText(trip_.getSource() + " - " + trip_.getDestination());
        txtTripDescAm.setText(trip_.getSourceLocal() + " - " + trip_.getDestinationLocal());

        DateFormatter(trip_);
        txtUnitPriceEn.setText(String.format("%.2f", trip_.getPrice()));
        txtUnitPriceAm.setText(String.format("%.2f", trip_.getPrice()));
        txtDiscountEn.setText(String.format("%.2f", trip_.getDiscount()));
        txtDiscountAm.setText(String.format("%.2f", trip_.getDiscount()));
        txtAvailableSeatsEn.setText(String.valueOf(trip_.getTotalSeats() - trip_.getAvailableSeats()));
        txtAvailableSeatsAm.setText(String.valueOf(trip_.getTotalSeats() - trip_.getAvailableSeats()));
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
                            renderSubTrips(singleSeatArrangement.getResult());
                            //new DrawSeat().execute(singleSeatArrangement.getResult());
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
    private void renderSubTrips(Result result)
    {
        if(result.getSubTrips() != null && result.getSubTrips().size() > 0)
        {
            ArrayList<SubTrip> subTrips = result.getSubTrips();

            parent = new SubTrip();
            parent.setDestination(trip_.getDestination());
            parent.setDestinationLocal(trip_.getDestinationLocal());
            parent.setSource(trip_.getSource());
            parent.setSourceLocal(trip_.getSourceLocal());
            parent.setPrice(trip_.getPrice());
            parent.setDiscount(trip_.getDiscount());
            parent.setTripCode(trip_.getTripCode());

            subTrips.add(0, parent);
            selectedSubTrip = parent;

            relativeLayoutSubTrips.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(windowSeatArrangement.this, windowSubTrips.class);

                    intent.putParcelableArrayListExtra("subTrips", subTrips);
                    intent.putExtra("selected", selectedSubTrip.getTripCode());
                    startActivityForResult(intent, LAUNCH_SUB_TRIP_ACTIVITY);
                }
            });
        }
        else
        {
            //Toast
        }
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

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(linearParam);

        final LinearLayout linearLayout1 = new LinearLayout(this);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setLayoutParams(linearParam);

        for(int x = 1 ; x <= maxX ; x++) {
            linearLayoutX = new LinearLayout(this);
            linearLayoutX.setOrientation(LinearLayout.VERTICAL);
            linearLayoutX.setLayoutParams(linearX);
            linearLayoutX.setId(xID + x);
            final int xf = x;

            for (int y = maxY; y >= 1; y--) {
                linearLayoutY = new LinearLayout(this);
                linearLayoutY.setOrientation(LinearLayout.HORIZONTAL);
                linearLayoutY.setLayoutParams(linearY);
                TextView textView = new TextView(this);
                textView.setTextSize(32 / 4);

                textView.setTextColor(getResources().getColor(android.R.color.black));
                textView.setLayoutParams(textParam);
                textView.setGravity(Gravity.CENTER);

                final int yf = y;
                seatArrangement = Stream.of(seatArrangements).filter(s -> s.getX() == xf && s.getY() == yf).findFirst().orElse(null);

                //for (int i = 0; i < seatArrangements.size(); i++)
                //{
                    //seatArrangement = seatArrangements.get(i);
                    if(seatArrangement != null)
                    {
                        String seatType = seatArrangement.getType();
                        linearLayoutY.setId(yID + y);
                        switch (seatType)
                        {
                            case "LKUP000000023":
                                linearLayoutY.setTag(seatArrangement);
                                String seatCode = seatArrangement.getCode();

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
                                linearLayoutY.setOnClickListener(this);
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
                        map.put(seatArrangement.getCode(), new seatIDs(xID + x, yID + y));
                    }
                //}
                linearLayoutY.addView(textView);
                linearLayoutX.addView(linearLayoutY);
            }
            linearLayout.addView(linearLayoutX);
        }
        SV.addView(linearLayout);
        signalR_(true);
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

        //totalPrice__.setText("total price: ETB/ብር " + totalPriceToPay);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_PASSENGER_INFORMATION_ACTIVITY)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                String firstName = data.getStringExtra("firstName");
                String middleName = data.getStringExtra("middleName");
                String lastName = data.getStringExtra("lastName");
                String additionalInformation = data.getStringExtra("additionalInformation");
                String mobile = data.getStringExtra("mobile");

                PassengerInformation passengerInformation = new PassengerInformation();
                passengerInformation.setSeatCode(onProcessTicketInfo.getSeatCode());
                passengerInformation.setSeatName(onProcessTicketInfo.getSeatName());
                passengerInformation.setTripCode(tripCode);
                passengerInformation.setFirstName(firstName);
                passengerInformation.setMiddleName(middleName);
                passengerInformation.setLastName(lastName);
                passengerInformation.setAdditionalInformation(additionalInformation);
                passengerInformation.setPhoneNumber(mobile);

                /*PassengerInformation toRemove = Stream.of(passengerInformationArrayList).filter(
                        x -> x.getSeatCode() == selectedArrangement.getCode()).findFirst().orElse(null);
                if(toRemove != null)
                {
                    passengerInformationArrayList.remove(toRemove);
                }

                passengerInformationArrayList.add(passengerInformation);
                ticketBuffer.setPassengerInformationList(passengerInformationArrayList);*/

                onProcessTicketInfo.setPassenger(passengerInformation);
                onProcessTicketInfo.setFlag(1);
                ticketsAdapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED)
            {
            }
        }
        else if(requestCode == LAUNCH_SUB_TRIP_ACTIVITY)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                ArrayList<SubTrip> subTrips = data.getParcelableArrayListExtra("selectedSubTrip");
                if(subTrips != null && subTrips.size() > 0)
                {
                    selectedSubTrip = subTrips.get(0);
                    txtTripDescEn.setText(selectedSubTrip.getSource() + " - " + selectedSubTrip.getDestination());
                    txtTripDescAm.setText(selectedSubTrip.getSourceLocal() + " - " + selectedSubTrip.getDestinationLocal());
                    txtUnitPriceEn.setText(String.format("%.2f", selectedSubTrip.getPrice()));
                    txtUnitPriceAm.setText(String.format("%.2f", selectedSubTrip.getPrice()));
                    txtDiscountEn.setText(String.format("%.2f", selectedSubTrip.getDiscount()));
                    txtDiscountAm.setText(String.format("%.2f", selectedSubTrip.getDiscount()));
                }
                else
                {

                }
            }
            else if (resultCode == Activity.RESULT_CANCELED)
            {

            }
        }
    }
    @Override
    public void onClick(View v)
    {
        SeatArrangement tag = (SeatArrangement) v.getTag();
        String sCode = tag.getCode();

        if (btnClicks == 0)
        {
            if (selectedSeats.contains(sCode))
            {
                v.setBackgroundResource(R.drawable.icon_available_seat);
                selectedSeats.remove(sCode);
                seatNames.remove(tag.getName());

                TicketInfo cInfo = Stream.of(ticketInfoList).filter(x -> x.getSeatCode().equals(sCode)).findFirst().orElse(null);
                ticketInfoList.remove(cInfo);

                arrangements.remove(tag);

                ticketsAdapter.notifyDataSetChanged();
                numSeats -= 1;
                calculatePrice();

                if(seatsCollection != null)
                {
                    seatsCollection.remove(sCode);
                }
                sendSeatStatus(-1, sCode);
            }
            else
            {
                v.setBackgroundResource(R.drawable.icon_selected_seat);
                selectedSeats.add(sCode);
                seatNames.add(tag.getName());

                TicketInfo cInfo = new TicketInfo();
                cInfo.setFlag(0);
                cInfo.setDestination(selectedSubTrip.getDestination());
                cInfo.setDestinationLocal(selectedSubTrip.getDestinationLocal());
                cInfo.setSource(selectedSubTrip.getSource());
                cInfo.setSourceLocal(selectedSubTrip.getSourceLocal());
                cInfo.setDiscount(selectedSubTrip.getDiscount());
                cInfo.setPrice(selectedSubTrip.getPrice());
                cInfo.setTripCode(selectedSubTrip.getTripCode());
                cInfo.setSeatCode(sCode);
                cInfo.setSeatName(tag.getName());
                //cInfo.setPassenger(new PassengerInformation());

                ticketInfoList.add(cInfo);

                arrangements.add(tag);
                ticketsAdapter.notifyDataSetChanged();
                numSeats += 1;
                calculatePrice();

                seatsCollection.add(sCode);
                sendSeatStatus(1, sCode);
            }

            btnClicks++;

        }
        else
        {
            if (selectedSeats.contains(sCode))
            {
                v.setBackgroundResource(R.drawable.icon_available_seat);
                selectedSeats.remove(sCode);
                seatNames.remove(tag.getName());

                arrangements.remove(tag);

                TicketInfo cInfo = Stream.of(ticketInfoList).filter(x -> x.getSeatCode().equals(sCode)).findFirst().orElse(null);
                ticketInfoList.remove(cInfo);

                ticketsAdapter.notifyDataSetChanged();

                numSeats -= 1;
                calculatePrice();

                if(seatsCollection != null)
                {
                    seatsCollection.remove(sCode);
                }
                sendSeatStatus(-1, sCode);
            }
            else
            {
                v.setBackgroundResource(R.drawable.icon_selected_seat);
                selectedSeats.add(sCode);
                seatNames.add(tag.getName());
                arrangements.add(tag);

                TicketInfo cInfo = new TicketInfo();
                cInfo.setFlag(0);
                cInfo.setDestination(selectedSubTrip.getDestination());
                cInfo.setDestinationLocal(selectedSubTrip.getDestinationLocal());
                cInfo.setSource(selectedSubTrip.getSource());
                cInfo.setSourceLocal(selectedSubTrip.getSourceLocal());
                cInfo.setDiscount(selectedSubTrip.getDiscount());
                cInfo.setPrice(selectedSubTrip.getPrice());
                cInfo.setTripCode(selectedSubTrip.getTripCode());
                cInfo.setSeatCode(sCode);
                cInfo.setSeatName(tag.getName());
                //cInfo.setPassenger(new PassengerInformation());
                ticketInfoList.add(cInfo);

                ticketsAdapter.notifyDataSetChanged();

                numSeats += 1;
                calculatePrice();

                seatsCollection.add(sCode);
                sendSeatStatus(1, sCode);
            }
            btnClicks--;
        }
    }
    class seatIDs
    {
        private int xID;
        private int yID;

        public int getxID() {
            return xID;
        }

        public void setxID(int xID) {
            this.xID = xID;
        }

        public int getyID() {
            return yID;
        }

        public void setyID(int yID) {
            this.yID = yID;
        }

        public seatIDs(int xID, int yID) {
            this.xID = xID;
            this.yID = yID;
        }
    }
    //For SignalR
    private void signalR(int type, String target)
    {
        if(!target.contains("%"))
        {
            seatIDs id = map.get(target);
            View p = linearLayout.findViewById(id.getxID());
            View py = p.findViewById(id.getyID());
            if (type == 1)
            {
                py.setOnClickListener(null);
                py.setBackgroundResource(R.drawable.icon_sold_seat);
            }
            else if (type == -1)
            {
                py.setBackgroundResource(R.drawable.icon_available_seat);
                py.setOnClickListener(this);
            }
        }
        else
        {
            String[] data = target.split("%");
            if(data != null && data.length > 0)
            {
                for(String seat : data)
                {
                    seatIDs id = map.get(seat);
                    try {
                        View p = linearLayout.findViewById(id.getxID());
                        View py = p.findViewById(id.getyID());
                        if (type == 1)
                        {
                            py.setOnClickListener(null);
                            py.setBackgroundResource(R.drawable.icon_sold_seat);
                        }
                        else if (type == -1)
                        {
                            py.setBackgroundResource(R.drawable.icon_available_seat);
                            py.setOnClickListener(this);
                        }
                    }catch (Exception ex){
                        Log.i("At update", "All: " + target + " | Crt: " + seat);
                    }

                }
            }
        }
    }
    private void signalR_(boolean subscribe)
    {
        try {
            if(subscribe){
                windowLogin.getHubConnection().on("seatStatusReceived", (type, trip, seat) -> {
                    if (tripCode.equals(trip) && seat != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                signalR(type, seat);
                            }
                        });
                    }
                }, Integer.class, String.class, String.class);

                windowLogin.getHubConnection().send("updatedMe", tripCode);
            }else {
                windowLogin.getHubConnection().remove("seatStatusReceived");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void sendSeatStatus(int selected, String seat)
    {
        try {
            windowLogin.getHubConnection().send("seatStatusUpdated", selected,tripCode,seat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void DateFormatter(Trip_ trip_)
    {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(trip_.getDate()));

            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int date = cal.get(Calendar.DAY_OF_MONTH);

            EthiopianCalendar ethiopianCalendar = new EthiopianCalendar(year, month, date, 1723856);
            int[] result = ethiopianCalendar.gregorianToEthiopic(year, month, date);

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            Date d = new Date();
            String dayOfTheWeek = sdf.format(d);

            String amMonth = windowDashboard.monthMapper(result[1]);
            String amDayMapper = windowDashboard.dayMapper(dayOfTheWeek);
            String ethiopianDate = String.format("%s ፡ %s %d ፡ %d", amDayMapper, amMonth, result[2], result[0]);

            txtDateEn.setText(DateFormat.getDateInstance(DateFormat.FULL).format(cal.getTime()));
            txtDateAm.setText(ethiopianDate);
        }
        catch (ParseException e) {
        e.printStackTrace();
    }
    }
}
