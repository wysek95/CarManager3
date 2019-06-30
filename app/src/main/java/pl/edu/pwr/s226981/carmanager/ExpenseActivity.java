package pl.edu.pwr.s226981.carmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class ExpenseActivity extends AppCompatActivity
{
    public static final String EXPENSE = "EXPENSE";
    private EditText dateEt, costEt, milageEt, expenseEt;
    private Button confirmBttn;
    private DateFormat dateFormat;
    private Date date = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        setTitle("Nowy wydatek");

        dateEt = findViewById(R.id.date_E_ET);
        costEt = findViewById(R.id.cost_E_ET);
        milageEt = findViewById(R.id.milage_E_ET);
        confirmBttn = findViewById(R.id.confirm_E_BTTN);

        dateFormat = DateFormat.getDateInstance(); //Ustalanie formatu daty
        dateEt.setText(dateFormat.format(date));   //Ustawienie dnia dzisiejszego na dateEditText

        getDateET();

        confirmBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(dateEt.getText().toString()) || TextUtils.isEmpty(costEt.getText().toString()) || TextUtils.isEmpty(milageEt.getText().toString()))
                {
                    Toast.makeText(ExpenseActivity.this, "Wszystkie pola muszą być uzupełnione", Toast.LENGTH_LONG).show();
                }
                else {
                    ExpenseInfo newExpense = new ExpenseInfo(Integer.valueOf(milageEt.getText().toString()), Float.valueOf(costEt.getText().toString()), getDateET());
                    Intent intent = new Intent();
                    intent.putExtra(EXPENSE, newExpense);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
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
}
