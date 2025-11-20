package com.example.budgettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    private TextView textViewLastSum;
    private TextView textViewTotalSum;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        textViewLastSum = findViewById(R.id.textViewLastSum);
        textViewTotalSum = findViewById(R.id.textViewTotalSum);
        dbHelper = new DBHelper(this);

        double lastSum = getIntent().getDoubleExtra("SUM_RESULT", 0.0);
        textViewLastSum.setText("Pēdējā summa: " + lastSum);

        double totalFromDb = dbHelper.getTotalSumFromDb();
        textViewTotalSum.setText("Kopējā summa no DB: " + totalFromDb);
    }
}
