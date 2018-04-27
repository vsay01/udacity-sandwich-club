package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private AppCompatImageView mIngredientsIv;
    private AppCompatTextView mKnownAsNameTvLabel;
    private AppCompatTextView mPlaceOfOriginTvLabel;
    private AppCompatTextView mIngredientsTvLabel;
    private AppCompatTextView mDescriptionTvLabel;
    private AppCompatTextView mKnownAsNameTvValue;
    private AppCompatTextView mPlaceOfOriginTvValue;
    private AppCompatTextView mIngredientsTvValue;
    private AppCompatTextView mDescriptionTvValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mIngredientsIv = findViewById(R.id.image_iv);
        mKnownAsNameTvLabel = findViewById(R.id.tv_also_known_as_label);
        mPlaceOfOriginTvLabel = findViewById(R.id.tv_place_origin_label);
        mIngredientsTvLabel = findViewById(R.id.tv_ingredient_label);
        mDescriptionTvLabel = findViewById(R.id.tv_description_label);

        mKnownAsNameTvValue = findViewById(R.id.tv_also_known_as_value);
        mPlaceOfOriginTvValue = findViewById(R.id.tv_place_origin_value);
        mIngredientsTvValue = findViewById(R.id.tv_ingredient_value);
        mDescriptionTvValue = findViewById(R.id.tv_description_value);

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

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich != null) {
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(mIngredientsIv);

            setTitle(sandwich.getMainName());

            if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
                for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                    mKnownAsNameTvValue.append(sandwich.getAlsoKnownAs().get(i));
                    if (i != sandwich.getAlsoKnownAs().size() - 1) {
                        mKnownAsNameTvValue.append("\n");
                    }
                }
                mKnownAsNameTvLabel.setVisibility(View.VISIBLE);
                mKnownAsNameTvValue.setVisibility(View.VISIBLE);
            } else {
                mKnownAsNameTvLabel.setVisibility(View.GONE);
                mKnownAsNameTvValue.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(sandwich.getPlaceOfOrigin())) {
                mPlaceOfOriginTvValue.setText(sandwich.getPlaceOfOrigin());
                mPlaceOfOriginTvLabel.setVisibility(View.VISIBLE);
                mPlaceOfOriginTvValue.setVisibility(View.VISIBLE);
            } else {
                mPlaceOfOriginTvLabel.setVisibility(View.GONE);
                mPlaceOfOriginTvValue.setVisibility(View.GONE);
            }

            if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
                for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                    mIngredientsTvValue.append(sandwich.getIngredients().get(i));
                    if (i != sandwich.getIngredients().size() - 1) {
                        mIngredientsTvValue.append("\n");
                    }
                }

                mIngredientsTvLabel.setVisibility(View.VISIBLE);
                mIngredientsTvValue.setVisibility(View.VISIBLE);
            } else {
                mIngredientsTvLabel.setVisibility(View.GONE);
                mIngredientsTvValue.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(sandwich.getDescription())) {
                mDescriptionTvValue.setText(sandwich.getDescription());
                mDescriptionTvLabel.setVisibility(View.VISIBLE);
                mDescriptionTvValue.setVisibility(View.VISIBLE);
            } else {
                mDescriptionTvLabel.setVisibility(View.GONE);
                mDescriptionTvValue.setVisibility(View.GONE);
            }
        }
    }
}