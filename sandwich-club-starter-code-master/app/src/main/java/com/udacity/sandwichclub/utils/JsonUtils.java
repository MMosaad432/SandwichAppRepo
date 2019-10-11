package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich selectedSandwich = new Sandwich();
        JSONObject sandwichString = null;
        JSONObject name = null;
        JSONArray alsoKnownAsArray;
        JSONArray ingredientsArray;

        String mainName,placeOfOrigin,description,image;
        List<String> alsoKnownAs = new ArrayList<String>();
        List<String> ingredients = new ArrayList<String>();

        try {
            sandwichString = new JSONObject(json);
            name = sandwichString.getJSONObject("name");


            mainName = name.getString("mainName");
            selectedSandwich.setMainName(mainName);

            alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            for ( int i = 0; i < alsoKnownAsArray.length();i++) {
                alsoKnownAs.add(alsoKnownAsArray.getString(i));
            }
            selectedSandwich.setAlsoKnownAs(alsoKnownAs);

            placeOfOrigin = sandwichString.getString("placeOfOrigin");
            selectedSandwich.setPlaceOfOrigin(placeOfOrigin);

            description = sandwichString.getString("description");
            selectedSandwich.setDescription(description);

            image= sandwichString.getString("image");
            selectedSandwich.setImage(image);

            ingredientsArray = sandwichString.getJSONArray("ingredients");
            for ( int i = 0; i < ingredientsArray.length();i++) {
                ingredients.add(ingredientsArray.getString(i));
            }
            selectedSandwich.setIngredients(ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return selectedSandwich;
    }
}
