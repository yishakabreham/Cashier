package et.com.cashier.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import et.com.cashier.R;
import et.com.cashier.adapters.RecyclerTouchListener;
import et.com.cashier.fragments.trip.fragment01;
import et.com.cashier.fragments.trip.fragment02;
import et.com.cashier.fragments.trip.fragment03;
import et.com.cashier.model.TripDate;
import et.com.cashier.model.TripDetail;
import et.com.cashier.network.HttpHandler;
import et.com.cashier.network.RequestPackage;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class windowTrip extends AppCompatActivity
{
    private com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView;
    private fragment01 fragment01;
    private fragment02 fragment02;
    private fragment03 fragment03;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_trip);

        initializeComponents();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(fragment01 == null)
                    {
                        fragment01 = new fragment01();
                    }
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout_home, fragment01);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_booking:
                    if(fragment02 == null)
                    {
                        fragment02 = new fragment02();
                    }
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.frame_layout_home, fragment02);
                    fragmentTransaction2.commit();
                    return true;
                case R.id.navigation_ticket:
                    if(fragment03 == null)
                    {
                        fragment03 = new fragment03();
                    }
                    FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.frame_layout_home, fragment03);
                    fragmentTransaction3.commit();
                    return true;
            }
            return false;
        }
    };
    private void initializeComponents()
    {
        Toolbar toolbar = findViewById(R.id.tbTrips);
        toolbar.setTitle("Trips");
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.navigation);
        fragment01 = new fragment01();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_home, fragment01);
        fragmentTransaction.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
