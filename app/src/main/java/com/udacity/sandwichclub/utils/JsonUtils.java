package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.Constants;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Log.d("JSON: ", json);
        try {
            JSONObject jsonSandwich = new JSONObject(json);

            JSONObject jsonObjectName = jsonSandwich.getJSONObject(Constants.SANDWICH_KEY_NAME);

            String mainNameStr = jsonObjectName.getString(Constants.SANDWICH_KEY_MAIN_NAME);
            String placeOfOriginStr = jsonSandwich.getString(Constants.SANDWICH_KEY_PLACE_OF_ORIGIN);
            String descriptionStr = jsonSandwich.getString(Constants.SANDWICH_KEY_DESCRIPTION);
            String imageStr = jsonSandwich.getString(Constants.SANDWICH_KEY_IMAGE);

            JSONArray jsonArrayAlsoKnownAs = jsonObjectName.getJSONArray(Constants.SANDWICH_KEY_ALSO_KNOWN_AS);
            JSONArray jsonArrayIngredients = jsonSandwich.getJSONArray(Constants.SANDWICH_KEY_INGREDIENTS);

            List<String> alsoKnownAsList = new ArrayList<>();
            List<String> ingredientsList = new ArrayList<>();

            for (int i = 0; i < jsonArrayAlsoKnownAs.length(); i++) {
                alsoKnownAsList.add((String) jsonArrayAlsoKnownAs.get(i));
            }

            for (int i = 0; i < jsonArrayIngredients.length(); i++) {
                ingredientsList.add((String) jsonArrayIngredients.get(i));
            }

            return new Sandwich(mainNameStr,
                    alsoKnownAsList,
                    placeOfOriginStr,
                    descriptionStr,
                    imageStr,
                    ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
