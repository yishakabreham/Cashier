package et.com.cashier.fragments.trip;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import et.com.cashier.R;
import et.com.cashier.activities.windowTrip;
import et.com.cashier.activities.windowTripDetail;
import et.com.cashier.adapters.RecyclerTouchListener;
import et.com.cashier.model.TripDate;
import et.com.cashier.model.TripDetail;
import et.com.cashier.network.HttpHandler;
import et.com.cashier.network.RequestPackage;

public class fragment01 extends Fragment {
    private GridLayoutManager gridLayoutManager;
    private DateAdapter dateAdapter;
    private TripAdapter adapter;
    private RecyclerView recyclerView;
    private ListView listView;
    private static String urlGetTrips = "http://192.168.1.126:5055/bus/Client/GetTrips";
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
        new GetTrips().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), windowTripDetail.class);
                startActivity(intent);
            }
        });
        staticData();
        return rootView;
    }

    private void staticData()
    {
        String[] tripDescriptions = {"Addis Ababa - Mekelle", "Addis Ababa - Jimma", "Addis Ababa - Dessie", "Jimma - Addis Ababa",
                "Addis Ababa - Gondar", "Mekelle - Bahirdar", "Shire - Addis Ababa"};
        String[] busDesciptions = {"Selam Bus 1", "Selam Bus 2", "Selam Bus 1", "Selam Bus 2", "Selam Bus 3", "Selam Bus 1", "Selam Bus 4"};
        String[] price = {"600", "178", "250", "1140", "470", "369", "80"};

        List<TripDetail> tripDetails = new ArrayList<>();
        for(int i = 0; i < tripDescriptions.length; i++)
        {
            TripDetail detail = new TripDetail();
            detail.setRouteDesc(tripDescriptions[i]);
            detail.setBusDesc(busDesciptions[i]);
            detail.setTripUnitAmount(Double.parseDouble(price[i]));

            tripDetails.add(detail);
        }

        TripAdapter adapter = new TripAdapter(getActivity(), tripDetails);
        listView.setAdapter(adapter);
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

    private void inflateList(List<TripDetail> tripDetails)
    {
        adapter = new TripAdapter(getActivity(), tripDetails);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                TripDetail tripDetail = (TripDetail) adapter.getItem(i);
            }
        });
    }

    private class Holder
    {
        TextView busDesc, tripDesc, tripAmount;
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

    private class TripAdapter extends BaseAdapter implements Filterable
    {
        private Context context;
        private LayoutInflater inflater;

        private List<TripDetail> tripDetails;
        private List<TripDetail> tripDetailList;

        public TripAdapter(Context context, List<TripDetail> tripDetails)
        {
            this.context = context;
            this.tripDetails = tripDetails;

            inflater = (LayoutInflater)context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return tripDetails.size();
        }

        @Override
        public Object getItem(int position) {
            return tripDetails.get(position);
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

            holder.busDesc.setText(tripDetails.get(position).getBusDesc());
            holder.tripDesc.setText(tripDetails.get(position).getRouteDesc());
            holder.tripAmount.setText("ETB " + String.valueOf(tripDetails.get(position).getTripUnitAmount()));

            return rowView;
        }

        @Override
        public Filter getFilter()
        {
            return new Filter() {

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    final FilterResults oReturn = new FilterResults();
                    final ArrayList<TripDetail> results = new ArrayList<>();

                    if(tripDetailList == null)
                        tripDetailList = tripDetails;
                    if (constraint != null)
                    {
                        if (tripDetailList != null && tripDetailList.size() > 0) {
                            for (final TripDetail g : tripDetailList) {
                                if (g.getRouteDesc().toLowerCase()
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
                    tripDetails = (ArrayList<TripDetail>) results.values;
                    notifyDataSetChanged();
                }
            };
        }

        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }

    private class GetTrips extends AsyncTask<Void, Void, List<TripDetail>>
    {
        private List<TripDetail> getTripDetails()
        {
            JSONArray jsonArray = null;
            HttpHandler httpHandler = new HttpHandler();
            ArrayList<TripDetail> tripDetails = new ArrayList<>();

            RequestPackage requestPackage = new RequestPackage();
            JSONObject jsonObjectPost = new JSONObject();
            try
            {
                jsonObjectPost.put("id", "1122334455");
                jsonObjectPost.put("fromDate", "12-04-2020");
                jsonObjectPost.put("toDate", "14-04-2020");

                requestPackage.setMethod("POST");
                requestPackage.setUri(urlGetTrips);
                requestPackage.setJsonObject(jsonObjectPost);

                jsonArray = httpHandler.makeServiceCallPost(requestPackage, getActivity());
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            if (jsonArray != null)
            {
                try
                {
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        TripDetail tripDetail = new TripDetail();

                        tripDetail.setId(jsonObject.getString("id"));
                        tripDetail.setTripCode(jsonObject.getString("tripCode"));
                        tripDetail.setTripDate(jsonObject.getString("tripDate"));
                        tripDetail.setBusDesc(jsonObject.getString("busDesc"));
                        tripDetail.setTripUnitAmount(jsonObject.getDouble("tripUnitAmount"));
                        tripDetail.setTripDiscount(jsonObject.getDouble("tripDiscount"));
                        tripDetail.setRouteDesc(jsonObject.getString("routeDesc"));
                        tripDetail.setTimeLength(jsonObject.getDouble("timeLength"));

                        tripDetails.add(tripDetail);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            return tripDetails;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected List<TripDetail> doInBackground(Void... voids)
        {
            return getTripDetails();
        }
        @Override
        protected void onPostExecute(List<TripDetail> tripDetails)
        {
            if(tripDetails != null && tripDetails.size() > 0)
            {
                inflateList(tripDetails);
            }
            else
            {
                //do sth
            }
        }
    }
}