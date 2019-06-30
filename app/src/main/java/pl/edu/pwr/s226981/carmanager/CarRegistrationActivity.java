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

public class CarRegistrationActivity extends AppCompatActivity
{
    public static final String REGICAR = "REGICAR";
    private EditText brandEt, modelEt, colorEt, plateEt;
    private Button confirmBttn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_registration);
        setTitle("Rejestracja nowego samochodu");

        brandEt = findViewById(R.id.brand_CR_ET);
        modelEt = findViewById(R.id.model_CR_ET);
        colorEt = findViewById(R.id.color_CR_ET);
        plateEt = findViewById(R.id.plate_CR_ET);
        confirmBttn = findViewById(R.id.confirm_CR_BTTN);

        confirmBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(brandEt.getText().toString()) || TextUtils.isEmpty(modelEt.getText().toString()) || TextUtils.isEmpty(colorEt.getText().toString()) || TextUtils.isEmpty(plateEt.getText().toString()))
                {
                    Toast.makeText(CarRegistrationActivity.this, "Wszystkie pola muszą być uzupełnione", Toast.LENGTH_LONG).show();
                }
                else
                {
                    CarInfo carInfo = new CarInfo(brandEt.getText().toString(), modelEt.getText().toString(), colorEt.getText().toString(), plateEt.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra(REGICAR, carInfo);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
