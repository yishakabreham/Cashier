package et.com.cashier;

import androidx.appcompat.app.AppCompatActivity;
import et.com.cashier.activities.NumberPickerActivity;
import et.com.cashier.activities.windowDashboard;
import et.com.cashier.activities.windowLogin;
import et.com.cashier.activities.windowPassengerDetail;
import et.com.cashier.activities.windowPassengerInfoConfirmation;
import et.com.cashier.activities.windowTrip;
import et.com.cashier.activities.windowTripDetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, windowLogin.class);
                startActivity(intent);
            }
        });
    }
}
