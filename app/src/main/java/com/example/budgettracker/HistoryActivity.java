package com.example.budgettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ListView listViewHistory;
    private Button buttonBackFromHistory;
    private Button buttonDeleteHistory;
    private DBHelper dbHelper;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listViewHistory = findViewById(R.id.listViewHistory);
        buttonBackFromHistory = findViewById(R.id.buttonBackFromHistory);
        buttonDeleteHistory = findViewById(R.id.buttonDeleteHistory);

        dbHelper = new DBHelper(this);

        loadHistory();

        buttonBackFromHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonDeleteHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteAllCalculations();
                loadHistory();
            }
        });
    }

    private void loadHistory() {
        List<String> data = dbHelper.getAllCalculationsAsStrings();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                data
        );

        listViewHistory.setAdapter(adapter);
    }
}
