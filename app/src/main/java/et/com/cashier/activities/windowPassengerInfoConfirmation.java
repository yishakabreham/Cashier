package et.com.cashier.activities;

import androidx.appcompat.app.AppCompatActivity;
import et.com.cashier.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class windowPassengerInfoConfirmation extends AppCompatActivity
{
    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_passanger_info_confirmation);

        init();
    }
    private void init()
    {
        btnContinue = findViewById(R.id.btnContinueToPayment);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
