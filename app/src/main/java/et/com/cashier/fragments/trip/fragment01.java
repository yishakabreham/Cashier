package et.com.cashier.fragments.trip;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Stream;

import et.com.cashier.R;
import et.com.cashier.activities.windowDashboard;
import et.com.cashier.activities.windowPassengerDetail;
import et.com.cashier.activities.windowSeatArrangement;
import et.com.cashier.adapters.RecyclerTouchListener;
import et.com.cashier.buffer.PassengerInformation;
import et.com.cashier.model.TripDate;
import et.com.cashier.network.retrofit.API;
import et.com.cashier.network.retrofit.pojo.Trip;
import et.com.cashier.network.retrofit.pojo.Trip_;
import et.com.cashier.network.retrofit.post.TripSearchCriteria;
import et.com.cashier.activities.windowProgress;
import et.com.cashier.utilities.EthiopianCalendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment01 extends Fragment {
    private Trip trip;
    private String token;
    private TripSearchCriteria searchCriteria;

    private GridLayoutManager gridLayoutManager;
    private DateAdapter dateAdapter;
    private TripAdapter adapter;
    private RecyclerView recyclerView;
    private ListView listView;
    private static String urlGetTrips = "http://192.168.1.126:5055/bus/Client/GetTrips";
    private static String urlTrips = "http://192.168.1.155:8101/Trips/getTripsByDate";
    private List<TripDate> dates;
    private SimpleDateFormat tripDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private Date selectedDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_fragment01, container, false);
        init(rootView);
        itemEventListener();
        selectedDate = Calendar.getInstance().getTime();
        GetData(selectedDate);

        return rootView;
    }
    private void GetData(Date fromDate)
    {
        Bundle bundle = getActivity().getIntent().getExtras();
        token = bundle.getString("token");

        searchCriteria = new TripSearchCriteria();
        searchCriteria.setFromDate(fromDate != null ? tripDateFormat.format(fromDate) : "12-04-2020");//tripDateFormat.format(Calendar.getInstance().getTime())
        searchCriteria.setToDate("");
        searchCriteria.setSource("");
        searchCriteria.setDestination("");

        GetTrips_(token, searchCriteria);
    }
    private void GetTrips_(String token, TripSearchCriteria searchCriteria)
    {
        final windowProgress progress = windowProgress.getInstance();
        progress.showProgress(getActivity(), "Getting Trips", false);

        API.tripList(token).getTrips(searchCriteria)
            .enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response)
            {
                if(response.isSuccessful() && response.code() == 200)
                {
                    trip = response.body();
                    inflateList(trip.getTrips());
                }
                else
                {

                }
                progress.hideProgress();
            }
            @Override
            public void onFailure(Call<Trip> call, Throwable t) {

            }
        });
    }
    private class TripAdapter extends BaseAdapter implements Filterable
    {
        private Context context;
        private LayoutInflater inflater;

        private List<Trip_> trips;
        private List<Trip_> tripList;

        public TripAdapter(Context context, List<Trip_> trips)
        {
            this.context = context;
            this.trips = trips;

            inflater = (LayoutInflater)context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return trips.size();
        }
        @Override
        public Object getItem(int position) {
            return trips.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            Holder holder = new Holder();
            View rowView;

            rowView = inflater.inflate(R.layout.item_trips_list, null);

            holder.availSeats = rowView.findViewById(R.id.txtAvailSeats_);
            holder.availSubTrips = rowView.findViewById(R.id.txtAvailSubRoutes_);
            holder.tripDesc = rowView.findViewById(R.id.txtRouteDesc);
            holder.tripAmount = rowView.findViewById(R.id.txtTripUnitAmount );
            holder.tripDescTranslation = rowView.findViewById(R.id.txtRouteDescTranslation);

            holder.availSeats.setText(String.valueOf(trips.get(position).getTotalSeats()));
            holder.availSubTrips.setText(String.valueOf(trips.get(position).getSubTripsCount()));
            holder.tripDesc.setText(trips.get(position).getSource() + " - " + trips.get(position).getDestination());
            holder.tripDescTranslation.setText(trips.get(position).getSourceLocal() + " - " + trips.get(position).getDestinationLocal());
            holder.tripAmount.setText(trips.get(position).getDiscount().equals(0.0) ? "ETB " + String.format("%.2f", trips.get(position).getPrice()) :
                    "ETB " + String.format("%.2f", trips.get(position).getPrice()) + "(Dis: ETB " + String.format("%.2f", trips.get(position).getDiscount()) + ")"); //"ETB 750.0 (Dis: ETB 50.0)");

            return rowView;
        }
        @Override
        public Filter getFilter()
        {
            return new Filter() {

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    final FilterResults oReturn = new FilterResults();
                    final ArrayList<Trip_> results = new ArrayList<>();

                    if(tripList == null)
                        tripList = trips;
                    if (constraint != null)
                    {
                        if (tripList != null && tripList.size() > 0) {
                            for (final Trip_ g : tripList) {
                                if (g.getSource().toLowerCase()
                                        .contains(constraint.toString()))
                                    results.add(g);
                            }
                        }
                        oReturn.values = results;
                    }
                    return oReturn;
                }

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,
                                              FilterResults results) {
                    trips = (ArrayList<Trip_>) results.values;
                    notifyDataSetChanged();
                }
            };
        }
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }
    public void init(View rootView)
    {
        listView = rootView.findViewById(R.id.listTrips);
        recyclerView  = rootView.findViewById(R.id.recyclerDate);
        gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerView .setLayoutManager(gridLayoutManager);

        dates = setDate();

        dateAdapter = new DateAdapter(getActivity(), dates);
        recyclerView .setAdapter(dateAdapter);
        recyclerView .setNestedScrollingEnabled(false);
    }
    private void itemEventListener()
    {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position)
            {
                TripDate date = dates.get(position);
                Stream.of(dates).forEach(d -> d.setSelected(false));
                date.setSelected(true);
                dateAdapter.notifyDataSetChanged();
                selectedDate = date.getGregorianDate();
                GetData(selectedDate);
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
    private void inflateList(List<Trip_> trips)
    {
        adapter = new TripAdapter(getActivity(), trips);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Trip_ trip_ = (Trip_) adapter.getItem(i);

                Intent intent = new Intent(getActivity(), windowSeatArrangement.class);
                intent.putExtra("token", token);
                intent.putExtra("tripCode", trip_.getTripCode());

                intent.putExtra("selectedTrip", trip_);
//                intent.putExtra("date", selectedDate);
//                intent.putExtra("sourceEn", trip_.getSource());
//                intent.putExtra("sourceAm", trip_.getSourceLocal());
//                intent.putExtra("destinationEn", trip_.getDestination());
//                intent.putExtra("destinationAm", trip_.getDestinationLocal());
//                intent.putExtra("numberOfAvailableSeats", trip_.getAvailableSeats());

                startActivity(intent);
            }
        });
    }
    private class Holder
    {
        TextView availSeats, availSubTrips, tripDesc, tripAmount, tripDescTranslation;
    }
    public static class DateAdapter extends RecyclerView.Adapter<DateAdapter.vHolder>
    {
        private Context context;
        private List<TripDate> tripDates;
        private int row_index = 0;

        public static class vHolder extends RecyclerView.ViewHolder
        {
            TextView firstRow, secondRow, thirdRow;
            View rowLine;
            public vHolder(View v)
            {
                super(v);
                firstRow = v.findViewById(R.id.txtFirstRow);
                secondRow = v.findViewById(R.id.txtSecondRow);
                rowLine = v.findViewById(R.id.rowLine);
            }
        }

        public DateAdapter(Context context, List<TripDate> dates) {
            this.tripDates = dates;
            this.context = context;
        }

        @Override
        public DateAdapter.vHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip_date, parent, false);
            return new DateAdapter.vHolder(view);
        }

        @Override
        public void onBindViewHolder(DateAdapter.vHolder holder, final int position)
        {
            holder.firstRow.setText(tripDates.get(position).getFirstRow());
            holder.secondRow.setText(tripDates.get(position).getSecondRow());
            holder.rowLine.setVisibility(tripDates.get(position).isSelected() ? View.VISIBLE : View.INVISIBLE);
            holder.firstRow.setTextColor(tripDates.get(position).isSelected() ? Color.rgb(35,56,126) : Color.rgb( 128,128,128));
            holder.secondRow.setTextColor(tripDates.get(position).isSelected() ? Color.rgb(35,56,126) : Color.rgb(128,128,128));
//            holder.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View view)
//                {
//                    row_index = position;
//                    notifyDataSetChanged();
//                }
//            });
//
//            if(row_index == position)
//            {
//                holder.imageView.setBackgroundColor(Color.parseColor("#ffffff"));
//                holder.title.setTextColor(Color.parseColor("#567845"));
//                holder.subTitle.setTextColor(Color.parseColor("#567845"));
//            }
//            else
//            {
//                holder.imageView.setBackgroundColor(Color.parseColor("#ffffff"));
//                holder.title.setTextColor(Color.parseColor("#000000"));
//                holder.subTitle.setTextColor(Color.parseColor("#000000"));
//            }
        }

        @Override
        public int getItemCount()
        {
            return tripDates.size();
        }

        public Object getItem(int position)
        {
            return tripDates.get(position);
        }
    }
    private List<TripDate> setDate()
    {
        List<TripDate> tripDates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        TripDate tripDate;

        tripDate = getTripDate(calendar);
        tripDate.setSelected(true);
        tripDates.add(tripDate);

        for(int i = 1; i < 10; i++)
        {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            tripDate = getTripDate(calendar);
            tripDates.add(tripDate);
        }
        return tripDates;
    }
    private TripDate getTripDate(Calendar calendar){
        TripDate tripDate = null;
        if(calendar != null){
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int date = calendar.get(Calendar.DAY_OF_MONTH);

            EthiopianCalendar ethiopianCalendar = new EthiopianCalendar(year, month, date, 1723856);
            int[] result = ethiopianCalendar.gregorianToEthiopic(year, month, date);

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            Date d = calendar.getTime();
            String dayOfTheWeek = sdf.format(d);

            String amMonth = windowDashboard.monthMapper(result[1]);
            String amDayMapper = windowDashboard.dayMapper(dayOfTheWeek);
            String ethiopianDate = String.format("%s ፡ %s %d ፡ %d", amDayMapper, amMonth, result[2], result[0]);

            String[] split = ethiopianDate.split("፡");
            tripDate = new TripDate(split[0] + ":" + split[1], split[2], split[0], calendar.getTime());
            Log.i("Date ", ethiopianDate + ", " + DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime()));
        }
        return tripDate;
    }
}