package pl.edu.pwr.s226981.carmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RefuelActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{
    public static final String REFUEL = "REFUEL";
    private EditText dateEt, litersEt, costEt, milageEt;
    private Button confirmBttn;
    private DateFormat dateFormat;
    private Date date = new Date();
    private CarInfo carInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refuel);
        setTitle("Nowe tankowanie");

        dateEt = findViewById(R.id.date_R_ET);
        litersEt = findViewById(R.id.liters_R_ET);
        costEt = findViewById(R.id.cost_R_ET);
        milageEt = findViewById(R.id.milage_R_ET);
        confirmBttn = findViewById(R.id.confirm_R_BTTN);

        dateFormat = DateFormat.getDateInstance(); //Ustalanie formatu daty
        dateEt.setText(dateFormat.format(date));   //Ustawienie dnia dzisiejszego na dateEditText

        getDateET();

        carInfo = (CarInfo) getIntent().getExtras().getSerializable(MainActivity.REFUEL_DATA); // Pobranie dodatkowych danych z intencji wysłąnych przez MainActivity

        confirmBttn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(TextUtils.isEmpty(dateEt.getText().toString()) || TextUtils.isEmpty(litersEt.getText().toString()) || TextUtils.isEmpty(costEt.getText().toString()) || TextUtils.isEmpty(milageEt.getText().toString()))
                {
                    Toast.makeText(RefuelActivity.this, "Wszystkie pola muszą być uzupełnione", Toast.LENGTH_LONG).show();
                }
                else {
                    RefuelInfo newRefuel = new RefuelInfo(Integer.valueOf(milageEt.getText().toString()), Float.valueOf(litersEt.getText().toString()), Float.valueOf(costEt.getText().toString()), getDateET());
                    //carInfo.getRefuelInfo().add(new RefuelInfo(Integer.valueOf(milageEt.getText().toString()), Float.valueOf(litersEt.getText().toString()), Float.valueOf(costEt.getText().toString()), getDateET()));
                    Intent intent = new Intent();
                    intent.putExtra(REFUEL, newRefuel);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });

        dateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RefuelActivity.this, RefuelActivity.this, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private Date getDateET() {
        try {
            return dateFormat.parse(dateEt.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);

        dateEt.setText(dateFormat.format(calendar.getTime()));
    }
}
