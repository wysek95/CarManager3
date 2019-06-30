package pl.edu.pwr.s226981.carmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_REFUEL_CODE = 1;
    public static final int REQUEST_EXPENSE_CODE = 2;
    public static final int REQUEST_CARREGI_CODE = 3;
    public static final String REFUEL_DATA = "REFUEL_DATA";
    public static final String EXPENSE_DATA = "EXPENSE_DATA";
    private Button refuelBttn, expenseBttn,addCarBttn;
    private TextView refuelCntTw, fuelConsTw, expenseCntTw, expenseSumTw, costAvgTw;
    private Spinner spinner;
    private ArrayList<CarInfo> carList;
    private ArrayList<RefuelInfo> refuelList;
    private ArrayList<ExpenseInfo> expenseList;
    private ArrayAdapter<CarInfo> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
        {
            carList = (ArrayList<CarInfo>) savedInstanceState.get("SAVE1");
            refuelList = (ArrayList<RefuelInfo>) savedInstanceState.get("SAVE2");
            expenseList = (ArrayList<ExpenseInfo>) savedInstanceState.get("SAVE3");
        }

        setContentView(R.layout.activity_main);
        setTitle("Menu głowne");

        refuelBttn = findViewById(R.id.refuel_M_BTTN);
        expenseBttn = findViewById(R.id.expense_M_BTTN);
        addCarBttn = findViewById(R.id.add_car_M_BTTN);
        spinner = findViewById(R.id.spinner_M);

        refuelCntTw = findViewById(R.id.refuel_counter_M_TW);
        fuelConsTw = findViewById(R.id.fuel_consumption_M_TW);
        expenseCntTw = findViewById(R.id.expense_counter_M_TW);
        expenseSumTw = findViewById(R.id.exp_sum_M_TW);

        spinnerInit();
        spinner.setAdapter(arrayAdapter);

        refuelBttn.setOnClickListener(goToRefuelActivity());
        expenseBttn.setOnClickListener(goToExpenseActivity());
        addCarBttn.setOnClickListener(goToCarRegistrationActivity());
    }

    private View.OnClickListener goToCarRegistrationActivity() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CarRegistrationActivity.class);
                startActivityForResult(intent, REQUEST_CARREGI_CODE);
            }
        };
    }

    private View.OnClickListener goToExpenseActivity() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                intent.putExtra(EXPENSE_DATA, chosenCar());
                startActivityForResult(intent, REQUEST_EXPENSE_CODE);
            }
        };
    }

    private View.OnClickListener goToRefuelActivity() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RefuelActivity.class);
                intent.putExtra(REFUEL_DATA, chosenCar());
                startActivityForResult(intent, REQUEST_REFUEL_CODE);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CARREGI_CODE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                if(data != null)
                {
                    CarInfo newCar = (CarInfo) data.getExtras().get(CarRegistrationActivity.REGICAR);
                    carList.add(newCar);
                }
            }
        }
        else if(requestCode == REQUEST_REFUEL_CODE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    RefuelInfo newRefuel = (RefuelInfo) data.getExtras().get(RefuelActivity.REFUEL);
                    refuelList.add(newRefuel);

                    consumptionCalc();
                    refuelCntTw.setText(Integer.valueOf(refuelList.size()).toString());
                    expenseSumCalc();
                }
            }
        }
        else if(requestCode == REQUEST_EXPENSE_CODE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                if(data != null)
                {
                    ExpenseInfo newExpense = (ExpenseInfo) data.getExtras().get(ExpenseActivity.EXPENSE);
                    expenseList.add(newExpense);

                    expenseSumCalc();
                    expenseCntTw.setText(Integer.valueOf(expenseList.size()).toString());
                }
            }
        }
    }

    private void expenseSumCalc()
    {
        Float pom1 = 0F;
        Float pom2 = 0F;
        Integer size1 = 0;
        Integer size2 = 0;
        size1 = refuelList.size();
        size2 = expenseList.size();

        if(size1>0)
        {
            for (int i = 0; i <= size1 - 1; i++)
            {
                pom1 += refuelList.get(i).getCost();
            }
        }
        if(size2>0)
        {
            for (int j = 0; j <= size2 - 1; j++)
            {
                pom2 += expenseList.get(j).getCost();
            }
        }
        expenseSumTw.setText(Float.valueOf(pom1 + pom2).toString() + "PLN");
    }

    private void consumptionCalc()
    {
        Integer size = 0;
        Float pom1 = 0F;
        Integer pom2 = 0;
        Integer pom3 = 0;
        Integer pom4 = 0;
        size = refuelList.size();

        if(size != 1)
        {
            for(int i = 0; i <= size-2; i++)
            {
                pom1 += refuelList.get(i).getLiters();
            }
            pom3 = refuelList.get(size-1).getMilage();
            pom4 = refuelList.get(0).getMilage();
            pom2 = pom3 - pom4;
            pom2 = pom2 / 100;
            pom1 = pom1 / pom2;
            fuelConsTw.setText(pom1.toString() + "L/100km ");
        }
        else
        {
            fuelConsTw.setText("Brak wystarczającej ilości danych tankowania");
        }
    }

    private void spinnerInit() {
        carList = new ArrayList();
        refuelList = new ArrayList();
        expenseList = new ArrayList();
        carList.add(new CarInfo("Fiat", "Bravo", "Bordowy", "ESI 12345"));
        carList.add(new CarInfo("Fiat", "Panda", "Niebieski", "ESI 54321"));
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, carList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private CarInfo chosenCar() {
        return (CarInfo) spinner.getSelectedItem();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putSerializable("SAVE1", carList);
        outState.putSerializable("SAVE2", refuelList);
        outState.putSerializable("SAVE3", expenseList);
        super.onSaveInstanceState(outState);
    }
}
