package com.example.budgettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumber1;
    private EditText editTextNumber2;
    private Button buttonCalculate;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber1 = findViewById(R.id.editTextNumber1);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        buttonCalculate = findViewById(R.id.buttonCalculate);

        dbHelper = new DBHelper(this);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n1Str = editTextNumber1.getText().toString().trim();
                String n2Str = editTextNumber2.getText().toString().trim();

                if (n1Str.isEmpty() || n2Str.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Lūdzu ievadiet abus skaitļus",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                double n1;
                double n2;
                try {
                    n1 = Double.parseDouble(n1Str);
                    n2 = Double.parseDouble(n2Str);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this,
                            "Nekorekti skaitļi",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                double sum = n1 + n2;

                dbHelper.insertCalculation(n1, n2, sum);

                Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
                intent.putExtra("SUM_RESULT", sum);
                startActivity(intent);
            }
        });
    }
}
