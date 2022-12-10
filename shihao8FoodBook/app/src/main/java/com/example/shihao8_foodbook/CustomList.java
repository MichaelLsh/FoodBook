package com.example.shihao8_foodbook;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<FoodEntry> {
    /* CustomList class extends from ArrayAdapter class in order to customize the ArrayAdapter based on our application's needs*/
    // on below CustomList class attributes
    // on below line foodEntries holds the food entries
    private ArrayList<FoodEntry> foodEntries;
    // on below line context holds the activity context
    private Context context;

    // on below CustomList constructor implementation
    public CustomList(Context contextPara, ArrayList<FoodEntry> foodPara) {
        super(contextPara, 0, foodPara);
        this.context = contextPara;
        this.foodEntries = foodPara;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get the reference to the current convertView object
        View view = convertView;

        if (view == null) {
            // check if the convertView holds nothing, then inflate the content.xml
            view = LayoutInflater.from(context).inflate(R.layout.item_content, parent, false);
        }

        // on below line get the current food entry and extract its info from the foodEntry list
        FoodEntry food = foodEntries.get(position);
        // and set its name, best before date, location, count and unit cost of this food entry to each TextView object to display
        TextView foodName = view.findViewById(R.id.food_name_text);
        TextView foodBestBeforeDate = view.findViewById(R.id.best_before_text);
        TextView foodLocation = view.findViewById(R.id.location_text);
        TextView foodCount = view.findViewById(R.id.count_text);
        TextView foodUnitCost = view.findViewById(R.id.unit_cost_text);

        foodName.setText(food.getFoodName());
        foodBestBeforeDate.setText(food.getFoodBestBeforeDate());
        foodLocation.setText(food.getFoodLocation());
        foodCount.setText(food.getFoodCount());
        foodUnitCost.setText(food.getFoodUnitCost());

        // return this view
        return view;
    }


}
