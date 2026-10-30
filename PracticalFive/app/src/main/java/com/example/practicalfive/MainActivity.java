package com.example.practicalfive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner municipalitySpinner = findViewById(R.id.municipalitySpinner);
        String[] names = getResources().getStringArray(R.array.municipality_names);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        municipalitySpinner.setAdapter(adapter);

        MaterialButton showDetailsButton = findViewById(R.id.showDetailsButton);
        showDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = municipalitySpinner.getSelectedItemPosition();
                if (!TextProcessor.isValidIndex(index, names.length)) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_INDEX, index);
                startActivity(intent);
            }
        });
    }
}
