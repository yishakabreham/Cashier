package et.com.cashier.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import et.com.cashier.R;
import et.com.cashier.adapters.ExpandableListAdapter;
import et.com.cashier.buffer.PassengerInformation;
import et.com.cashier.network.retrofit.API;
import et.com.cashier.network.retrofit.pojo.Consignee;
import et.com.cashier.network.retrofit.pojo.Consignee_;
import et.com.cashier.network.retrofit.post.ItemCode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class windowPassengerDetail extends AppCompatActivity
{
    private Button btnPassengerDetail, btnSearch;
    private String token;
    private String mobileNumber = null;
    private Consignee consignee;
    private ExpandableListView listView;

    private String firstName__, middleName__, lastName__, additionalInformation__, phoneNumber__;

    private EditText firstName, middleName, lastName, phoneNumber, additionalInformation, searchNumber;

    ExpandableListAdapter expandableListAdapter;
    ArrayList<String> expandableListTitle;
    HashMap<String, List<Consignee_>> expandableListDetail;

    private RelativeLayout listContainer;

    int initialHeight;
    private Consignee_ consignee_;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_passanger_detail);

        GetData();
        init();
    }
    private void GetData()
    {
        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("token");

        firstName__ = bundle.getString("firstName");
        middleName__ = bundle.getString("middleName");
        lastName__ = bundle.getString("lastName");
        additionalInformation__ = bundle.getString("additionalInformation");
        phoneNumber__ = bundle.getString("mobile");
    }

    private void init()
    {
        Toolbar toolbar = findViewById(R.id.tbPassengerDetail);
        toolbar.setTitle("Passenger Detail");
        setSupportActionBar(toolbar);

        firstName = findViewById(R.id.txtFirstName);
        middleName = findViewById(R.id.txtMiddleName);
        lastName = findViewById(R.id.txtLastName);
        phoneNumber = findViewById(R.id.txtPhone);
        additionalInformation = findViewById(R.id.txtOther);
        searchNumber = findViewById(R.id.txtSearch);

        listContainer = findViewById(R.id.rLExpandableList);
        listView = findViewById(R.id.expandableListView);

        btnPassengerDetail = findViewById(R.id.btnPassengerDetail);
        btnSearch = findViewById(R.id.btnPassengerDetailSearchCustomer);

        if(firstName__ != null && !firstName__.equals(""))
            firstName.setText(firstName__);
        if(middleName__ != null && !middleName__.equals(""))
            middleName.setText(middleName__);
        if(lastName__ != null && !lastName__.equals(""))
            lastName.setText(lastName__);
        if(additionalInformation__ != null && !additionalInformation__.equals(""))
            additionalInformation.setText(additionalInformation__);
        if(phoneNumber__ != null && !phoneNumber__.equals(""))
            phoneNumber.setText(phoneNumber__);

        mobileNumber = searchNumber.getText().toString();
        ItemCode itemCode = new ItemCode();
        itemCode.setCode("0914569653");

        initialHeight = listView.getMeasuredHeight();
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                initialHeight = initialHeight <= 0 ? listView.getMeasuredHeight() : initialHeight;
                int height = 0;

                int c = expandableListAdapter.getChildrenCount(0);

                height += (listView.getDividerHeight() + (listView.getChildAt(0).getMeasuredHeight())) * c;

                listView.getLayoutParams().height = initialHeight + height;
            }
        });
        listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                listView.getLayoutParams().height = initialHeight;
            }
        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                consignee_ = (Consignee_)parent.getExpandableListAdapter().getChild (groupPosition, childPosition);
                setFields(consignee_);
                return false;
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final windowProgress progress = windowProgress.getInstance();
                progress.showProgress(windowPassengerDetail.this, "Getting Customer", false);
                if(listContainer.getVisibility() == View.VISIBLE){
                    listView.collapseGroup(0);
                }
                API.consigneeDetail(token).getConsigneeDetail(itemCode)
                        .enqueue(new Callback<Consignee>() {
                            @Override
                            public void onResponse(Call<Consignee> call, Response<Consignee> response)
                            {
                                if(response.isSuccessful() && response.code() == 200)
                                {
                                    consignee = response.body();
                                    if(consignee != null)
                                    {
                                        List<Consignee_> consignees_ = consignee.getConsignees();
                                        if(consignees_ != null && consignees_.size() > 0)
                                        {
                                            if(consignees_.size() == 1)
                                            {
                                                listContainer.setVisibility(View.GONE);
                                                Consignee_ consignee_ = consignees_.get(0);
                                                setFields(consignee_);
                                            }
                                            else
                                            {
                                                listContainer.setVisibility(View.VISIBLE);
                                                expandableListDetail = new HashMap<>();
                                                expandableListDetail.put(consignees_.size() + " Records", consignees_);
                                                expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                                                expandableListAdapter = new ExpandableListAdapter(getApplicationContext(), expandableListTitle, expandableListDetail);
                                                listView.setAdapter(expandableListAdapter);
                                            }

                                        }else {
                                            listContainer.setVisibility(View.GONE);
                                            //Toast no result
                                        }
                                    }
                                }
                                else
                                {

                                }
                                progress.hideProgress();
                            }

                            @Override
                            public void onFailure(Call<Consignee> call, Throwable t)
                            {
                                View rootLayout = findViewById(R.id.rootLayout);
                                Snackbar snackbar = Snackbar
                                        .make(rootLayout, Html.fromHtml("<B>Error</B><Br/>error occurred while fetching data from server"), Snackbar.LENGTH_LONG);
                                snackbar.show();

                                progress.hideProgress();
                            }
                        });
            }
        });
        btnPassengerDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(windowPassengerDetail.this, windowSeatArrangement.class);

                intent.putExtra("code", consignee_.getCode());
                intent.putExtra("firstName", firstName.getText().toString());
                intent.putExtra("middleName", middleName.getText().toString());
                intent.putExtra("lastName", lastName.getText().toString());
                intent.putExtra("additionalInformation", additionalInformation.getText().toString());
                intent.putExtra("mobile", phoneNumber.getText().toString());

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setFields(Consignee_ consignee_){
        if(consignee_ != null) {
            firstName.setText(consignee_.getFirstName());
            middleName.setText(consignee_.getMiddleName());
            lastName.setText(consignee_.getLastName());
            phoneNumber.setText(consignee_.getMobile());
            additionalInformation.setText(String.valueOf(consignee_.getRemark()) != "null" ? String.valueOf(consignee_.getRemark()) : "");
        }
    }
}
