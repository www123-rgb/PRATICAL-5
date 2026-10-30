package com.example.practicalfive;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_INDEX = "com.example.practicalfive.EXTRA_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView titleView = findViewById(R.id.detailTitle);
        TextView historyView = findViewById(R.id.historyText);
        TextView wordCountView = findViewById(R.id.wordCountText);
        ImageView photoView = findViewById(R.id.cityPhoto);
        MaterialButton shareButton = findViewById(R.id.shareButton);
        MaterialButton backButton = findViewById(R.id.backButton);

        String[] names = getResources().getStringArray(R.array.municipality_names);
        String[] histories = getResources().getStringArray(R.array.municipality_histories);
        int index = getIntent().getIntExtra(EXTRA_INDEX, -1);

        if (!TextProcessor.isValidIndex(index, names.length)
                || !TextProcessor.isValidIndex(index, histories.length)) {
            titleView.setText(R.string.missing_municipality);
            historyView.setText("");
            wordCountView.setText("");
            photoView.setVisibility(View.GONE);
            shareButton.setEnabled(false);
        } else {
            String history = TextProcessor.normalizeHistory(histories[index]);
            titleView.setText(TextProcessor.formatTitle(names[index]));
            historyView.setText(history);
            wordCountView.setText(getString(R.string.word_count, TextProcessor.wordCount(history)));

            TypedArray images = getResources().obtainTypedArray(R.array.municipality_images);
            int imageId = images.getResourceId(index, 0);
            images.recycle();
            if (imageId != 0) {
                photoView.setImageResource(imageId);
            }

            final String shareTitle = TextProcessor.formatTitle(names[index]);
            final String shareHistory = history;
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = TextProcessor.buildShareMessage(
                            shareTitle,
                            shareHistory,
                            getString(R.string.share_footer));
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, message);
                    startActivity(Intent.createChooser(
                            shareIntent,
                            getString(R.string.share_via)));
                }
            });
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
