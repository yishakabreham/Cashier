package et.com.cashier.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import et.com.cashier.R;
import et.com.cashier.fragments.trip.fragment01;
import et.com.cashier.network.retrofit.pojo.SubTrip;
import et.com.cashier.network.retrofit.pojo.Trip_;
import et.com.cashier.utilities.CommonElements;

public class windowSubTrips extends AppCompatActivity
{
    private List<SubTrip> subTrips;
    private ListView listView;
    private String selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_sub_trips);

        GetData();
        init();
    }
    private void GetData()
    {
        Bundle bundle = getIntent().getExtras();
        subTrips = bundle.getParcelableArrayList("subTrips");
        selected = bundle.getString("selected");
    }
    private void init()
    {
        Toolbar toolbar = findViewById(R.id.tbSubTrips);
        toolbar.setTitle("Sub Trips");
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.lstSubTrips);
        SubTripAdapter adapter = new SubTripAdapter(this, subTrips);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                SubTrip subTrip = (SubTrip)adapter.getItem(position);
                Intent intent = new Intent(windowSubTrips.this, windowSeatArrangement.class);

                ArrayList<SubTrip> subTrips = new ArrayList<>();
                subTrips.add(subTrip);
                intent.putParcelableArrayListExtra("selectedSubTrip", subTrips);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    private class Holder
    {
        TextView tripDesc, tripAmount, tripDescTranslation;
        RelativeLayout relativeLayout;
        ImageView imageView;
    }
    private class SubTripAdapter extends BaseAdapter
    {
        private Context context;
        private LayoutInflater inflater;

        private List<SubTrip> trips;

        public SubTripAdapter(Context context, List<SubTrip> trips)
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
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            Holder holder = new Holder();
            View rowView;

            rowView = inflater.inflate(R.layout.item_sub_trip_list, null);

            holder.relativeLayout = rowView.findViewById(R.id.colorHolder);
            holder.tripDesc = rowView.findViewById(R.id.txtSRouteDesc);
            holder.tripAmount = rowView.findViewById(R.id.txtSTripUnitAmount );
            holder.tripDescTranslation = rowView.findViewById(R.id.txtSRouteDescTranslation);
            holder.imageView = rowView.findViewById(R.id.imgSelected);

            holder.relativeLayout.setBackgroundColor(position != 0 ? Color.rgb(246,190,0) : Color.rgb(85, 174, 58));
            holder.tripDesc.setText(trips.get(position).getSource() + " - " + trips.get(position).getDestination());
            holder.tripDescTranslation.setText(trips.get(position).getSourceLocal() + " - " + trips.get(position).getDestinationLocal());
            holder.tripAmount.setText(trips.get(position).getDiscount().equals(0.0) ? "ETB " + CommonElements.currencyFormat(String.valueOf(trips.get(position).getPrice())) :
                    "ETB " + CommonElements.currencyFormat(String.valueOf(trips.get(position).getPrice())) + "(Dis: ETB " + CommonElements.currencyFormat(String.valueOf(trips.get(position).getDiscount())) + ")");
            holder.imageView.setVisibility(trips.get(position).getTripCode().equals(selected) ? View.VISIBLE : View.INVISIBLE);

            return rowView;
        }
    }
}