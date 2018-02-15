package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**
     * Method parse JSON and create new Sandwich-Object
     *
     * @param json JSON-String to parse
     * @return Sandwich-Object or null
     */
    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;

        if(json != null && json.isEmpty() == false) {

            String mainName = null;
            String placeOfOrigin = null;
            String description = null;
            String image = null;
            List<String> alsoKnownAs = new ArrayList<String>();
            List<String> ingredients = new ArrayList<String>();

            // Initialize JSON-Object from JSON-String
            try {
                JSONObject objJSON = new JSONObject(json);
                /*
                    JSON-structure for Sandwich:
                {
                    name: 		{ mainName:valueName, alsoKnownAs:[] },
                    placeOfOrigin:	valuePlaceOfOrigin,
                    description:	valueDescription,
                    image:		valueImage,
                    ingredients:	[]
                }
                 */
                JSONObject name =  objJSON.getJSONObject("name");
                mainName = name.getString("mainName");
                JSONArray jsonAlsoKnownAs = name.getJSONArray("alsoKnownAs");
                placeOfOrigin =  objJSON.getString("placeOfOrigin");
                description =  objJSON.getString("description");
                image =  objJSON.getString("image");
                JSONArray jsonIngredients = objJSON.getJSONArray("ingredients");


                // loop and add to list
                for(int i=0;i<jsonAlsoKnownAs.length();i++) {
                    String s = jsonAlsoKnownAs.getString(i);
                    alsoKnownAs.add(s);
                }

                // loop and add to list
                for(int i=0;i<jsonIngredients.length();i++) {
                    String s = jsonIngredients.getString(i);
                    ingredients.add(s);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            // Create new Sandwich with data from JSON
            sandwich = new Sandwich(mainName,
                    alsoKnownAs,
                    placeOfOrigin,
                    description,
                    image,
                    ingredients);
        }

        return sandwich;
    }

}
