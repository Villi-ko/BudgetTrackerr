package com.example.budgettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    private TextView textViewLastSum;
    private TextView textViewTotalSum;
    private Button buttonBackMain;
    private Button buttonHistorySummary;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        textViewLastSum = findViewById(R.id.textViewLastSum);
        textViewTotalSum = findViewById(R.id.textViewTotalSum);
        buttonBackMain = findViewById(R.id.buttonBackMain);
        buttonHistorySummary = findViewById(R.id.buttonHistorySummary);
        dbHelper = new DBHelper(this);

        double lastSum = getIntent().getDoubleExtra("SUM_RESULT", 0.0);
        textViewLastSum.setText("Pēdējā summa: " + lastSum);

        double totalFromDb = dbHelper.getTotalSumFromDb();
        textViewTotalSum.setText("Kopējā summa no DB: " + totalFromDb);

        buttonBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        buttonHistorySummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SummaryActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
