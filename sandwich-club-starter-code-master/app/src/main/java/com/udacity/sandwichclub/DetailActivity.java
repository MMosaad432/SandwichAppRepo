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

    private Sandwich sandwich;

    private TextView alsoKnownAsTV;
    private TextView placeOfOriginTV;
    private TextView descriptionTV;
    private TextView ingredientsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownAsTV = (TextView) findViewById(R.id.also_known_tv);
        placeOfOriginTV = (TextView) findViewById(R.id.origin_tv);
        descriptionTV = (TextView) findViewById(R.id.description_tv);
        ingredientsTV = (TextView) findViewById(R.id.ingredients_tv);

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
        sandwich = JsonUtils.parseSandwichJson(json);
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
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        int ingredientSize=1,alsoKnownAsSize=1;
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        String placeOfOrigins = sandwich.getPlaceOfOrigin();
        String description = sandwich.getDescription();
        List<String> ingredients = sandwich.getIngredients();

        if(alsoKnownAs.size()== 0)
            alsoKnownAsTV.setText(R.string.no_details);
        else
            for (String alsoKnown : alsoKnownAs) {
                if(alsoKnownAsSize==alsoKnownAs.size()){
                    alsoKnownAsTV.append(alsoKnown);
                }
                else {
                    alsoKnownAsTV.append(alsoKnown);
                    alsoKnownAsTV.append("\n");
                    alsoKnownAsSize++;
                }
            }

        if(placeOfOrigins.equals(""))
            placeOfOriginTV.setText(R.string.no_details);
        else
            placeOfOriginTV.setText(placeOfOrigins);

        if(description.equals(""))
            descriptionTV.setText(R.string.no_details);
        else
            descriptionTV.setText(description);

        if (ingredients.size()==0)
            ingredientsTV.setText(R.string.no_details);
        else
            for (String ingredient : ingredients) {
            if(ingredientSize==ingredients.size()){
                ingredientsTV.append(ingredient);
            }
            else {
                ingredientsTV.append(ingredient);
                ingredientsTV.append("\n");
                ingredientSize++;
            }
            }
    }
}
