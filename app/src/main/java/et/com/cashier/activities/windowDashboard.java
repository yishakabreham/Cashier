package et.com.cashier.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import et.com.cashier.R;
import et.com.cashier.adapters.RecyclerTouchListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class windowDashboard extends AppCompatActivity
{
    private List<DashBoardItem> dashBoardItemListBody;
    private RecyclerView recyclerViewBody;
    private GridLayoutManager gridLayoutManagerBody;
    private DashboardAdapter adapterBody;

    private ListView listViewTrips;
    private TextView dailyTripCount;
    private String[] bodyTitle =
            {
                    "Book a Ticket",
                    "Refund a Ticket",
                    "Reserve a Ticket",
                    "Daily Sales",
                    "Log Out",
                    "Close"
            };
    private String[] bodySubTitle =
            {
                    "ትኬት ይቁረጡ",
                    "ትኬት ይመልሱ",
                    "ትኬት ያስቀምጡ",
                    "የዛሬ ሽያጭ",
                    "ይውጡ",
                    "ይዝጉ"
            };
    private int[] bodyImages =
            {
                    R.drawable.icon_book_ticket,
                    R.drawable.icon_refund_ticket,
                    R.drawable.icon_reserve_ticket,
                    R.drawable.icon_report,
                    R.drawable.icon_log_out,
                    R.drawable.icon_exit
            };
    private String[] routes = {"Addis Ababa - Mekelle", "Addis Ababa - Hawassa", "Addis Ababa - Jimma", "Addis Ababa - Dessie", "Addis Ababa - Shire", "Addis Ababa - Bahirdar", "Addis Ababa - Harar"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_dashboard);

        init();
        listeners();
    }
    private void init()
    {
        dashBoardItemListBody = new ArrayList<>();

        for(int i = 0 ; i < bodyImages.length; i++)
        {
            DashBoardItem item = new DashBoardItem();
            item.setIcon(bodyImages[i]);
            item.setTitle(bodyTitle[i]);
            item.setSubTitle(bodySubTitle[i]);

            dashBoardItemListBody.add(item);
        }
        gridLayoutManagerBody = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        recyclerViewBody = findViewById(R.id.body);
        recyclerViewBody.setLayoutManager(gridLayoutManagerBody);
        adapterBody = new DashboardAdapter(windowDashboard.this, dashBoardItemListBody);
        recyclerViewBody.setAdapter(adapterBody);
        recyclerViewBody.setNestedScrollingEnabled(false);

        listViewTrips = findViewById(R.id.listTrips);
        ArrayList<TodayTrips> trips = new ArrayList();

        for(int i = 0; i < routes.length; i++)
        {
            TodayTrips todayTrips = new TodayTrips();
            todayTrips.setSn(String.valueOf(i + 1));
            todayTrips.setRoute(routes[i]);
            todayTrips.setUnitPrice("ETB " + (150 * (i + 1)));

            trips.add(todayTrips);
        }
        TodayTripsAdapter adapter = new TodayTripsAdapter(this, trips);
        listViewTrips.setAdapter(adapter);

        dailyTripCount = findViewById(R.id.dailyTripCount);
        dailyTripCount.setText("Total " + trips.size() + " Trips");
    }
    private void listeners()
    {
        recyclerViewBody.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewBody, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position)
            {
                switch (position)
                {
                    case 0:
                        Intent intent = new Intent(windowDashboard.this, windowTrip.class);
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
    public static class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.vHolder>
    {
        private Context context;
        private List<DashBoardItem> dashBoardItems;

        public static class vHolder extends RecyclerView.ViewHolder
        {
            TextView title, subTitle;
            ImageView imageView;
            public vHolder(View v)
            {
                super(v);
                title = v.findViewById(R.id.txtTileTitle);
                subTitle = v.findViewById(R.id.txtTileSubTitle);
                imageView = v.findViewById(R.id.imgDashboardIcon);
            }
        }

        public DashboardAdapter(Context context, List<DashBoardItem> dashBoardItems) {
            this.dashBoardItems = dashBoardItems;
            this.context = context;
        }

        @NonNull
        @Override
        public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_header, parent, false);
            return new vHolder(view);
        }

        @Override
        public void onBindViewHolder(DashboardAdapter.vHolder holder, final int position)
        {
            holder.title.setText(dashBoardItems.get(position).getTitle());
            holder.subTitle.setText(dashBoardItems.get(position).getSubTitle());
            holder.imageView.setImageResource(dashBoardItems.get(position).getIcon());
        }

        @Override
        public int getItemCount()
        {
            return dashBoardItems.size();
        }

        public Object getItem(int position)
        {
            return dashBoardItems.get(position);
        }
    }
    private class Holder
    {
        TextView sn, route, price;
    }
    public class TodayTripsAdapter extends BaseAdapter
    {
        private Context context;
        private LayoutInflater inflater;
        private List<TodayTrips> trips;

        public TodayTripsAdapter(Context context, List<TodayTrips> trips)
        {
            this.context = context;
            this.trips = trips;

            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            Holder holder = new Holder();
            View rowView;

            rowView = inflater.inflate(R.layout.item_today_trips, null);

            holder.sn = rowView.findViewById(R.id.todaySN);
            holder.route = rowView.findViewById(R.id.todayRoute);
            holder.price = rowView.findViewById(R.id.todayPrice);

            holder.sn.setText(trips.get(i).getSn());
            holder.route.setText(trips.get(i).getRoute());
            holder.price.setText(trips.get(i).getUnitPrice());

            return rowView;
        }
    }
    class DashBoardItem
    {
        private String title;
        private String subTitle;
        private int icon;

        public String getTitle() {
            return title;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public int getIcon() {
            return icon;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }
    }
    class TodayTrips
    {
        private String sn;
        private String route;
        private  String unitPrice;

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getRoute() {
            return route;
        }

        public void setRoute(String route) {
            this.route = route;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }
    }
}
