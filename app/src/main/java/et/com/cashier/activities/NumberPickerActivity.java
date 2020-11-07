package et.com.cashier.activities;

import androidx.appcompat.app.AppCompatActivity;
import et.com.cashier.R;

import android.os.Bundle;
import android.widget.NumberPicker;

public class NumberPickerActivity extends AppCompatActivity {

    private NumberPicker picker1, picker2, picker3;
    private String[] pickerValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_picker);

        picker1 = findViewById(R.id.numPicker);
        picker1.setMaxValue(4);
        picker1.setMinValue(0);

        picker2 = findViewById(R.id.numPicker_month);
        picker2.setMaxValue(4);
        picker2.setMinValue(0);

        picker3 = findViewById(R.id.numPicker_day);
        picker3.setMaxValue(4);
        picker3.setMinValue(0);

        pickerValues = new String[]{"ABCD", "EFG", "HI", "JKLMNO", "PQRST"};
        picker1.setDisplayedValues(pickerValues);
        picker2.setDisplayedValues(pickerValues);
        picker3.setDisplayedValues(pickerValues);

    }
}