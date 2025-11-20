package com.example.budgettracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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
    private Button buttonToggleTheme;
    private Button buttonHistoryMain;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber1 = findViewById(R.id.editTextNumber1);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonToggleTheme = findViewById(R.id.buttonToggleTheme);
        buttonHistoryMain = findViewById(R.id.buttonHistoryMain);

        dbHelper = new DBHelper(this);

        int currentNightMode = AppCompatDelegate.getDefaultNightMode();
        if (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            buttonToggleTheme.setText("☀");
        } else {
            buttonToggleTheme.setText("☾");
        }

        buttonToggleTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mode = AppCompatDelegate.getDefaultNightMode();

                if (mode == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    buttonToggleTheme.setText("☾");
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    buttonToggleTheme.setText("☀");
                }

                recreate();
            }
        });

        buttonHistoryMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

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
