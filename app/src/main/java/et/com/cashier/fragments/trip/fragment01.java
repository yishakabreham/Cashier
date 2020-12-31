package et.com.cashier.fragments.trip;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
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

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import et.com.cashier.R;
import et.com.cashier.activities.windowLogin;
import et.com.cashier.activities.windowSeatArrangement;
import et.com.cashier.activities.windowTripDetail;
import et.com.cashier.adapters.RecyclerTouchListener;
import et.com.cashier.model.TripDate;
import et.com.cashier.model.TripDetail;
import et.com.cashier.network.retrofit.API;
import et.com.cashier.network.retrofit.pojo.Trip;
import et.com.cashier.network.retrofit.pojo.Trip_;
import et.com.cashier.network.retrofit.post.TripSearchCriteria;
import et.com.cashier.network.volley.HttpHandler;
import et.com.cashier.network.volley.RequestPackage;
import et.com.cashier.utilities.windowProgress;
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
        GetData();
        GetTrips_(token, searchCriteria);

        return rootView;
    }
    private void GetData()
    {
        Bundle bundle = getActivity().getIntent().getExtras();
        token = bundle.getString("token");

        searchCriteria = new TripSearchCriteria();
        searchCriteria.setFromDate("12-04-2020");
        searchCriteria.setToDate("");
        searchCriteria.setSource("");
        searchCriteria.setDestination("");
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

            holder.busDesc = rowView.findViewById(R.id.txtBusDesc);
            holder.tripDesc = rowView.findViewById(R.id.txtRouteDesc);
            holder.tripAmount = rowView.findViewById(R.id.txtTripUnitAmount );
            holder.tripDescTranslation = rowView.findViewById(R.id.txtRouteDescTranslation);

            holder.busDesc.setText(trips.get(position).getBusName());
            holder.tripDesc.setText(trips.get(position).getSource() + " - " + trips.get(position).getDestination());
            holder.tripDescTranslation.setText(trips.get(position).getSourceLocal() + " - " + trips.get(position).getDestinationLocal());
            holder.tripAmount.setText("ETB " + trips.get(position).getPrice());

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
    private void init(View rootView)
    {
        listView = rootView.findViewById(R.id.listTrips);
        recyclerView  = rootView.findViewById(R.id.recyclerDate);
        gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerView .setLayoutManager(gridLayoutManager);

        List<TripDate> dates = new ArrayList<>();
        for(int i = 0; i < 11; i++)
        {
            dates.add(new TripDate("Wed, Jun " + i));
        }
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
                TripDate tripDate = (TripDate)dateAdapter.getItem(position);
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
                startActivity(intent);
            }
        });
    }
    private class Holder
    {
        TextView busDesc, tripDesc, tripAmount, tripDescTranslation;
    }
    public static class DateAdapter extends RecyclerView.Adapter<DateAdapter.vHolder>
    {
        private Context context;
        private List<TripDate> tripDates;
        private int row_index = 0;

        public static class vHolder extends RecyclerView.ViewHolder
        {
            TextView title, subTitle;
            ImageView imageView;
            public vHolder(View v)
            {
                super(v);
                title = v.findViewById(R.id.txtTitle);
                subTitle = v.findViewById(R.id.txtSubTitle);
                imageView = v.findViewById(R.id.imgTic);
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
            holder.title.setText(tripDates.get(position).getDescription());
            holder.subTitle.setText("subtitle");
            holder.imageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    row_index = position;
                    notifyDataSetChanged();
                }
            });

            if(row_index == position)
            {
                holder.imageView.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.title.setTextColor(Color.parseColor("#567845"));
                holder.subTitle.setTextColor(Color.parseColor("#567845"));
            }
            else
            {
                holder.imageView.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.title.setTextColor(Color.parseColor("#000000"));
                holder.subTitle.setTextColor(Color.parseColor("#000000"));
            }
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
}