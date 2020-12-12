package et.com.cashier.activities;

import androidx.appcompat.app.AppCompatActivity;
import et.com.cashier.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class windowPassengerDetail extends AppCompatActivity
{
    private Button btnPassengerDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_passanger_detail);

        init();
    }

    private void init()
    {
        btnPassengerDetail = findViewById(R.id.btnPassengerDetail);
        btnPassengerDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(windowPassengerDetail.this, windowPassengerInfoConfirmation.class);
                startActivity(intent);
            }
        });
    }
}
