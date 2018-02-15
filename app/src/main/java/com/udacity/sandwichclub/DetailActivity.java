package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView m_also_known_tv;
    private TextView m_origin_tv;
    private TextView m_description_tv;
    private TextView m_ingredients_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

        StringBuffer sb1 = new StringBuffer();
        List<String> also_known_list = sandwich.getAlsoKnownAs();
        for(String str: also_known_list) {
            if(str == also_known_list.get(also_known_list.size() - 1))
                sb1.append(str);
            else
                sb1.append(str + "\n");
        }
        m_also_known_tv.setText(sb1.toString());
        m_origin_tv.setText(sandwich.getPlaceOfOrigin());
        m_description_tv.setText(sandwich.getDescription());

        StringBuffer sb2 = new StringBuffer();
        List<String> ingredients_list = sandwich.getIngredients();
        for(String str: ingredients_list) {
            if(str == ingredients_list.get(ingredients_list.size() - 1))
                sb2.append(str);
            else
                sb2.append(str + "\n");
        }
        m_ingredients_tv.setText(sb2.toString());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        m_also_known_tv = findViewById(R.id.also_known_tv);
        m_origin_tv = findViewById(R.id.origin_tv);
        m_description_tv = findViewById(R.id.description_tv);
        m_ingredients_tv = findViewById(R.id.ingredients_tv);
    }
}
