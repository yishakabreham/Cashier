package et.com.cashier.activities;

import androidx.appcompat.app.AppCompatActivity;
import et.com.cashier.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class windowTripDetail extends AppCompatActivity {
    private Button btnConfirmTrip;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_trip_detail);

        init();
    }

    private void init()
    {
        btnConfirmTrip = findViewById(R.id.btnConfirmTrip);
        btnConfirmTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(windowTripDetail.this, windowPassengerDetail.class);
                startActivity(intent);
            }
        });
    }
}
